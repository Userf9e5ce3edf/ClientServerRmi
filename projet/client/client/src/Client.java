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
            // Retrieve the registry
            Registry reg = LocateRegistry.getRegistry(null);

            // Look up the remote object in the registry
            IRequete stub = (IRequete) reg.lookup("Hello");

            // Test the VoirStock method
            String stock = stub.VoirStock("b9b6233");
            System.out.println("Stock: " + stock);

            // Test the RechercheComposant method
            List<String> composants = stub.RechercheComposant("Famille10");
            System.out.println("Composants: " + composants);

            // Test the payerFacture method
            boolean paymentResult = stub.payerFacture("John Doe", 100.0);
            System.out.println("Payment result: " + paymentResult);

            // Test the ConsulterFacture method
            String bill = stub.ConsulterFacture("John Doe");
            System.out.println("Bill: " + bill);

        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}