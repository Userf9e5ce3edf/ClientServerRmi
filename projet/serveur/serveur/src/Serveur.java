import Interfaces.IRequete;
import Models.RelationBaseDeDonnees;
import Models.Requete;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
        Requete req = new Requete();
        String test = req.VoirStock("b9b6233");
        /**
        try {
            IRequete requeteImpl = new Requete();
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", requeteImpl);

            System.out.println("Le Serveur est prêt...");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
         **/
    }
}