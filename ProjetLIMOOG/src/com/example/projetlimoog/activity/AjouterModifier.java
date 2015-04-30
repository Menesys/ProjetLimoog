package com.example.projetlimoog.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Current;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Usager;

public class AjouterModifier extends Activity implements OnItemClickListener, OnClickListener {

	//éléments graphiques
	TextView username = null;
	TextView evalName = null;
	TextView evalDate = null;
	TextView evalComp = null;

	EditText evalNameEdit = null;
	EditText evalDateEdit = null;
	
	Button saveButton = null;

	ListView listEvalComp = null;

	//déclarations
	Evaluation currentEval = Current.getCurrentEvaluation();
	List<Competence> listeComp = Usager.getCompetenceByPersonne(Current.getCurrentUsager().getiId());
	List<Competence> listeCompFinale = Evaluation.getCompetenceByEvaluation(currentEval.getiId());
	
	
	@SuppressLint("SimpleDateFormat") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajoutermodifierlayout);
		
		//éléments graphiques
		saveButton = (Button) findViewById(R.id.saveButton1);
		saveButton.setOnClickListener(this);
		
		evalNameEdit = (EditText) findViewById(R.id.editTextName);
		evalDateEdit = (EditText) findViewById(R.id.editTextDate);

		username = (TextView) findViewById(R.id.username);
		evalName = (TextView) findViewById(R.id.evalName);
		evalDate = (TextView) findViewById(R.id.evalDate);
		evalComp = (TextView) findViewById(R.id.evalComp);

		listEvalComp = (ListView) findViewById(R.id.listEvalComp);

		//Affiche le nom de l'utilisateur :
		username.setText(Current.getCurrentUsager().toString());
		
		// Liste avec nom + checkbox (special adapter) :
		ArrayAdapter<Competence> adapter = new ArrayAdapter<Competence>(this, android.R.layout.simple_list_item_multiple_choice, listeComp);
		listEvalComp.setAdapter(adapter);
		listEvalComp.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// Si l'évaluation est modifiée :
		// On récupère le nom et la date
		if (!(Current.isNouvelleEvaluation())) {
			
			evalNameEdit.setText(currentEval.getsNom());
			evalNameEdit.setSingleLine(); //empêche les retours a la ligne
			
			evalDateEdit.setText(currentEval.giveNiceDate().toString());
			evalDateEdit.setSingleLine();
		} 
		
		// Si c'est une nouvelle évaluation :
		// La date prends la valeur de la date du jour
		// Les champs sont vides.
		else {
			
			Date d = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.format(d);
			
			evalNameEdit.setText("");
			evalNameEdit.setSingleLine(); //empêche les retours a la ligne
			
			evalDateEdit.setText(formatter.format(d).toString());
			evalDateEdit.setSingleLine();
		}
		
		// Cocher les compétences de l'évaluation :
		// On cherche si le nom de l'item dans la liste de l'évaluation est le même que l'item de la liste des compétences
		// Si oui, on le coche.
		for (int i=0; i<adapter.getCount();i++){
			Competence c=(Competence) listEvalComp.getItemAtPosition(i);
			for (int j=0; j<listeCompFinale.size();j++){
				if (c.getcNom().equals(listeCompFinale.get(j).getcNom())){
					listEvalComp.setItemChecked(i, true);
				}
			}
		}

		listEvalComp.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		
		// Donner listeCompFinale a l'évaluation : 
		// On récupère le nom et la date et on appelle les méthodes de création/modifiaction.
		
		String nom = evalNameEdit.getText().toString();
		String date = evalDateEdit.getText().toString();
	
		if(Current.isNouvelleEvaluation()){
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
			currentEval.setsNom(evalNameEdit.getText().toString());
			try {
				currentEval.setcDate(format.parse(evalDateEdit.getText().toString()));
			} catch (ParseException e) {
				currentEval.setcDate(new Date());
			}
			currentEval.setComp(listeCompFinale);
			Evaluation.createEval(currentEval);
			//Evaluation.createEval(nom, date, listeCompFinale);
		}
		else{
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
			currentEval.setsNom(evalNameEdit.getText().toString());
			try {
				currentEval.setcDate(format.parse(evalDateEdit.getText().toString()));
			} catch (ParseException e) {
				currentEval.setcDate(new Date());
			}
			currentEval.setComp(listeCompFinale);
			Toast.makeText(AjouterModifier.this," "+listeCompFinale.size(), Toast.LENGTH_SHORT).show();
			Evaluation.updateEval(currentEval);
		}
		this.finish();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int pos, long id) {
		
		// Cocher l'item cliqué :
		// On prends la compétence séléctionnée, on teste si elle est déjà cochée
		// Si oui on la décoche, sinon on la coche.
		
		Competence temp=(Competence) listEvalComp.getItemAtPosition(pos);
		boolean ischecked = listEvalComp.getCheckedItemPositions().get(pos);
		
		if (ischecked){
			listeCompFinale.add(temp);

			Toast.makeText(AjouterModifier.this,"on ajoute "+temp.getcNom(), Toast.LENGTH_SHORT).show();
		} else {
			System.out.println(listeCompFinale.size());
//			listeCompFinale.remove(temp);
			removeCompetenceFinale(temp.getiId());
			System.out.println(listeCompFinale.size());

			Toast.makeText(AjouterModifier.this,"on enlève "+temp.getcNom(), Toast.LENGTH_SHORT).show();
		}
		
		listEvalComp.setItemChecked(pos, ischecked);

	}
	
	public void removeCompetenceFinale(int id){
		for(Competence c: listeCompFinale){
			if(c.getiId()==id){
				listeCompFinale.remove(c);
				return;
			}
				
		}
	}
}