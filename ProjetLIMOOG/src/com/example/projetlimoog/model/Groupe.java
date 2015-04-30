package com.example.projetlimoog.model;

import java.util.ArrayList;
import java.util.List;

public class Groupe {

	private int iId;
	private String cNom;
	private int iAnnee;
	private List<Appartenir> usagers=new ArrayList<Appartenir>();
	private List<Enseignement> enseignements=new ArrayList<Enseignement>();
	
	public Groupe(){}
	
	public Groupe(int id,String nom,int annee){
		iId=id;
		cNom=nom;
		iAnnee=annee;
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

	public int getiAnnee() {
		return iAnnee;
	}

	public void setiAnnee(int iAnnee) {
		this.iAnnee = iAnnee;
	}

	public List<Appartenir> getUsagers() {
		return usagers;
	}

	public void setUsagers(List<Appartenir> usagers) {
		this.usagers = usagers;
	}

	public List<Enseignement> getEnseignements() {
		return enseignements;
	}

	public void setEnseignements(List<Enseignement> enseignements) {
		this.enseignements = enseignements;
	}
	
	// Surcharge de la méthode ToString :
		@Override
		public String toString() {
			return cNom;

		}
	
	//add / remove Usager and Enseignement
	
	public void addUsager(Appartenir a){
		usagers.add(a);
	}
	
	public void removeUsager(Appartenir a){
		usagers.remove(a);
	}

	public void addEnseignement(Enseignement e){
		enseignements.add(e);
	}
	
	public void removeEnseignement(Enseignement e){
		enseignements.remove(e);
	}
	
}
