package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ptstemmer.Stemmer;
import ptstemmer.Stemmer.StemmerType;
import ptstemmer.exceptions.PTStemmerException;

public class Main {

	private static final String PATH_RMI = "rmi://localhost/radical";
	private static final String BOOKS_DIR = "/home/emerson/Documentos/projetoStemmer/livros/";
	private static final String VOCABULARY_WORDS = "/home/emerson/Documentos/projetoStemmer/vocPalavras.txt";
	private static final String VOCABULARY_RADICALS = "/home/emerson/Documentos/projetoStemmer/vocRadicais.txt";
	private static BufferedReader in;
	private static BufferedReader palavrasIn;
	private static BufferedReader radicaisIn;

	public static void main(String[] args) throws IOException, PTStemmerException, NotBoundException, SQLException {
		Stemmer stemmer = null;
		String algoritmo = null;
		String radical;
		int numeroPalavras;
		int numeroAcertos;
		long tempoInicio;
		long tempoTotal;

		Cliente cliente = (Cliente) Naming.lookup(PATH_RMI);

		Map<String, Integer> palavras = new HashMap<String, Integer>();
		Map<String, Integer> radicais = new HashMap<String, Integer>();

		for (int x = 0; x < 3; x++) {
			if (x == 0) {
				algoritmo = "ORENGO";
				stemmer = Stemmer.StemmerFactory(StemmerType.valueOf(algoritmo));
			}
			if (x == 1) {
				algoritmo = "PORTER";
				stemmer = Stemmer.StemmerFactory(StemmerType.valueOf(algoritmo));
			}
			if (x == 2) {
				algoritmo = "SAVOY";
				stemmer = Stemmer.StemmerFactory(StemmerType.valueOf(algoritmo));
			}

			numeroPalavras = 0;
			numeroAcertos = 0;
			tempoTotal = 0;

			palavras.clear();

			String linha = null;
			String linhaPalavras = null;
			String linhaRadicais = null;

			File baseFolder = new File(BOOKS_DIR);

			File[] files = baseFolder.listFiles();

			if (files.length == 0 || files == null) {
				System.out.println("Não ha arquivos de texto na pasta padrão, insira os arquivos e tente novamente.");

				return;
			}

			for (int i = 0; i < files.length; i++) {
				File file = files[i];

				if (file.getPath().endsWith(".txt")) {
					String name = file.getName();

					in = new BufferedReader(new FileReader(BOOKS_DIR + name));

					while ((linha = in.readLine()) != null) {
						linha = linha.replace(".", "");
						linha = linha.replace(",", "");
						linha = linha.replace(":", "");
						linha = linha.replace(";", "");
						linha = linha.replace("(", "");
						linha = linha.replace(")", "");
						linha = linha.replace("?", "");

						String[] array = linha.split(" ");

						for (int j = 0; j < array.length; j++) {

							if (array[j].isEmpty() != true) {

								palavrasIn = new BufferedReader(new FileReader(VOCABULARY_WORDS));

								while ((linhaPalavras = palavrasIn.readLine()) != null) {
									if (array[j].equals(linhaPalavras)) {
										if (palavras.containsKey(linhaPalavras)) {
											if (palavras.get(linhaPalavras) != null)
												palavras.replace(linhaPalavras, palavras.get(linhaPalavras) + 1);
										} else {
											palavras.put(linhaPalavras, 1);

											numeroPalavras++;

											tempoInicio = System.currentTimeMillis();
											radical = stemmer.getWordStem(array[j]);
											tempoTotal = ((System.currentTimeMillis() - tempoInicio) + tempoTotal);

											radicaisIn = new BufferedReader(new FileReader(VOCABULARY_RADICALS));

											while ((linhaRadicais = radicaisIn.readLine()) != null) {
												if (radical.equals(linhaRadicais)) {
													numeroAcertos++;
													break;
												} else {
													if (radicais.containsKey(linhaRadicais)) {
														if (palavras.get(linhaRadicais) != null)
															radicais.replace(linhaRadicais,
																	palavras.get(linhaRadicais) + 1);
													} else {
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

			System.out.println(algoritmo + ":");
			System.out.println("Palavras: " + numeroPalavras);
			System.out.println("Acertos: " + numeroAcertos);
			System.out.println("Precisão: " + (numeroAcertos * 100) / numeroPalavras + "%");
			System.out.println("Tempo: " + tempoTotal + " ms");
			System.out.println("\n");

		}
	}
}