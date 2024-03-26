import Interfaces.IRequete;

import java.util.List;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class ClientDistant {
    private ClientDistant() {}

    public static void main(String[] args) {
        try {
            // Retrieve the registry
            Registry reg = LocateRegistry.getRegistry(null);

            // Look up the remote object in the registry
            IRequete stub = (IRequete) reg.lookup("Hello");

            // Test the VoirStock method
            String stock = stub.VoirStock("COMP1");
            System.out.println("Stock: " + stock);

            // Test the RechercheComposant method
            List<String> composants = stub.RechercheComposant("Electronics");
            System.out.println("Composants: " + composants);

            // Test the acheterComposant method
            boolean purchaseResult = stub.acheterComposant("COMP1", 5, "John Doe");
            System.out.println("Purchase result: " + purchaseResult);

            // Test the ajouterComposant method
            boolean addResult = stub.ajouterComposant("COMP1", 5);
            System.out.println("Add component result: " + addResult);

            // Test the payerFacture method
            boolean paymentResult = stub.payerFacture("John Doe", 50);
            System.out.println("Payment result: " + paymentResult);

            // Test the ConsulterFacture method
            String facture = stub.ConsulterFacture("John Doe");
            System.out.println("facture: " + facture);

        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}