import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BDDConnection {

	public Connection conn;
	private static BDDConnection co;

	private BDDConnection() {
		try {
			Class.forName("org.postgresql.Driver"); // Pour vérifier que la
													// class existe donc est
													// chargée
			String url = "jdbc:postgresql://localhost:5432/limoog"; // url de la
																	// base de
																	// données
			String user = "postgres"; // user de connection
			String pwd = "root";// mot de passe de connection
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
		}
	}

	public static BDDConnection getInstance() {
		if (co == null)
			co = new BDDConnection();
		return co;
	}

	
	
	// METHODES RELATIVES A L'USAGER

	/*
	 * Récupérer l'usager selon son ID
	 */
	public String getUsagerById(int n) {

		try {
			PreparedStatement pstate = conn.prepareStatement("Select * from tpersonne where iid=?");
			pstate.setInt(1, n); // 1 => premier "?"
			ResultSet res = pstate.executeQuery();
			return responseToJson(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Récupérer l'usager en fonction du login
	 */
	public String getUsagerByLogin(String s) {
		try {
			PreparedStatement pstate = conn.prepareStatement("Select * from tpersonne where login=?");
			pstate.setString(1, s);
			ResultSet res = pstate.executeQuery();
			return responseToJson(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer tous les ETUDIANTS
	 */
	public String getAllUsager() {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tpersonne where crole='user'");
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "usagers");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	// METHODES RELATIVES AUX COMPETENCES

	/*
	 * Récupérer la liste des compétences
	 */
	public String getAllCompetence() {

		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tcompetence");
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "competences");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Récupérer id des competences d'une évaluation
	 * */
	public List<Integer> getCompetenceIdsByEvaluation(int id){
		List<Integer> list=new ArrayList<Integer>();
		try {
			PreparedStatement pstate=conn.prepareStatement("select icompetence from tinclure where ievaluation=?");
			pstate.setInt(1, id);
			ResultSet res= pstate.executeQuery();
			while(res.next()){
				list.add(res.getInt(1));
			}
		} catch (Exception e) {
		}
		
		return list;
	}
	
	/*
	 * Récupérer la liste des compétences d'une évaluation (!!)
	 * */
	public String getCompetenceByEvaluation(int id){
	
		try {
			PreparedStatement pstate=conn.prepareStatement("SELECT * FROM tcompetence, tinclure WHERE tcompetence.iid = tinclure.icompetence AND tinclure.ievaluation = ?");
			pstate.setInt(1, id);
			ResultSet res= pstate.executeQuery();
			return responseToJsonList(res, "competences");
			
		} catch (Exception e) {}
		
		return null;
	}
	
	
	/*
	 * Récupérer une compétence par évaluation et personne
	 * */
	public List<Integer> getCompetenceIdsByEvaluationAndByPersonne(int id,int per){
		List<Integer> list=new ArrayList<Integer>();
		try {
			PreparedStatement pstate=conn.prepareStatement("SELECT tinclure.icompetence FROM tcompetence,tenseigner,tinclure WHERE tcompetence.imatiere = tenseigner.imatiere ANDtinclure.icompetence = tcompetence.iid ANDtenseigner.ipersonne = ? AND tinclure.ievaluation = ?");
			pstate.setInt(1, per);
			pstate.setInt(2, id);
			ResultSet res= pstate.executeQuery();
			while(res.next()){
				list.add(res.getInt(1));
			}
		} catch (Exception e) {
		}
		
		return list;
	}

	/*
	 * Récupérer la liste des compétences d'un usager
	 * */
	public String getCompetenceByPersonne(int per){
		
		try {
			PreparedStatement pstate = conn.prepareStatement("SELECT * FROM tcompetence, tenseigner WHERE tcompetence.imatiere = tenseigner.imatiere AND tenseigner.ipersonne = ?"); //REQUETE
			pstate.setInt(1, per);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "competences");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	// METHODES RELATIVES AUX EVALUATIONS

	/*
	 * créer une évaluation
	 */
	public boolean createEvaluation(String nom, Date date, int[] compsId) {
		java.sql.Date sDate=new java.sql.Date(date.getTime());
		System.out.println("create "+nom);
		boolean b=false;
		try {
			PreparedStatement pstate = conn.prepareStatement("insert into tevaluation (ddate,cnom) values (?,?)");
			pstate.setDate(1, sDate);
			pstate.setString(2, nom);
			b=pstate.executeUpdate()>0;
			pstate=conn.prepareStatement("select iid from tevaluation where cnom=? and ddate=?");
			pstate.setString(1, nom);
			pstate.setDate(2, sDate);
			ResultSet res=pstate.executeQuery();
			int evalId=0;
			while(res.next())
				evalId=res.getInt(1);
			pstate = conn.prepareStatement("insert into tinclure values (?,?)");
			for(int i:compsId){
				pstate.setInt(1, evalId);
				pstate.setInt(2, i);
				pstate.executeUpdate();
			}
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}

	/*
	 * modifier une évaluation
	 */
	public boolean updateEvaluation(int id, String nom, Date date, int[] compsId) {
		java.sql.Date sDate=new java.sql.Date(date.getTime());
		try {
			PreparedStatement pstate = conn.prepareStatement("update tevaluation set ddate=? , cnom=? where iid=?");
			pstate.setDate(1, sDate);
			pstate.setString(2, nom);
			pstate.setInt(3, id);
			boolean b=pstate.executeUpdate()>0;
			List<Integer> list =getCompetenceIdsByEvaluation(id);
			for(Integer i:list){
				boolean a=false;
				for(int j:compsId){
					if(j==i){
						a=true;
						break;
					}
				}
				if(!a){
					removeCompetenceByIdAndEvaluation(id,i);
				}
			}
			
			for(int i:compsId){
				if(!list.contains(i)){
					pstate=conn.prepareStatement("insert into tinclure values (?,?)");
					pstate.setInt(1, id);
					pstate.setInt(2, i);
					pstate.executeUpdate();
				}
			}
			
			return b;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			return false;
		
	}
	
	public boolean updateEvaluationByPersonne(int id, String nom, Date date, int[] compsId, int per) {
		java.sql.Date sDate=new java.sql.Date(date.getTime());
		try {
			PreparedStatement pstate = conn.prepareStatement("update tevaluation set ddate=? , cnom=? where iid=?");
			pstate.setDate(1, sDate);
			pstate.setString(2, nom);
			pstate.setInt(3, id);
			boolean b=pstate.executeUpdate()>0;
			List<Integer> list =getCompetenceIdsByEvaluationAndByPersonne(id, per);
			for(Integer i:list){
				boolean a=false;
				for(int j:compsId){
					if(j==i){
						a=true;
						break;
					}
				}
				if(!a){
					removeCompetenceByIdAndEvaluation(id,i);
				}
			}
			
			for(int i:compsId){
				if(!list.contains(i)){
					pstate=conn.prepareStatement("insert into tinclure values (?,?)");
					pstate.setInt(1, id);
					pstate.setInt(2, i);
					pstate.executeUpdate();
				}
			}
			
			return b;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			return false;
		
	}
	
	private void removeCompetenceByIdAndEvaluation(int eval, int comp) {
		try {
			
			PreparedStatement pstate=conn.prepareStatement("delete from tevalueravecevaluation where icompetence=? and ievaluation=?");
			pstate.setInt(1, comp);
			pstate.setInt(2, eval);
			pstate.executeUpdate();
			
			pstate=conn.prepareStatement("delete from tinclure where icompetence=? and ievaluation=?");
			pstate.setInt(1, comp);
			pstate.setInt(2, eval);
			pstate.executeUpdate();
		} catch (Exception e) {
		}
		
	}

	/*
	 * Récupérer la liste des évaluations
	 */
	public String getAllEval() {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevaluation");
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "evaluations");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Supprimer une évaluation
	 */
	public boolean deleteEval(int n) {
		PreparedStatement pstate;
		try {
			pstate = conn.prepareStatement("DELETE FROM tevalueravecevaluation WHERE ievaluation=?");
			pstate.setInt(1, n); // 1 => premier "?"
			pstate.executeUpdate();
			
			pstate = conn.prepareStatement("DELETE FROM tinclure WHERE ievaluation=?");
			pstate.setInt(1, n); // 1 => premier "?"
			pstate.executeUpdate();
			
			pstate = conn.prepareStatement("DELETE FROM tpreparer WHERE ievaluation=?");
			pstate.setInt(1, n); // 1 => premier "?"
			pstate.executeUpdate();
			
			pstate = conn.prepareStatement("DELETE FROM tevaluation WHERE iid=?");
			pstate.setInt(1, n); // 1 => premier "?"
			return pstate.executeUpdate()>0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	

	
	// METHODES RELATIVES AU MOT DE PASSE

	/*
	 * Récupérer login et mot de passe
	 */
	public String getLoginPassword(String s, String p) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tpersonne where clogin=? and cpassword=?");
			pstate.setString(1, s);
			pstate.setString(2, p);
			ResultSet res = pstate.executeQuery();

			return responseToJson(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	// METHODES RELATIVES AUX NOTES

	/*
	 * Récupérer les notes en fonction de la compétence, l'évaluation et
	 * l'utilisateur
	 */
	public String getNoteByCompEvalUser(int c, int ev, int u) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where icompetence=? and ievaluation=? and ipersonne=?");
			pstate.setInt(1, c);
			pstate.setInt(2, ev);
			pstate.setInt(3, u);
			ResultSet res = pstate.executeQuery();

			return responseToJson(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer les notes en fonction de l'évaluation et l'utilisateur
	 */
	public String getNoteByEvalUser(int ev, int u) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where ievaluation=? and ipersonne=?");
			pstate.setInt(1, ev);
			pstate.setInt(2, u);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer les notes en fonction de la compétence et l'utilisateur
	 */
	public String getNoteByCompUser(int c, int u) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where icompetence=? and ipersonne=?");
			pstate.setInt(1, c);
			pstate.setInt(2, u);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer les notes en fonction de la compétence et l'évaluation
	 */
	public String getNoteByCompEval(int c, int ev) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where icompetence=? and ievaluation=?");
			pstate.setInt(1, c);
			pstate.setInt(2, ev);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer les notes en fonction de l'évaluation
	 */
	public String getNoteByEval(int ev) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where ievaluation=?");
			pstate.setInt(1, ev);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer les notes en fonction de l'utilisateur
	 */
	public String getNoteByUser(int u) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where ipersonne=?");
			pstate.setInt(1, u);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Récupérer les notes en fonction de la compétence
	 */
	public String getNoteByComp(int c) {
		try {
			PreparedStatement pstate = conn
					.prepareStatement("Select * from tevalueravecevaluation where icompetence=?");
			pstate.setInt(1, c);
			ResultSet res = pstate.executeQuery();

			return responseToJsonList(res, "notes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	//METHODES RELATIVES A LA MATIERE
	
	/*
	 *Récupérer la matière par son id 
	 */
	public String getMatiereById(int n) {

		try {
			PreparedStatement pstate = conn.prepareStatement("Select * from tmatiere where iid=?");
			pstate.setInt(1, n); // 1 => premier "?"
			ResultSet res = pstate.executeQuery();
			return responseToJson(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}	
	
	
	// JSON

	/*
	 * Mets la réponse en JSON
	 */
	public String responseToJson(ResultSet res) throws SQLException {
		StringBuilder str = new StringBuilder();
		str.append("{");
		if (res.next()) { // res.next deplace le curseur d'une ligne et renvoie
							// vrai si la ligne est non vide
			for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) { // getMetaData informations sur la bdd
				System.out.print(res.getObject(i) + " | "); // res.getObject(i) prend l'object à la ième colonne
				str.append("\"" + res.getMetaData().getColumnLabel(i) + "\":\""
						+ res.getObject(i) + "\"");
				if (i != res.getMetaData().getColumnCount())
					str.append(",");
				str.append("\n");
			}
		}
		str.append("}");

		System.out.println();
		str.append("\n");
		return str.toString();
	}

	/*
	 * Mets la liste en JSON
	 */
	public String responseToJsonList(ResultSet res, String name)
			throws SQLException {
		StringBuilder str = new StringBuilder();
		str.append("{\"" + name + "\":[");

		while (res.next()) { // res.next deplace le curseur d'une ligne et
								// renvoie vrai si la ligne est non vide
			str.append("{");
			for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) { // getMetaData informations sur la bdd
				System.out.print(res.getObject(i) + " | "); // res.getObject(i) prend l'object à la ième colonne
				str.append("\"" + res.getMetaData().getColumnLabel(i) + "\":\""
						+ res.getObject(i) + "\"");
				if (i != res.getMetaData().getColumnCount())
					str.append(",");
				str.append("\n");
			}
			str.append("}");

			System.out.println();
			str.append(",\n");
		}
		if(str.charAt(str.length()-1)!='[')
			str.deleteCharAt(str.length() - 2);
		str.append("]}");
		return str.toString();
	}

}
