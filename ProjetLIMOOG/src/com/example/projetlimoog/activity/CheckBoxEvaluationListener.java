package com.example.projetlimoog.activity;

import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Note;

public class CheckBoxEvaluationListener implements OnCheckedChangeListener {

	private Competence oComp�tence;
	private Evaluer oEvaluation;
	private RadioGroup oRadioGroup;

	// LG modif
	private TextView oTextView ;
		
	public CheckBoxEvaluationListener(Competence c, Evaluer cc,RadioGroup g, TextView t) {
		oTextView = t;
		oComp�tence = c;
		oEvaluation = cc;
		oRadioGroup = g;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		// LG d�but
		// pour prouver qu'on se trompe d'item � l'enregistrement
		//if (group != null) return ;

			if (oEvaluation.lLoading) {
				// On est en cours de chargement
				// rien � faire, sinon erreur car change la note en se trompant d'item
				return ;
			}
		// LG fin
			
		if (oEvaluation.currentUser != null) {
			
			Note n = oEvaluation.currentUser.getNoteByCompetenceAndEvaluation(oComp�tence,oEvaluation.eval); // si Note n d�j� existante

			if (n == null) { // si la Note n'est pas cr��e on la cr��
				n = new Note(oEvaluation.currentUser, oComp�tence, oEvaluation.eval, 0);
			}
			
			switch (checkedId) { // Notation
			case R.id.TB:
//				Toast.makeText(eval.getApplicationContext(), "TB", Toast.LENGTH_SHORT).show();
				// Note Tr�s Bien - entrer le niveau 4 dans la Note n
				n.setiNiveau(4);
				break;
			case R.id.B:
//				Toast.makeText(eval.getApplicationContext(), "B", Toast.LENGTH_SHORT).show();
				// Note Bien - entrer le niveau 3 dans la Note n
				n.setiNiveau(3);
				break;
			case R.id.P:
//				Toast.makeText(eval.getApplicationContext(), "P", Toast.LENGTH_SHORT).show();
				// Note Passable - entrer le niveau 2 dans la Note n
				n.setiNiveau(2);
				break;
			case R.id.NA:
//				Toast.makeText(eval.getApplicationContext(), "NA", Toast.LENGTH_SHORT).show();
				// Note Non Acquis - entrer le niveau 1 dans la Note n
				n.setiNiveau(1);
				break;
			default: 
//				Toast.makeText(eval.getApplicationContext(), "default", Toast.LENGTH_SHORT).show();
				break;
			}
			
			// LG modif
			Log.v("onCheckedChanged"
				, "Note chang�e en " + n.getiNiveau()
					+ " pour " + oComp�tence.getcNom() 
					+ " de " + oEvaluation.currentUser.getcPrenom()) ;
		}
	}

	//ne devrait pas exister
	public void clear() {										//effacer les cases coch�es.
		oRadioGroup.clearCheck();
		
	}

	//elle non plus 
	public void update() {										//Recocher les cases qui ont �t� not�es par le pass�
	
		int lvl=-1;
		Note n = oEvaluation.currentUser.getNoteByCompetenceAndEvaluation(oComp�tence,oEvaluation.eval); 	// si Note n d�j� existante
		Log.v("update", oEvaluation.currentUser.getcPrenom() + ", " + oComp�tence.getcNom());
		Log.v("update", oTextView.getText().toString());

		if (n != null) {
			lvl=n.getiNiveau();
		}

		switch (lvl) { 											// On check le radiobutton correspondant
		case 1:
			oRadioGroup.check(R.id.NA);
			break;
		case 2:
			oRadioGroup.check(R.id.P);
			break;
		case 3:
			oRadioGroup.check(R.id.B);
			break;
		case 4:
			oRadioGroup.check(R.id.TB);
//			Toast.makeText(eval.getApplicationContext(), "tb", Toast.LENGTH_SHORT).show();
			break;
		default:
			//Toast.makeText(eval.getApplicationContext(), "DEFAULT"+lvl, Toast.LENGTH_SHORT).show();
			oRadioGroup.check(-1);
		}
		
	}
	
//	// LG : pour debug
//	public String getNomCompetence(){
//		return oComp�tence.getcNom() ;
//}

}
