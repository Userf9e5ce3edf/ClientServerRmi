import Interfaces.IMessage;
import Interfaces.IRequete;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Client {
    private Client() {}

    public static void main(String[] args) {
        try {
            // Récupérer le registre
            Registry reg = LocateRegistry.getRegistry(null);
            // Recherche dans le registre de l'objet distant
            IRequete stub = (IRequete) reg.lookup("Hello");
            // Appel de la méthode distante à l'aide de l'objet obtenu

            List<String> test = stub.RechercheComposant("voirStock");
            System.out.println(test);

        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}