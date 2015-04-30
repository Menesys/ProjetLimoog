package com.example.projetlimoog.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Current;
import com.example.projetlimoog.model.Usager;

public class MainActivity extends Activity implements OnClickListener {

	String loginbdd;
	String pwdbdd;

	Button loginButton = null;
	EditText pwdField = null;
	EditText loginField = null;

	Usager u = new Usager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);

		pwdField = (EditText) findViewById(R.id.pwdField);
		loginField = (EditText) findViewById(R.id.loginField);
		loginField.setSingleLine(); 								// empêche le retour a la ligne
		
		//loginField.setKeyListener(null);  						//Rendre un textview non-modifiable

//		loginField.setText("admin"); 								// A SUPPRIMER (préremplissage)
//		pwdField.setText("admin");
	}

	@SuppressLint("NewApi") public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
//		ActionBar actionBar = getActionBar();
		return true;
	}

	@Override
	public void onClick(View arg0) {
		String login = loginField.getText().toString();
		String passw = pwdField.getText().toString();
		u = Usager.getLoginPassword(login, passw);

		if (u != null) {

			Intent intent = new Intent(MainActivity.this, MenuApp.class); // change l'activité
			Current.setCurrentUsager(u);
			startActivity(intent);
		}

		else {

			Toast.makeText(MainActivity.this,"Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
			pwdField.setText("");
		}
	}

}
