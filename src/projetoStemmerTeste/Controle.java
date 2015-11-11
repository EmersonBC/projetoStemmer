package projetoStemmerTeste;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
 
public class Controle {

	public static void main(String[] args) throws NotBoundException, IOException {
		Cliente cliente = (Cliente) Naming.lookup("rmi://localhost/radical");
		Scanner sc = new Scanner(System.in);
		int escolha = 0;
		int vezes;
		
		while(escolha!=-1){
			escolha = 0;
			System.out.println("\n**********MENU**********");
			System.out.println("1- Para buscar uma palavra");
			System.out.println("2- Para listar todas as palavras");
			System.out.println("3- Para listar os radicais nao tratados");
			System.out.println("4- Para sair");
			escolha = sc.nextInt();
			String palavra = null;
			
			switch(escolha){
				case 1:
					System.out.println("Digite a palavra que deseja buscar: ");
					sc.nextLine();
					palavra = sc.nextLine();
					
					vezes = cliente.buscarPalavra(palavra);
					
					if(vezes!=-1){
						System.out.println("\nA palavra "+ palavra +" apareceu "+ vezes +" vezes");
					}
					else
						System.out.println("\nPalavra nao encontrada!");
					break;
				
				case 2:
					cliente.listarPalavras();
					System.out.println("\nArquivo TotalPalavras.log gerado com sucesso!");
					break;
				
				case 3:
					cliente.listarRadicais();
					System.out.println("\nArquivo TotalRadicais.log gerado com sucesso!");
					break;
					
				case 4:
					escolha = -1;
					break;
			}
			sc.nextLine();
		}
	}

}
