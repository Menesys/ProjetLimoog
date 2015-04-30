package com.example.projetlimoog.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class Post extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... args) {
		String str="";
		System.setProperty("http.keepAlive", "false");
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		HttpURLConnection connexion = null;
		try {
			String don="";
			for(int i=1;i<args.length;i+=2){
				don+=args[i]+"="+args[i+1]+"&";
			}
			if(don!="")
				don=don.substring(0,don.length()-1);

		  // On a envoyé les données à une adresse distante
		  URL url = new URL(args[0]);
		  connexion = (HttpURLConnection) url.openConnection();
		  connexion.setRequestMethod("POST");
		  connexion.setDoOutput(true);
		  
		  // On envoie la requête ici
		  writer = new OutputStreamWriter(connexion.getOutputStream());

		  // On insère les données dans notre flux
		  writer.write(don);

		  // Et on s'assure que le flux est vidé
		  writer.flush();

		  // On lit la réponse ici
		  reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		  String ligne;

		  // Tant que « ligne » n'est pas null, c'est que le flux n'a pas terminé d'envoyer des informations
		  while ((ligne = reader.readLine()) != null) {
		    str+=ligne+"\n";
		  }
		} catch (Exception e) {
		  e.printStackTrace();
		} finally {
		  try{writer.close();}catch(Exception e){}
		  try{reader.close();}catch(Exception e){}
		}
		return str;
	}

}
