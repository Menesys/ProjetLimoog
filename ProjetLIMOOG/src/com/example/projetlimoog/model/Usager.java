package com.example.projetlimoog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projetlimoog.connection.Connection;
import com.example.projetlimoog.connection.Get;

public class Usager {

	private int iId;
	private String cNom;
	private String cPrenom;

	private List<Appartenir> groupes = new ArrayList<Appartenir>();
	private List<Note> notes = new ArrayList<Note>();

	public Usager() {
	}

	public Usager(int id, String nom, String prenom) {
		iId = id;
		cNom = nom;
		cPrenom = prenom;
	}

	// Getters and setters

	public int getiId() {
		return iId;
	}

	public void setiId(int iId) {
		this.iId = iId;
	}

	public String getcNom() {
		return cNom;
	}

	public void setcNom(String cNom) {
		this.cNom = cNom;
	}

	public String getcPrenom() {
		return cPrenom;
	}

	public void setcPrenom(String cPrenom) {
		this.cPrenom = cPrenom;
	}

	public List<Appartenir> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Appartenir> groupes) {
		this.groupes = groupes;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	// add / remove Note and Groupe

	public void addNote(Note n) {
		notes.add(n);
	}

	public void removeNote(Note n) {
		notes.remove(n);
	}

	public void addGroupe(Appartenir a) {
		groupes.add(a);
	}

	public void removeGroupe(Appartenir a) {
		groupes.remove(a);
	}

	public Note getNoteByCompetenceAndEvaluation(Competence c, Evaluation e) {
		for (Note n : notes)
			if (n.getiCompetence().equals(c) && n.getiEvaluation().equals(e)) {
				System.out.println("note trouvé a " + n.getiNiveau());
				return n;
			}

		return null;
	}

	//surcharge de la méthode toString : 
	@Override
	public String toString() {
		return cPrenom + " " + cNom;

	}

	//METHODES BDD
	
	//récupérer tous les usagers :
	public static ArrayList<Usager> getAllUsager() {

		//LISTE DES ETUDIANTS !!
		
		Get g = new Get();
		ArrayList<Usager> userlist = new ArrayList<Usager>();
		g.execute(Connection.url + "AllUsager");

		try {
			String str = g.get();

			JSONObject reader = new JSONObject(str);
			JSONArray users = reader.getJSONArray("usagers");

			if (users != null) {
				int len = users.length();
				for (int i = 0; i < len; i++) {
					JSONObject elem = users.getJSONObject(i);
					String nom = elem.getString("cnom");
					String prenom = elem.getString("cprenom");
					int id = elem.getInt("iid");

					userlist.add(new Usager(id, nom, prenom));
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

	//récupérer un usager en fonction de son id :
	public static Usager getUsagerById(int n) {

		Get g = new Get();
		g.execute(Connection.url + "GetUsagerById?id=" + n);

		try {
			String str = g.get();

			JSONObject elem = new JSONObject(str);

			if (elem != null) { {
					
					String nom = elem.getString("cnom");
					String prenom = elem.getString("cprenom");
					int id = elem.getInt("iid");

					return new Usager(id, nom, prenom);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	//récupérer un usager en fonction de son login/mot de passe
	public static Usager getLoginPassword(String l, String p){
		
		Get g = new Get();
		g.execute(Connection.url + "LoginPassword?login=" + l + "&password="+p);

		try {
			String str = g.get();

			JSONObject elem = new JSONObject(str);

			if (elem != null) { {
					
					String nom = elem.getString("cnom");
					String prenom = elem.getString("cprenom");
					int id = elem.getInt("iid");

					return new Usager(id, nom, prenom);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	//récupérer la liste des compétences d'un utilisateur :
	public static ArrayList<Competence> getCompetenceByPersonne(int pid){
		Get g = new Get();
		ArrayList<Competence> complist = new ArrayList<Competence>();
		g.execute(Connection.url + "GetCompetenceByPersonne?id="+pid);

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
}
