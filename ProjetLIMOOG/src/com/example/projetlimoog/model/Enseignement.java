package com.example.projetlimoog.model;

import java.util.Date;

public class Enseignement {

	private Date dDebut;
	private Date dFin;
	private Groupe iGroupe;
	private Intervenant iIntervenant;
	private Matiere iMatiere;
	
	public Enseignement(){}
	
	public Enseignement(Date debut,Date fin,Groupe groupe,Intervenant intervenant,Matiere matiere){
		dDebut=debut;
		dFin=fin;
		iGroupe=groupe;
		iIntervenant=intervenant;
		iMatiere=matiere;
		
		if(iGroupe!=null){
			iGroupe.addEnseignement(this);
		}
		
		if(iIntervenant!=null){
			iIntervenant.addEnseignement(this);
		}
		
		if(iMatiere!=null){
			iMatiere.addEnseignement(this);
		}
	}

	// Getters and setters 
	
	public Date getdDebut() {
		return dDebut;
	}

	public void setdDebut(Date dDebut) {
		this.dDebut = dDebut;
	}

	public Date getdFin() {
		return dFin;
	}

	public void setdFin(Date dFin) {
		this.dFin = dFin;
	}

	public Groupe getiGroupe() {
		return iGroupe;
	}

	public void setiGroupe(Groupe iGroupe) {
		if(this.iGroupe!=null){
			this.iGroupe.removeEnseignement(this);
		}
		this.iGroupe = iGroupe;
		if(this.iGroupe!=null){
			this.iGroupe.addEnseignement(this);
		}
	}

	public Intervenant getiIntervenant() {
		return iIntervenant;
	}

	public void setiIntervenant(Intervenant iIntervenant) {
		if(this.iIntervenant!=null){
			this.iIntervenant.removeEnseignement(this);
		}
		this.iIntervenant = iIntervenant;
		if(this.iIntervenant!=null){
			this.iIntervenant.addEnseignement(this);
		}
	}

	public Matiere getiMatiere() {
		return iMatiere;
	}

	public void setiMatiere(Matiere iMatiere) {
		if(this.iMatiere!=null){
			this.iMatiere.removeEnseignement(this);
		}
		
		this.iMatiere = iMatiere;
		
		if(this.iMatiere!=null){
			this.iMatiere.addEnseignement(this);
		}
	}
	
}
