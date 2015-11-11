package servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args)  throws RemoteException, MalformedURLException {
		Servidor server = new Servidor();
		
		Naming.rebind("rmi://localhost/radical", server);
	}

}