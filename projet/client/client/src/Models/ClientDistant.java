package Models;

import Interfaces.IRequete;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientDistant {
    private static ClientDistant instance = null;
    public IRequete stub = null;
    private static String hostIP = "127.0.0.1";

    private ClientDistant() throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(hostIP);
        stub = (IRequete) reg.lookup("Requete");
    }

    public static ClientDistant getInstance() throws RemoteException, NotBoundException {
        if (instance == null) {
            instance = new ClientDistant();
        }
        return instance;
    }

    public static void setHostIP(String newHostIP) {
        hostIP = newHostIP;
        instance = null;
    }
}