package com.example.projetlimoog.model;

import java.util.ArrayList;
import java.util.List;

public class Competence {
	
	private int iId;
	private String cNom;
	private Matiere iMatiere;
	private List<Note> notes = new ArrayList<Note>();
	
	public Competence(){}
	
	public Competence(int id, String nom, Matiere matiere){
		iId = id;
		cNom = nom;
		iMatiere = matiere;
		
		if(matiere!=null){ //ajouter à matière la competence
			matiere.addCompetence(this);
		}
	}
	
	//Getters and setters
	
	//Id
	public int getiId() {
		return iId;
	}

	public void setiId(int iId) {
		this.iId = iId;
	}

	//Nom
	public String getcNom() {
		return cNom;
	}

	public void setcNom(String cNom) {
		this.cNom = cNom;
	}

	//Matiere
	public Matiere getiMatiere() {
		return iMatiere;
	}

	public void setiMatiere(Matiere iMatiere) {
		if(this.iMatiere!=null){
			this.iMatiere.removeCompetence(this);
		}
		this.iMatiere = iMatiere;
		if(this.iMatiere!=null){
			this.iMatiere.addCompetence(this);
		}
		
	}

	//Note
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	// add note / remove note
	
	public void addNote(Note n){
		notes.add(n);
	}
	
	public void removeNote(Note n){
		notes.remove(n);
	}
	
	@Override
	public String toString(){
		return cNom;
		
	}
	
	public Note getNoteByUsagerAndEvaluation(Usager u,Evaluation e){
		for(Note no:notes){ //Toutes les Note no dans la liste de Note notes
			if(no.getiUsager().equals(u)&&no.getiEvaluation().equals(e))
				return no;
		}
		return null;
	}
	
}
