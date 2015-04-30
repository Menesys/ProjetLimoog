package com.example.projetlimoog.model;

public class Current {

	private static Usager currentUsager; //currentUsager

	public static Usager getCurrentUsager() {
		return currentUsager;
	}

	public static void setCurrentUsager(Usager currentUsager) {
		Current.currentUsager = currentUsager;
	}
	
	
	
	private static Evaluation currentEvaluation; //currentEvaluation
	
	public static Evaluation getCurrentEvaluation() {
		return currentEvaluation;
	}

	public static void setCurrentEvaluation(Evaluation currentEvaluation) {
		Current.currentEvaluation = currentEvaluation;
	}

	
	
	public static Competence currentCompetence; //currentCompetence

	public static Competence getCurrentCompetence() {
		return currentCompetence;
	}

	public static void setCurrentCompetence(Competence currentCompetence) {
		Current.currentCompetence = currentCompetence;
	}
	
	
	
	public static Evaluation evalEvaluee;
	
	public static Evaluation getEvalEvaluee() {
		return evalEvaluee;
	}

	public static void setEvalEvaluee(Evaluation evalEvaluee) {
		Current.evalEvaluee = evalEvaluee;
	}

	
	
	public static boolean nouvelleEvaluation;

	public static boolean isNouvelleEvaluation() {
		return nouvelleEvaluation;
	}

	public static void setNouvelleEvaluation(boolean nouvelleEvaluation) {
		Current.nouvelleEvaluation = nouvelleEvaluation;
	}
	
}
