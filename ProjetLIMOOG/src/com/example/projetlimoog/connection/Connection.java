package com.example.projetlimoog.connection;

import java.util.concurrent.ExecutionException;

public class Connection {
	
	public final static String url="http://192.168.0.22:8080/test/"; 		//Appart
	//public final static String url="http://192.168.43.111:8080/test/"; 	//Téléphone Lucas
	//public final static String url="http://192.168.200.1:8080/test/"; 		//ORT

	private static Connection connection = new Connection();

	private Connection() {}

	public static Connection getInstance() {
		return connection;
	}

	public String postURL(String url, String ...params) {
		Post post=new Post();
		String[] strs =new String[params.length+1];
		strs[0]=url;
		
		for(int i=0;i<params.length;i++){
			strs[i+1]=params[i];
		}
		post.execute(strs);
		
		try {
			return post.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getURL(String url) {
		Get get=new Get();
		get.execute(url);
		try {
			return get.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
