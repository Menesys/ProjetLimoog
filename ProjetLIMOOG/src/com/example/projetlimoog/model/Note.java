package com.example.projetlimoog.model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projetlimoog.connection.Connection;
import com.example.projetlimoog.connection.Get;

public class Note {
	
	private Usager iUsager;
	private Competence iCompetence;
	private Evaluation iEvaluation;
	private int iNiveau;
	
	public Note(){}
	
	public Note(Usager usager, Competence competence, Evaluation evaluation, int niveau){
		iUsager = usager;
		iCompetence = competence;
		iEvaluation = evaluation;
		iNiveau = niveau;
		
		if (iCompetence!=null){
			iCompetence.addNote(this);
		}
		
		if (iUsager!=null){
			iUsager.addNote(this);
		}
		
		if (iEvaluation!=null){
			iEvaluation.addNote(this);
		}
	}

	/**
	 * GETTER iUsager
	 * @return iUsager
	 */
	
	public Usager getiUsager() {
		return iUsager;
	}

	public void setiUsager(Usager iUsager) {
		if (this.iUsager!=null){
			this.iUsager.removeNote(this);
		}
		this.iUsager = iUsager;
		if (this.iUsager!=null){
			this.iUsager.addNote(this);
		}
	}

	/**
	 * GETTER iCompetence
	 * @return iCompetence
	 */
	public Competence getiCompetence() {
		return iCompetence;
	}

	public void setiCompetence(Competence iCompetence) {
		if (this.iCompetence!=null){
			this.iCompetence.removeNote(this);
		}
		this.iCompetence = iCompetence;
		if (this.iCompetence!=null){
			this.iCompetence.addNote(this);
		}
	}
	
	/**
	 * GETTER iEvaluation
	 * @return iEvaluation
	 */

	public Evaluation getiEvaluation() {
		return iEvaluation;
	}

	public void setiEvaluation(Evaluation iEvaluation) {
		if (this.iEvaluation!=null){
			this.iEvaluation.removeNote(this);
		}
		this.iEvaluation = iEvaluation;
		if (this.iEvaluation!=null){
			this.iEvaluation.addNote(this);
		}
	}

	/**
	 * GETTER iNiveau
	 * @return iNiveau
	 */
	public int getiNiveau() {
		return iNiveau;
	}

	public String getiNiveauToString(){
		switch(iNiveau){
		case 1 :
			return("Non Acquis");
		case 2 :
			return("Passable");
		case 3 :
			return("Bien");
		case 4 :
			return("Très bien");
		default :
			return("Non noté");
			
		}
	}
	public void setiNiveau(int iNiveau) {
		this.iNiveau = iNiveau;
	}
	
	/**
	 * METHODE BDD
	 */
	
	public static ArrayList<Note> getNoteByCompEvalUser(int c, int ev, int u){
		
		Get g=new Get();
		ArrayList<Note> userlist = new ArrayList<Note>();
		g.execute(Connection.url+"GetNoteByCompEvalUser?comp="+c+"&eval="+ev+"&user="+u);
		
		try {
			String str=g.get();
			
			JSONObject reader = new JSONObject(str);
			JSONArray users =reader.getJSONArray("notes");
			
			if(users != null){
				int len = users.length();
				for(int i=0; i<len;i++){
					JSONObject elem=users.getJSONObject(i);
					int usager = elem.getInt("ipersonne");
					Usager user = Usager.getUsagerById(usager); // attention !!
					int comp = elem.getInt("icompetence");
					int eval = elem.getInt("ievaluation");
					int niveau = elem.getInt("iniveau");					
					//userlist.add(new Note(usager,comp,eval,niveau));
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return userlist;
	}
}
