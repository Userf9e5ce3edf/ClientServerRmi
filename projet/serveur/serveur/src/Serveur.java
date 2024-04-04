import Interfaces.IRequete;
import Models.Requete;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur {

    public static void main(String args[]) {


//        String refComposant = "COMP52";
//        int quantite = 10;
//        String nomClient = "John Doe";
//
//        try {
//            // Call ajouterAuPanier method with test data
//            Requete requete = new Requete();
//            boolean result = requete.ajouterAuPanier(refComposant, quantite, nomClient);
//
//            // Print the result
//            System.out.println("Result of ajouterAuPanier: " + result);
//        } catch (RemoteException e) {
//            System.err.println("Error while adding to cart: " + e.getMessage());
//        }
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