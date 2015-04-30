package com.example.projetlimoog.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Current;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Usager;

@SuppressLint("NewApi") public class Evaluer extends Activity implements OnItemSelectedListener,
		OnClickListener, OnNavigationListener {
	
	Spinner selectEtudiant = null;
	TextView nomAndDate = null;
	Button enregistrerButton = null;
	Button backBtn = null;
	Evaluation eval = Current.getEvalEvaluee();

	// modifications LG
	boolean lLoading = false ;
		
	// Déclaration des variables et listes du spinner
	ArrayAdapter<Usager> adapterEtudiant = null;
	CustomAdapter monadapter;

	// liste d'usager
	List<Usager> listEtudiant=Usager.getAllUsager();
	
	Usager currentUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluerlayout);

		// liste de compétences évaluées :
		List<Competence> listComp = new ArrayList<Competence>();
		
		// La liste actuelle de compétences prends la valeur de la liste de compétences de current eval
		if(eval.getComp()!=null && eval.getComp().size()!=0){
			listComp=eval.getComp();
		} else {
			listComp = Evaluation.getCompetenceByEvaluation(eval.getiId());
		}
		

		monadapter = new CustomAdapter(this, listComp);
		
		ListView list = (ListView) findViewById(R.id.listeCompetence);
		list.setAdapter(monadapter);
	}

	@SuppressLint("NewApi") @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		//Barre d'action
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setTitle("Évaluer"); 
		
		// Filtres ...
		ArrayList<String> listEvalFilter = new ArrayList<String>();
		listEvalFilter.add("Tri alphabétique");
		listEvalFilter.add("Tri par date");
		listEvalFilter.add("Tri par moins évalués");
		listEvalFilter.add("Tri par moins réussies");
		
//		ArrayList<String> listUserFilter = new ArrayList<String>();
//		listEvalFilter.add("Tri apprenants...");
//		listEvalFilter.add("Tri alphabétique");
//		listEvalFilter.add("Tri par date");
//		listEvalFilter.add("Tri par moins évalués");
//		listEvalFilter.add("Tri par moins réussies");
		
		ArrayAdapter<String> adapterEvalFilter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listEvalFilter);
//		ListViewAdapter adapter1 = new ListViewAdapter(Evaluer.this);
//        actionBar.setListNavigationCallbacks(adapter1,
//                new OnNavigationListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(int itemPosition,
//                            long itemId) {
//                        Toast.makeText(Evaluer.this,
//                                "coucou", Toast.LENGTH_SHORT)
//                                .show();
//                        return false;
//                    }
//                });

//		ArrayAdapter<String> adapterUserFilter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listUserFilter);
		
		actionBar.setListNavigationCallbacks(adapterEvalFilter, this);
//		actionBar.setListNavigationCallbacks(adapterUserFilter, this);
		
		nomAndDate = (TextView) findViewById(R.id.nomAndDate);
		nomAndDate.setText(Html.fromHtml("<u>"+eval.getsNom()+" - "+eval.giveNiceDate()+"</u>"));
//		nomAndDate.setText(eval.getsNom()+" - "+eval.giveNiceDate());
		
		enregistrerButton = (Button) findViewById(R.id.enregistrerButton);
		enregistrerButton.setOnClickListener(this);
		
		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);

		selectEtudiant = (Spinner) findViewById(R.id.selectEtudiant);
		selectEtudiant.setOnItemSelectedListener(this);

		// Affecter la liste d'Etudiants dans le spinner selectEtudiant
		adapterEtudiant = new ArrayAdapter<Usager>(this,
				android.R.layout.simple_spinner_item, listEtudiant);
		adapterEtudiant
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectEtudiant.setAdapter(adapterEtudiant);

		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> a, View v, int pos, long id) {

			// usager sélectionné
			Usager temp = (Usager) selectEtudiant.getAdapter().getItem(pos);

			if (currentUser == null || !(currentUser.equals(temp))) {
				currentUser = temp;
				monadapter.updateNote();
			}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {

		case R.id.backBtn: //Fermer sans enregistrer
			this.finish();
			break;

		case R.id.evalSimple: //Sauvegarder évaluation TODO
			Toast.makeText(Evaluer.this,"Données sauvegardées", Toast.LENGTH_SHORT).show();
			//Evaluation.updateEval(eval.getiId(), eval.getsNom(), eval.getcDate().toString(), eval.getComp());
			break;
		}
	}

	public void onBackPressed(){
		
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

			alertDialogBuilder.setTitle("Quitter");
			alertDialogBuilder.setMessage("Quitter sans enregistrer ?");
			alertDialogBuilder.setCancelable(true);

			alertDialogBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() { //Annulation de la boîte de dialogue
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
			});
			alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() { // Suppression d'une evaluation
						public void onClick(DialogInterface dialog, int id) {
							finish();
						}
			});

			alertDialogBuilder.show();
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}