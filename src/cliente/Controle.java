package cliente;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Controle {

	private static final String PATH_RMI = "rmi://localhost/radical";
	private static Scanner sc;

	public static void main(String[] args) throws NotBoundException, IOException {
		Cliente cliente = (Cliente) Naming.lookup(PATH_RMI);
		sc = new Scanner(System.in);
		int escolha = 0;
		int vezesPalavraApareceu;

		while (escolha != -1) {
			escolha = 0;
			System.out.println("\n**********MENU**********");
			System.out.println("1- Buscar palavra");
			System.out.println("2- Listar todas as palavras");
			System.out.println("3- Listar os radicais não reconhecidos");
			System.out.println("4- Sair");
			escolha = sc.nextInt();
			String palavra = null;

			switch (escolha) {
			case 1:
				System.out.println("Digite a palavra que deseja buscar: ");
				sc.nextLine();
				palavra = sc.nextLine();

				vezesPalavraApareceu = cliente.buscarPalavra(palavra);

				if (vezesPalavraApareceu != -1) {
					System.out.println("\nA palavra " + palavra + " apareceu " + vezesPalavraApareceu + " vezes");
				} else
					System.out.println("\nPalavra nao encontrada!");
				break;

			case 2:
				cliente.listarPalavras();
				System.out.println("\nArquivo TotalPalavras.txt gerado com sucesso!");
				break;

			case 3:
				cliente.listarRadicais();
				System.out.println("\nArquivo TotalRadicaisNaoReconhecidos.txt gerado com sucesso!");
				break;

			case 4:
				escolha = -1;
				break;
			}
			sc.nextLine();
		}
	}

}
