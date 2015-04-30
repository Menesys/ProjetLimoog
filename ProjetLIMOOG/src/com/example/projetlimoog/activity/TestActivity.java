package com.example.projetlimoog.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Matiere;
import com.example.projetlimoog.model.Usager;

public class TestActivity extends Activity implements OnItemSelectedListener {
	Spinner selectEtudiant = null;
	ArrayAdapter<Usager> adapterEtudiant = null;
	ListView list=null;
	TestCustomAdaper adapt;
	Evaluation eval;
	
	List<Competence> comps;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.evaluerlayout);

		initSpinner();
		initRadioGroup();
	}

	private void initRadioGroup() {
		comps = new ArrayList<Competence>();

		// (A SUPPRIMER DANS LA VERSION FINALE)
		eval = new Evaluation(1, new Date(), "eval1");
		Matiere mat = new Matiere(1, "test");
		Competence comp1 = new Competence(1, "Test1", mat);
		Competence comp2 = new Competence(2, "Test2", mat);
		Competence comp3 = new Competence(3, "Test3", mat);
		Competence comp4 = new Competence(4, "Test4", mat);
		comps.add(comp1);
		comps.add(comp2);
		comps.add(comp3);
		comps.add(comp4);
		
		
		

	}

	private void initSpinner() {
		List<Usager> listEtudiant = new ArrayList<Usager>();

		// (A SUPPRIMER DANS LA VERSION FINALE)
		Usager user = new Usager(1, "Pan", "Peter");
		Usager user2 = new Usager(2, "Baba", "Ali");
		Usager user3 = new Usager(3, "Test", "Test");
		Usager user4 = new Usager(4, "Eleve", "Bon");
		listEtudiant.add(user);
		listEtudiant.add(user2);
		listEtudiant.add(user3);
		listEtudiant.add(user4);

		selectEtudiant = (Spinner)findViewById(R.id.selectEtudiant);

		// Affecter la liste d'Etudiants dans le spinner selectEtudiant
		adapterEtudiant = new ArrayAdapter<Usager>(this,
				android.R.layout.simple_spinner_item, listEtudiant);
		adapterEtudiant
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectEtudiant.setAdapter(adapterEtudiant);
		selectEtudiant.setOnItemSelectedListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> a, View v, int pos, long id) {

		// décocher tous les radiobuttons du radiogroup
		list= (ListView)findViewById(R.id.listeCompetence);
		adapt=new TestCustomAdaper(this, comps,eval,(Usager) selectEtudiant.getAdapter().getItem(pos));
		list.setAdapter(adapt);
		//updateCheck();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
