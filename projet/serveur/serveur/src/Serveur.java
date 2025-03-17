import Interfaces.IRequete;
import Models.Requete;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur {

    public static void main(String args[]) {
        try {
            int port = 1099;
            LocateRegistry.createRegistry(port);
            System.out.println("RMI registry sur le port " + port);

            Requete obj = new Requete();
            IRequete stub = (IRequete) UnicastRemoteObject.exportObject(obj, port);
            Registry reg = LocateRegistry.getRegistry();
            reg.bind("Requete", stub);

            System.out.println("Server prêt");
        } catch (Exception e) {
            System.err.println("Erreur serveur: " + e.toString());
        }
    }
}