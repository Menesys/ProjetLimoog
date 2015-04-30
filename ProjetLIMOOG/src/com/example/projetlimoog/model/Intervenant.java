package com.example.projetlimoog.model;

import java.util.ArrayList;
import java.util.List;

public class Intervenant {

	private int iId;
	private String cNom;
	private String cPrenom;
	private List<Evaluation> evaluations=new ArrayList<Evaluation>();
	private List<Enseignement> enseignements=new ArrayList<Enseignement>();
	
	public Intervenant(){}
	
	public Intervenant(int id, String nom, String prenom){
		iId=id;
		cNom=nom;
		cPrenom=prenom;
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

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public List<Enseignement> getEnseignements() {
		return enseignements;
	}

	public void setEnseignements(List<Enseignement> enseignements) {
		this.enseignements = enseignements;
	}
	
	//add / remove evaluations and enseignements
	//without link -> sans allers-retours pour éviter une boucle infinie.
	
	public void addEvaluation(Evaluation e){
		evaluations.add(e);
		e.addIntervenantWithOutLink(this);
	}
	
	public void removeEvaluation(Evaluation e){
		evaluations.remove(e);
		e.removeIntervenantWithOutLink(this); 
	}
	
	public void addEvaluationWhitOutLink(Evaluation e){
		evaluations.add(e);
	}
	
	public void removeEvaluationWhitOutLink(Evaluation e){
		evaluations.remove(e);
	}
	
	public void addEnseignement(Enseignement e){
		enseignements.add(e);
	}
	
	public void removeEnseignement(Enseignement e){
		enseignements.remove(e);
	}
	
}
