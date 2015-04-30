package com.example.projetlimoog.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Get extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... args) {
		String str="";
		System.setProperty("http.keepAlive", "false");
		BufferedReader reader = null;
		HttpURLConnection connexion = null;
		try {
		  // On a envoyé les données à une adresse distante
		  URL url = new URL(args[0]);
		  connexion = (HttpURLConnection) url.openConnection();
		  connexion.setRequestMethod("GET");
		  connexion.setDoOutput(false);

		  // On lit la réponse ici
		  reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		  String ligne;

		  // Tant que « ligne » n'est pas null, c'est que le flux n'a pas terminé d'envoyer des informations
		  while ((ligne = reader.readLine()) != null) {
//		    System.out.println(ligne);
		    str+=ligne+"\n";
		  }
		} catch (Exception e) {
		  e.printStackTrace();
		} finally {
		  try{reader.close();}catch(Exception e){}
		}
		return str;
	}

}
