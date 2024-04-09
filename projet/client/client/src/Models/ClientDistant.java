package Models;

import Interfaces.IRequete;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientDistant {
    private static ClientDistant instance = null;
    public IRequete stub = null;

    private ClientDistant() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1");
            stub = (IRequete) reg.lookup("Hello");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }

    public static ClientDistant getInstance() {
        if (instance == null) {
            instance = new ClientDistant();
        }
        return instance;
    }
}