package cliente;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Cliente extends Remote {
	public void radical(Map<String, Integer> palavras, Map<String, Integer> radicais, String nomeArquivo)
			throws RemoteException, IOException;

	public int buscarPalavra(String palavra) throws IOException;

	public void listarPalavras() throws IOException;

	public void listarRadicais() throws IOException;
}
