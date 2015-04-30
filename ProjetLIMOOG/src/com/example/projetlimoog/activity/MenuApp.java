package com.example.projetlimoog.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Current;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Matiere;
import com.example.projetlimoog.model.Usager;

public class MenuApp extends Activity implements OnClickListener,
		OnItemSelectedListener {

	//éléments graphiques
	Button preparerEval = null;
	Button evaluer = null;
	Button evalSimple = null;
	Button bilan = null;

	Spinner spinEval = null;
	
	TextView nameUser = null;
	
	//déclarations
	int itempos;
	
	ArrayAdapter<Evaluation> adapterEval = null;
	ArrayList<Evaluation> listEval = Evaluation.getAllEval();
	ArrayList<Competence> allUserComp = Usager.getCompetenceByPersonne(Current.getCurrentUsager().getiId());
	
	Evaluation nulleval = new Evaluation();


	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menulayout);

		nameUser = (TextView) findViewById(R.id.WelcomeAndUser);
		nameUser.setText("Bienvenue " + Current.getCurrentUsager().getcPrenom()+" "+Current.getCurrentUsager().getcNom()); 
		// ^ Récupérer l'information d'une autre activité.
	}

	@SuppressLint("NewApi") public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Menu");
		
		//case eval simple, toutes les comp.
		nulleval.setComp(allUserComp);
		nulleval.setsNom("Evaluation Simple");
		nulleval.setcDate(new Date());
		
		//éléments graphiques
		preparerEval = (Button) findViewById(R.id.preparerEval);
		preparerEval.setOnClickListener(this);

		evaluer = (Button) findViewById(R.id.evaluer);
		evaluer.setOnClickListener(this);
		
		evalSimple = (Button) findViewById(R.id.evalSimple);
		evalSimple.setOnClickListener(this);

		bilan = (Button) findViewById(R.id.bilan);
		bilan.setOnClickListener(this);

		// relatif au spinner
		spinEval = (Spinner) findViewById(R.id.spinEval);
		spinEval.setOnItemSelectedListener(this);

		adapterEval = new ArrayAdapter<Evaluation>(this,android.R.layout.simple_spinner_item, listEval);
		adapterEval.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinEval.setAdapter(adapterEval);

		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) { // changer d'activité selon le bouton choisi

		case R.id.preparerEval:
			intent = new Intent(MenuApp.this, PreparerEval.class);
			startActivity(intent);
			break;

		case R.id.evalSimple:
			Current.setEvalEvaluee(nulleval);
			Evaluation.createEval(nulleval);
			intent = new Intent(MenuApp.this, Evaluer.class);
			
			startActivity(intent);
			break;

		case R.id.bilan:
			intent = new Intent(MenuApp.this, Bilan.class);
			startActivity(intent);
			break;
			
		case R.id.evaluer:
			intent = new Intent(MenuApp.this, Evaluer.class);
			Current.setEvalEvaluee((Evaluation)spinEval.getAdapter().getItem(itempos));
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,long arg3) {
		itempos = pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
	switch (item.getItemId()) {
//		case R.id.action_eval_filter:
//		// Comportement du bouton settings
//		return true;
	
		case R.id.action_refresh:
		// Comportement du bouton "Rafraichir"
		finish();
		startActivity(getIntent());
		return true;
		}
	return false;
	}

}