import Interfaces.IRequete;
import Models.EnumModeDePaiment;

import java.util.List;
import java.util.Scanner;
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

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Please select an operation:");
                System.out.println("1. VoirStock");
                System.out.println("2. RechercheComposant");
                System.out.println("3. acheterComposant");
                System.out.println("4. ajouterComposant");
                System.out.println("5. payerFacture");
                System.out.println("6. ConsulterFacture");
                System.out.println("7. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline left-over
                switch (choice) {
                    case 1:
                        System.out.println("Enter the reference of the component:");
                        String refComposant = scanner.nextLine();
                        String stock = stub.VoirStock(refComposant);
                        System.out.println("Stock: " + stock);
                        break;
                    case 2:
                        System.out.println("Enter the family of the component:");
                        String famille = scanner.nextLine();
                        List<String> composants = stub.RechercheComposant(famille);
                        System.out.println("Composants: " + composants);
                        break;
                    case 3:
                        System.out.println("Enter the reference of the component:");
                        String refComposantAchat = scanner.nextLine();
                        System.out.println("Enter the quantity:");
                        int quantite = scanner.nextInt();
                        scanner.nextLine(); // consume newline left-over
                        System.out.println("Enter the client name:");
                        String nomClientAchat = scanner.nextLine();
                        boolean purchaseResult = stub.acheterComposant(refComposantAchat, quantite, nomClientAchat);
                        System.out.println("Purchase result: " + purchaseResult);
                        break;
                    case 4:
                        System.out.println("Enter the reference of the component:");
                        String refComposantAjout = scanner.nextLine();
                        System.out.println("Enter the quantity:");
                        int quantiteAjout = scanner.nextInt();
                        scanner.nextLine(); // consume newline left-over
                        boolean addResult = stub.ajouterComposant(refComposantAjout, quantiteAjout);
                        System.out.println("Add component result: " + addResult);
                        break;
                    case 5:
                        System.out.println("Enter the client name:");
                        String nomClientPaiement = scanner.nextLine();
                        System.out.println("Enter the amount:");
                        double montant = scanner.nextDouble();
                        scanner.nextLine(); // consume newline left-over
                        System.out.println("Choose a payment method:");
                        System.out.println("1. CARTEBANCAIRE");
                        System.out.println("2. ESPECE");
                        System.out.println("3. VIREMENT");
                        int paymentChoice = scanner.nextInt();
                        scanner.nextLine(); // consume newline left-over
                        EnumModeDePaiment modeDePaiment;
                        switch (paymentChoice) {
                            case 1:
                                modeDePaiment = EnumModeDePaiment.CARTEBANCAIRE;
                                break;
                            case 2:
                                modeDePaiment = EnumModeDePaiment.ESPECE;
                                break;
                            case 3:
                                modeDePaiment = EnumModeDePaiment.VIREMENT;
                                break;
                            default:
                                System.out.println("Invalid choice. Defaulting to CARTEBANCAIRE.");
                                modeDePaiment = EnumModeDePaiment.CARTEBANCAIRE;
                        }
                        boolean paymentResult = stub.payerFacture(nomClientPaiement, montant, modeDePaiment);
                        System.out.println("Payment result: " + paymentResult);
                        break;
                    case 6:
                        System.out.println("Enter the client name:");
                        String nomClientFacture = scanner.nextLine();
                        String facture = stub.ConsulterFacture(nomClientFacture);
                        System.out.println("Facture: " + facture);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}