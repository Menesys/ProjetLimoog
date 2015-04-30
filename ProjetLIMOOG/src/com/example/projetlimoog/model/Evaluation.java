package com.example.projetlimoog.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.example.projetlimoog.connection.Connection;
import com.example.projetlimoog.connection.Get;

@SuppressLint("SimpleDateFormat") public class Evaluation implements Comparable<Evaluation>{

	// Une évaluation possède un ID, une Date, un Nom.
	private int iId;
	private Date cDate;
	private String sNom;

	// Une évaluation possède également une liste de notes, d'intervenants et de
	// compétences.
	private List<Intervenant> intervenants = new ArrayList<Intervenant>();
	private List<Note> notes = new ArrayList<Note>();
	private List<Competence> comp = new ArrayList<Competence>();

	public Evaluation() {

	}

	public Evaluation(int id, Date date, String nom) {
		iId = id;
		cDate = date;
		sNom = nom;
	}

	// Getters and setters

	// ID
	public int getiId() {
		return iId;
	}

	public void setiId(int iId) {
		this.iId = iId;
	}

	// DATE
//	public void setCdate(Date cDate) {
//		this.cDate = cDate;
//	}

	public Date getcDate() {
		return cDate;
	}

	// Change le format de la date :
	public String giveNiceDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cDate);
	}

	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}

	// INTERVENANTS
	public List<Intervenant> getIntervenants() {
		return intervenants;
	}

	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}

	// NOTES
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	// add / remove Intervenant and Note
	// without link -> sans allers-retours pour éviter une boucle infinie.

	public void addIntervenant(Intervenant i) {
		intervenants.add(i);
		i.addEvaluationWhitOutLink(this);
	}

	public void removeIntervenant(Intervenant i) {
		intervenants.remove(i);
		i.removeEvaluationWhitOutLink(this);
	}

	public void addIntervenantWithOutLink(Intervenant i) {
		intervenants.add(i);
	}

	public void removeIntervenantWithOutLink(Intervenant i) {
		intervenants.remove(i);
	}

	public void addNote(Note n) {
		notes.add(n);
	}

	public void removeNote(Note n) {
		notes.remove(n);
	}

	// COMPETENCE
	public List<Competence> getComp() {
		return comp;
	}

	public void setComp(List<Competence> comp) {
		this.comp = comp;
	}

	public void addComp(Competence c) {
		comp.add(c);
	}

	public void removeComp(Competence c) {
		comp.remove(c);
	}

	// NOM
	public String getsNom() {
		return sNom;
	}

	public void setsNom(String sNom) {
		this.sNom = sNom;
	}

	// Surcharge de la méthode ToString :
	@Override
	public String toString() {
		return sNom;

	}

	
	// METHODES BDD
	
	//Récupérer toutes les évaluations :
	public static ArrayList<Evaluation> getAllEval() {

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Get g = new Get();
		ArrayList<Evaluation> userlist = new ArrayList<Evaluation>();
		g.execute(Connection.url+"AllEval");

		try {
			String str = g.get();

			JSONObject reader = new JSONObject(str);
			JSONArray eval = reader.getJSONArray("evaluations");

			if (eval != null) {
				int len = eval.length();
				for (int i = 0; i < len; i++) {
					JSONObject elem = eval.getJSONObject(i);
					String nom = elem.getString("cnom");
					Date date = format.parse(elem.getString("ddate"));
					int id = elem.getInt("iid");

					userlist.add(new Evaluation(id, date, nom));
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return userlist;
	}

	//Supprimer une évaluation :
	public static void deleteEval(int n) {
		Get g = new Get();
		g.execute(Connection.url + "DeleteEval?id=" + n);
	}

	//Créer une évaluation - évaluation
	public static void createEval(Evaluation e){
		createEval(e.sNom, e.giveNiceDate(), e.comp);
	}
	
	//Créer une évaluation - nom date compétences
	public static void createEval(String nom, String date, List<Competence> comp){
		StringBuilder n=new StringBuilder();
		for(int i=0;i<nom.length();i++){
			if(nom.charAt(i)==' ')
				n.append("_");
			else
				n.append(nom.charAt(i));
		}
			
		Get g = new Get();
		StringBuilder str = new StringBuilder();
		for(Competence c:comp){
			str.append(c.getiId()+"-");
		}
		if(str.length()>0){
			str.deleteCharAt(str.length()-1);//enleve le dernier tiret
		}
		String vraiStr=Connection.url+"CreateEvaluation?nom="+n.toString()+"&date="+date+"&listcomp="+str.toString();
		System.out.println(vraiStr);
		g.execute(vraiStr);

	}
	
	//Updater une évaluation dans la BDD :
	public static void updateEval(int id,String nom, String date, List<Competence> comp){
		Get g = new Get();
		StringBuilder str = new StringBuilder();
		for(Competence c:comp){
			str.append(c.getiId()+"-");
		}
		if(str.length()>0){
			str.deleteCharAt(str.length()-1);//enleve le dernier tiret
		}
		String vraiStr=Connection.url+"UpdateEvaluation?id="+id+"&nom="+nom+"&date="+date+"&listcomp="+str.toString();
		System.out.println(Connection.url+"UpdateEvaluation?id="+id+"&nom="+nom+"&date="+date+"&listcomp="+str.toString());
		System.out.println(vraiStr);
		g.execute(vraiStr);
	}

	//Récupérer les compétences d'une évaluation : 
	public static ArrayList<Competence> getCompetenceByEvaluation(int evalid){
		Get g = new Get();
		ArrayList<Competence> complist = new ArrayList<Competence>();
		g.execute(Connection.url + "GetCompetenceByEvaluation?id="+evalid);

		try {
			String str = g.get();

			JSONObject reader = new JSONObject(str);
			JSONArray users = reader.getJSONArray("competences");

			if (users != null) {
				int len = users.length();
				for (int i = 0; i < len; i++) {
					JSONObject elem = users.getJSONObject(i);
					
					String nom = elem.getString("cnom");
					int mat = elem.getInt("imatiere");
					Matiere matiere = Matiere.getMatiereById(mat);
					
					int id = elem.getInt("iid");

					complist.add(new Competence(id, nom, matiere));
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return complist;
	}

	// Mettre à jour une évaluation
	public static void updateEval(Evaluation e) {
		updateEval(e.iId, e.sNom, e.giveNiceDate(), e.comp);
		
	}
	
	// TRI DES EVALUATIONS DU PLUS RECENT AU MOINS RECENT

	@Override
	public int compareTo(Evaluation e) {
		return getcDate().compareTo(e.getcDate());
	}
}
