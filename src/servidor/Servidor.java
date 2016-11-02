package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Servidor extends UnicastRemoteObject implements cliente.Cliente {
	protected Servidor() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	private static final String PATH_WORDS_FILE = "/home/emerson/Documentos/projetoStemmer/radicais/palavrasAtingidas/";
	private static final String PATH_RADICAL_NOT_RECOGNIZED = "/home/emerson/Documentos/projetoStemmer/radicais/radicaisNaoReconhecidos/";
	private static final String ALL_WORDS_FILE = "/home/emerson/Documentos/projetoStemmer/radicais/TotalPalavras.txt";
	private static final String ALL_RADICAL_NOT_RECOGNIZED_FILE = "/home/emerson/Documentos/projetoStemmer/radicais/TotalRadicaisNaoReconhecidos.txt";
	private BufferedReader in;

	public void radical(Map<String, Integer> palavras, Map<String, Integer> radicais, String nomeArquivo)
			throws RemoteException, IOException {
		Date data = new Date();

		FileWriter arqPalavras = new FileWriter(new File(PATH_WORDS_FILE + data + " " + nomeArquivo + ".log"));

		System.out.println(data.toString());

		for (Map.Entry<String, Integer> pair : palavras.entrySet()) {
			arqPalavras.write(pair.getKey() + " " + pair.getValue());
			arqPalavras.append("\n");
		}

		arqPalavras.close();

		FileWriter arqRadicais = new FileWriter(
				new File(PATH_RADICAL_NOT_RECOGNIZED + data + " " + nomeArquivo + ".log"));

		System.out.println(data.toString());

		for (Map.Entry<String, Integer> pair : radicais.entrySet()) {
			arqRadicais.write(pair.getKey() + " " + pair.getValue());
			arqRadicais.append("\n");
		}

		arqRadicais.close();
	}

	public int buscarPalavra(String palavra) throws IOException {
		int quantidade = 0;
		String linha = null;

		File baseFolder = new File(PATH_WORDS_FILE);

		File[] files = baseFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.getPath().endsWith(".log")) {
				String name = file.getName();

				in = new BufferedReader(new FileReader(PATH_WORDS_FILE + name));

				while ((linha = in.readLine()) != null) {
					String[] array = linha.split(" ");

					if (array[0].equals(palavra)) {
						quantidade++;
					}
				}

			}
		}

		if (quantidade != 0) {
			return quantidade;
		} else {
			return -1;
		}
	}

	public void listarPalavras() throws IOException {
		String linha = null;
		Map<String, Integer> hash = new HashMap<String, Integer>();

		File baseFolder = new File(PATH_WORDS_FILE);

		File[] files = baseFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.getPath().endsWith(".log")) {
				String name = file.getName();

				in = new BufferedReader(new FileReader(PATH_WORDS_FILE + name));

				while ((linha = in.readLine()) != null) {
					String[] array = linha.split(" ");

					if (hash.containsKey(array[0])) {
						if (hash.get(array[0]) != null)
							hash.replace(array[0], hash.get(array[0]) + Integer.parseInt(array[1]));
					} else {
						hash.put(array[0], Integer.parseInt(array[1]));
					}
				}

			}
		}

		FileWriter saida = new FileWriter(new File(ALL_WORDS_FILE));

		for (Map.Entry<String, Integer> pair : hash.entrySet()) {
			saida.write(pair.getKey() + " " + pair.getValue());
			saida.append("\n");
		}
		saida.close();
	}

	public void listarRadicais() throws IOException {
		String linha = null;
		Map<String, Integer> hash = new HashMap<String, Integer>();

		File baseFolder = new File(PATH_RADICAL_NOT_RECOGNIZED);

		File[] files = baseFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.getPath().endsWith(".log")) {
				String name = file.getName();

				in = new BufferedReader(new FileReader(PATH_RADICAL_NOT_RECOGNIZED + name));

				while ((linha = in.readLine()) != null) {
					String[] array = linha.split(" ");

					if (hash.containsKey(array[0])) {
						if (hash.get(array[0]) != null)
							hash.replace(array[0], hash.get(array[0]) + Integer.parseInt(array[1]));
					} else {
						hash.put(array[0], Integer.parseInt(array[1]));
					}
				}

			}
		}

		FileWriter saida = new FileWriter(new File(ALL_RADICAL_NOT_RECOGNIZED_FILE));

		for (Map.Entry<String, Integer> pair : hash.entrySet()) {
			saida.write(pair.getKey());
			saida.append("\n");
		}
		saida.close();
	}
}
