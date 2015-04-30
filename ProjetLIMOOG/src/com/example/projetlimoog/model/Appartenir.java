package com.example.projetlimoog.model;

import java.util.Date;

public class Appartenir {

	private Date dDebut;
	private Date dFin;
	private Usager iUsager;
	private Groupe iGroupe;
	
	public Appartenir(){}
	
	public Appartenir(Date debut, Date fin, Usager usager, Groupe groupe){
		dDebut = debut;
		dFin = fin;
		iUsager = usager;
		
		if(usager!=null){
			usager.addGroupe(this);
		}
		iGroupe = groupe;
		
		if(groupe!=null){
			groupe.addUsager(this);
		}
	}

	//Getters and Setters
	
	//Date début
	public Date getdDebut() {
		return dDebut;
	}

	public void setdDebut(Date dDebut) {
		this.dDebut = dDebut;
	}

	//Date fin
	public Date getdFin() {
		return dFin;
	}

	public void setdFin(Date dFin) {
		this.dFin = dFin;
	}

	//iUsager
	public Usager getiUsager() {
		return iUsager;
	}

	public void setiUsager(Usager iUsager) {
		if(this.iUsager!=null){
			this.iUsager.removeGroupe(this);
		}
		this.iUsager = iUsager;
		if(this.iUsager!=null){
			this.iUsager.addGroupe(this);
		}
	}

	//Groupe
	public Groupe getiGroupe() {
		return iGroupe;
	}

	public void setiGroupe(Groupe iGroupe) {
		if(this.iGroupe!=null){
			this.iGroupe.removeUsager(this);
		}
		this.iGroupe = iGroupe;
		if(this.iGroupe!=null){
			this.iGroupe.addUsager(this);
		}
	}
	
	
}
