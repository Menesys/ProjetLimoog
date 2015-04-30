package com.example.projetlimoog.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Current;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Matiere;

public class Bilan extends Activity {

	Spinner selectEtud = null;
	Button enregistrerBtn = null;

	CustomAdapterBilan monadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bilanlayout);

		ArrayList<Competence> listComp = new ArrayList<Competence>();

		Matiere m = new Matiere(1, "informatique");
		Competence c = new Competence(1, "Comp1", m);
		Competence d = new Competence(2, "Comp2", m);
		Competence e = new Competence(3, "Comp3", m);

		listComp.add(c);
		listComp.add(d);
		listComp.add(e);

		monadapter = new CustomAdapterBilan(listComp, this);

		ListView list = (ListView) findViewById(R.id.listeComp);
		list.setAdapter(monadapter);
	}

	@SuppressLint("NewApi") @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Évaluer"); 
		//
		// TextView bilan=(TextView) findViewById(R.id.bilan);
		// Get g=new Get();
		// //g.execute("http://data.fwft.fr/");
		// g.execute("http://192.168.0.22:8080/test/AllComp");
		//
		// try {
		// String str=g.get();
		// System.out.println(str);
		// JSONObject reader = new JSONObject(str);
		// JSONArray comps=reader.getJSONArray("competences");
		// JSONObject elem=comps.getJSONObject(0);
		//
		// bilan.setText(elem.getString("cnom")+" "+elem.getInt("iid"));
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return true;
	}

}
