package servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Main {

	private static final String PATH_RMI = "rmi://localhost/radical";

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Servidor server = new Servidor();

		Naming.rebind(PATH_RMI, server);
	}

}