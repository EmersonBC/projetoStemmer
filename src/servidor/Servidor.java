package servidor;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Servidor  extends UnicastRemoteObject implements projetoStemmerTeste.Cliente{
	protected Servidor() throws RemoteException{
		super();
	}

	private static final long serialVersionUID = 1L;

	public void radical(Map<String, Integer> palavras, Map<String, Integer> radicais, String nomeArquivo) throws RemoteException, IOException{
		java.util.Date data = new java.util.Date();
		
		FileWriter arqPalavras = new FileWriter(new File("/home/emerson/Documentos/radicais/palavrasAtingidas/"+ data + " " + nomeArquivo +".log"));
		
		System.out.println(data.toString());
		
		for (Map.Entry<String,Integer> pair : palavras.entrySet()) {
			arqPalavras.write(pair.getKey() +" "+ pair.getValue());
	           arqPalavras.append("\n");
        }
		
		arqPalavras.close();
		
		FileWriter arqRadicais = new FileWriter(new File("/home/emerson/Documentos/radicais/radicaisNaoReconhecidos/"+ data + " "+ nomeArquivo +".log"));
		
		System.out.println(data.toString());
		
		for (Map.Entry<String,Integer> pair : radicais.entrySet()) {
           arqRadicais.write(pair.getKey() +" "+ pair.getValue());
           arqRadicais.append("\n");
        }
		
		arqRadicais.close();	
	}

	public int buscarPalavra(String palavra) throws IOException{
		int quantidade = 0;
		String linha=null;
		
		File baseFolder = new File("/home/emerson/Documentos/radicais/palavrasAtingidas/");  
		
		File[] files = baseFolder.listFiles();
		
		for (int i = 0; i < files.length; i++) {
		    File file = files[i];
		    
		    if (file.getPath().endsWith(".log")) {  
		    	String name = file.getName();  
		    	
		    	BufferedReader in = new BufferedReader(new FileReader("/home/emerson/Documentos/radicais/palavrasAtingidas/"+ name));
		    	
		        while ((linha = in.readLine()) != null){
		        	String[] array = linha.split(" ");
		        	
		        	if(array[0].equals(palavra)){
		        		quantidade++;
		        	}
				}

		    }
		}
		
		if(quantidade!=0){
			return quantidade;
		}
		else{
			return -1;
		}
	}
	
	public void listarPalavras() throws IOException{
		String linha=null;
		Map<String, Integer> hash = new HashMap<String, Integer>();

		File baseFolder = new File("/home/emerson/Documentos/radicais/palavrasAtingidas/");  
		
		File[] files = baseFolder.listFiles();
		
		for (int i = 0; i < files.length; i++) {
		    File file = files[i];
		    
		    if (file.getPath().endsWith(".log")) {  
		    	String name = file.getName();  
		    	
		    	BufferedReader in = new BufferedReader(new FileReader("/home/emerson/Documentos/radicais/palavrasAtingidas/"+ name));
		    	
		        while ((linha = in.readLine()) != null){
		        	String[] array = linha.split(" ");
		        	
		        	if(hash.containsKey(array[0])){
		        		if(hash.get(array[0]) != null)
		        			hash.replace(array[0], hash.get(array[0])+Integer.parseInt(array[1]));
		        	}
		        	else{
		        		hash.put(array[0], Integer.parseInt(array[1]));
		        	}
				}

		    }
		}
		
		FileWriter saida = new FileWriter(new File("/home/emerson/Documentos/radicais/TotalPalavras.log"));
		
		for (Map.Entry<String,Integer> pair : hash.entrySet()) {
	           saida.write(pair.getKey() +" "+ pair.getValue());
	           saida.append("\n");
	    }
		saida.close();
	}

	public void listarRadicais() throws IOException{
		String linha=null;
		Map<String, Integer> hash = new HashMap<String, Integer>();

		File baseFolder = new File("/home/emerson/Documentos/radicais/radicaisNaoReconhecidos/");  
		
		File[] files = baseFolder.listFiles();
		
		for (int i = 0; i < files.length; i++) {
		    File file = files[i];
		    
		    if (file.getPath().endsWith(".log")) {  
		    	String name = file.getName();  
		    	
		    	BufferedReader in = new BufferedReader(new FileReader("/home/emerson/Documentos/radicais/radicaisNaoReconhecidos/"+ name));
		    	
		        while ((linha = in.readLine()) != null){
		        	String[] array = linha.split(" ");
		        	
		        	if(hash.containsKey(array[0])){
		        		if(hash.get(array[0]) != null)
		        			hash.replace(array[0], hash.get(array[0])+Integer.parseInt(array[1]));
		        	}
		        	else{
		        		hash.put(array[0], Integer.parseInt(array[1]));
		        	}
				}

		    }
		}
		
		FileWriter saida = new FileWriter(new File("/home/emerson/Documentos/radicais/TotalRadicais.log"));
		
		for (Map.Entry<String,Integer> pair : hash.entrySet()) {
	           saida.write(pair.getKey());
	           saida.append("\n");
	    }
		saida.close();
	}
}

