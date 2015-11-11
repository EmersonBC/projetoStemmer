package projetoStemmerTeste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import projetoStemmerTeste.Cliente;
import ptstemmer.Stemmer;
import ptstemmer.Stemmer.StemmerType;
import ptstemmer.exceptions.PTStemmerException;

public class Main {

	public static void main(String[] args) throws IOException, PTStemmerException, NotBoundException, SQLException {
		Stemmer stemmer = null;
		String algo = null;
		String rad;
		int Npalavras;
		int Nacertos;
		long tempoInicio;
		long tempoTotal;
		
		Cliente cliente = (Cliente) Naming.lookup("rmi://localhost/radical");
		
		Map<String, Integer> palavras = new HashMap<String, Integer>();
		Map<String, Integer> radicais = new HashMap<String, Integer>();
		
		//FileWriter saida = new FileWriter(new File("saida.log"));
		
		for(int x=0; x<3; x++){
				if(x==0){
					algo = "ORENGO";
					stemmer = Stemmer.StemmerFactory(StemmerType.valueOf(algo));
				}
				if(x==1){
					algo = "PORTER";
					stemmer = Stemmer.StemmerFactory(StemmerType.valueOf(algo));
				}
				if(x==2){
					algo = "SAVOY";
					stemmer = Stemmer.StemmerFactory(StemmerType.valueOf(algo));
				}
				
				Npalavras = 0;
				Nacertos = 0;
				tempoTotal = 0;
				
				palavras.clear();
				
				String linha=null;
				String linhaPalavras=null;
				String linhaRadicais=null;  
				
				File baseFolder = new File("/home/emerson/Documentos/stemmer/");  
				
				File[] files = baseFolder.listFiles();  
				
				for (int i = 0; i < files.length; i++) {  
				    File file = files[i];
				    
				    if (file.getPath().endsWith(".txt")) {  
				    	String name = file.getName();  
				    	
				    	BufferedReader in = new BufferedReader(new FileReader("/home/emerson/Documentos/stemmer/"+ name));
				    	
				        while ((linha = in.readLine()) != null){   
							linha = linha.replace(".","");
							linha = linha.replace(",","");
							linha = linha.replace(":","");
							linha = linha.replace(";","");
							linha = linha.replace("(","");
							linha = linha.replace(")","");
							linha = linha.replace("?","");
							
							String[] array = linha.split(" ");
							
							for(int j=0; j< array.length; j++){
								
								if(array[j].isEmpty()!=true){
									
									BufferedReader palavrasIN = new BufferedReader(new FileReader("voc.txt"));
									
									while((linhaPalavras = palavrasIN.readLine()) != null){
										if(array[j].equals(linhaPalavras)){
											if(palavras.containsKey(linhaPalavras)){
												if(palavras.get(linhaPalavras)!=null)
													palavras.replace(linhaPalavras, palavras.get(linhaPalavras)+1);
											}
											else{
												palavras.put(linhaPalavras, 1);
												
												Npalavras++;

												tempoInicio = System.currentTimeMillis();
												rad = stemmer.getWordStem(array[j]);
												tempoTotal = ((System.currentTimeMillis()-tempoInicio) + tempoTotal);
												
												BufferedReader radicaisIN = new BufferedReader(new FileReader("output.txt"));
											
												while ((linhaRadicais = radicaisIN.readLine()) != null){
													if(rad.equals(linhaRadicais)){
														Nacertos++;
														break;
													}
													else{
														if(radicais.containsKey(linhaRadicais)){
															if(palavras.get(linhaRadicais)!=null)
																radicais.replace(linhaRadicais, palavras.get(linhaRadicais)+1);
														}
														else{
															radicais.put(linhaRadicais, 1);
														}
													}
												}
												break;
											}
									    }
									}
									
								}
							}
				        }
					}
				    cliente.radical(palavras, radicais, file.getName());
				}
				
            System.out.println(algo + ":");	
            System.out.println("Palavras: "+ Npalavras);
            System.out.println("Acertos: "+ Nacertos);
            System.out.println("Precisão: "+ (Nacertos*100)/Npalavras +"%");
            System.out.println("Tempo: "+ tempoTotal +" ms");
            System.out.println("\n");
            
			}
		
		//saida.close();
	}
}