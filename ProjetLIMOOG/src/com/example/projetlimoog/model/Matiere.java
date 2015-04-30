package com.example.projetlimoog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.projetlimoog.connection.Connection;
import com.example.projetlimoog.connection.Get;

public class Matiere {

	private int iId;
	private String cNom;
	private List<Competence> competences = new ArrayList<Competence>();
	private List<Enseignement> enseignements = new ArrayList<Enseignement>();
	
	public Matiere(){}
	
	public Matiere(int id, String nom){
		iId = id;
		cNom = nom;
	}

	//Getters and setters
	
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

	public List<Competence> getCompetences() {
		return competences;
	}

	public void setCompetences(List<Competence> competences) {
		this.competences = competences;
	}

	public List<Enseignement> getEnseignements() {
		return enseignements;
	}

	public void setEnseignements(List<Enseignement> enseignements) {
		this.enseignements = enseignements;
	}
	
	//add / remove Competence and Enseignement
	
	public void addCompetence(Competence c){
		competences.add(c);
	}
	
	public void removeCompetence(Competence c){
		competences.remove(c);
	}
	
	public void addEnseignement(Enseignement e){
		enseignements.add(e);
	}
	
	public void removeEnseignement(Enseignement e){
		enseignements.remove(e);
	}

	public static Matiere getMatiereById(int n) {

		Get g = new Get();
		g.execute(Connection.url + "GetMatiereById?id=" + n);

		try {
			String str = g.get();

			JSONObject elem = new JSONObject(str);

			if (elem != null) { {
					
					String nom = elem.getString("cnom");
					int id = elem.getInt("iid");

					return new Matiere(id, nom);
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
}
