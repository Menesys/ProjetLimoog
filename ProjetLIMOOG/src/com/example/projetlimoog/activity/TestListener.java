package com.example.projetlimoog.activity;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Note;
import com.example.projetlimoog.model.Usager;

public class TestListener implements OnCheckedChangeListener {

	private Usager user;
	private Evaluation evaluation;
	private Competence competence;
	private RadioGroup group;
	
	public TestListener(Evaluation evaluation, Competence c,Usager u) {
		this.evaluation=evaluation;
		competence=c;
		user=u;
	}

	@Override
	public void onCheckedChanged(RadioGroup g, int checkedId) {
		if (user != null) {
			
		int lvl;
		switch (checkedId) { // Notation
		case R.id.TB:
			//n.setiNiveau(4);
			lvl=4;
			break;
		case R.id.B:
			//n.setiNiveau(3);
			lvl=3;
			break;
		case R.id.P:
			//n.setiNiveau(2);
			lvl=2;
			break;
		case R.id.NA:
			//n.setiNiveau(1);
			lvl=1;
			break;
		default: 
			return;
		}
		
			
			Note n = user.getNoteByCompetenceAndEvaluation(competence,evaluation);

			if (n == null) {
				n = new Note(user, competence, evaluation, 0);
			}
			System.out.println("set au niveau "+lvl);
			n.setiNiveau(lvl);
		}

	}
	
	public void setUser(Usager u){
		user=u;
	}
	
	public void setGroup(RadioGroup r){
		group=r;
	}
	
	public void updateCheck(){
		if(group==null)
			return;
		int lvl=-1;
		Note n = user.getNoteByCompetenceAndEvaluation(competence,evaluation);

		if (n != null) {
			lvl=n.getiNiveau();
		}
		System.out.println("update au niveau "+lvl);

		switch (lvl) {
		case 1:
			group.check(R.id.NA);
			break;
		case 2:
			group.check(R.id.P);
			break;
		case 3:
			group.check(R.id.B);
			break;
		case 4:
			group.check(R.id.TB);
			break;
		default:
			group.check(-1);
		}
	}
	
	public void clearCheck(){
		if (group!=null){
			group.clearCheck();
		}
	}

}
