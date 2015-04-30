package com.example.projetlimoog.activity;

/*
 * Faire en sorte de voir les cases coch�es dans Evaluer; ----------------- V
 * Faire des nouvelles classes pour r�peter les �l�ments graphiques-------- V
 * Trouver comment faire les filtres--------------------------------------- ~
 * Trouver comment passer des infos d'une activit� a une autre------------- V
 * Faire la classe Bilan et son layout------------------------------------- X
 * Connexion avec la BDD--------------------------------------------------- V
 * */

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Current;
import com.example.projetlimoog.model.Evaluation;

public class PreparerEval extends Activity implements OnItemClickListener,
		OnClickListener, OnItemLongClickListener, OnItemSelectedListener {

	// �l�ments graphiques
	ImageButton nouveauButton = null;
	
	ListView listViewEval = null;
	
	Spinner classeSpinner = null;
	Spinner dateSpinner = null;
	
	// d�clarations
	int position;
	ArrayList<Evaluation> listeEval = Evaluation.getAllEval(); 	// r�cup�re la liste des �valuations dans la base de donn�es
//	ArrayAdapter<Groupe> adapterGroupe = null;
//	List<Groupe> listeGroupe = new ArrayList<Groupe>();
	CustomAdapterListeEval monadapter; 							//adapter de la liste des �valuation permettant d'afficher l'�valuation et la date
	
//	Groupe g = new Groupe(1,"test",2015);
//	Groupe g2 = new Groupe(2,"testvvv", 2014);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preparerlayout);

		// �l�ments graphiques
		nouveauButton = (ImageButton) findViewById(R.id.nouveauButton);
		nouveauButton.setOnClickListener(this);
		
		listViewEval = (ListView) findViewById(R.id.listEval);
		
//		classeSpinner = (Spinner) findViewById(R.id.classeSpinner);
//		classeSpinner.setOnItemClickListener(this);
//
//		dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
//		dateSpinner.setOnItemSelectedListener(this);
//		
//		listeGroupe.add(g);
//		listeGroupe.add(g2);
//
//		adapterGroupe = new ArrayAdapter<Groupe>(this,android.R.layout.simple_spinner_item, listeGroupe);
//		adapterGroupe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		classeSpinner.setAdapter(adapterGroupe);
		

		Collections.sort(listeEval);
		
		monadapter = new CustomAdapterListeEval(listeEval, this);
		
		listViewEval.setAdapter(monadapter);
		
		// Clic court : modifier une �valuation
		listViewEval.setOnItemClickListener(this);
		// Clic long : supprimer une �valuation
		listViewEval.setOnItemLongClickListener(this);
	}

	@SuppressLint("NewApi") @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Pr�parer �valuation"); 
		return true;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		
		// Clic long - supprimer une �valuation :
		
		position = pos;
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setTitle("Supprimer ?");
		alertDialogBuilder.setMessage("Voulez-vous supprimer les choix s�l�ction�s ?");
		alertDialogBuilder.setCancelable(true);

		alertDialogBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() { //Annulation de la bo�te de dialogue
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
		});
		alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() { // Suppression d'une evaluation
					public void onClick(DialogInterface dialog, int id) {
						Evaluation e = (Evaluation) listViewEval.getAdapter().getItem(position);
						int n = e.getiId();
						Evaluation.deleteEval(n);
					}
		});

		alertDialogBuilder.show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

		// Clic court - modifier une �valuation :
		
		Intent intent = new Intent(PreparerEval.this, AjouterModifier.class);
		Current.setNouvelleEvaluation(false);
		Current.setCurrentEvaluation((Evaluation) listViewEval.getAdapter().getItem(pos));

		startActivity(intent);

	}

	@Override
	public void onClick(View v) {

		// Cr�er une nouvelle �valuation (clic sur le bouton "nouveau") :
		
		switch (v.getId()) {
		case R.id.nouveauButton:
			Intent intent = new Intent(PreparerEval.this, AjouterModifier.class);
			Current.setNouvelleEvaluation(true);
			Current.setCurrentEvaluation(new Evaluation());
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

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