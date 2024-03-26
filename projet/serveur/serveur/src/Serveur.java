import Interfaces.IRequete;
import Models.RelationBaseDeDonnees;
import Models.Requete;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Serveur {

    public static void BDDPremierConnection() {
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();
        baseDeDonnees.createTables();
        baseDeDonnees.close();
    }

    public static void main(String args[]) {

        BDDPremierConnection();
        try {
            Requete obj = new Requete();
            IRequete stub = (IRequete) UnicastRemoteObject.exportObject(obj, 0);
            Registry reg = LocateRegistry.getRegistry();
            reg.bind("Hello", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}