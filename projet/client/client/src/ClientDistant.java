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
            Registry reg = LocateRegistry.getRegistry(null);
            IRequete stub = (IRequete) reg.lookup("Hello");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choisir une operation:");
                System.out.println("1. Voir le stock d'un composant");
                System.out.println("2. Recherche composants par famille");
                System.out.println("3. acheter un composant");
                System.out.println("4. ajouter un composant");
                System.out.println("5. payer une facture");
                System.out.println("6. consulter facture");
                System.out.println("7. Quitter");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Saisir la reference du composant:");
                        String refComposant = scanner.nextLine();
                        String stock = stub.VoirStock(refComposant);
                        System.out.println("Stock: " + stock);
                        break;
                    case 2:
                        System.out.println("saisir la famile du composant:");
                        String famille = scanner.nextLine();
                        List<String> composants = stub.RechercheComposant(famille);
                        System.out.println("Composants: " + composants);
                        break;
                    case 3:
                        System.out.println("saisir la reference du composant:");
                        String refComposantAchat = scanner.nextLine();
                        System.out.println("saisir la quantité:");
                        int quantite = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("saisir le nom du client:");
                        String nomClientAchat = scanner.nextLine();
                        boolean purchaseResult = stub.acheterComposant(refComposantAchat, quantite, nomClientAchat);
                        if(purchaseResult) {
                            System.out.println("achat reussi");
                        } else {
                            System.out.println("achat echoué");
                        }
                        break;
                    case 4:
                        System.out.println("saisir la reference du composant:");
                        String refComposantAjout = scanner.nextLine();
                        System.out.println("saisir la quantité:");
                        int quantiteAjout = scanner.nextInt();
                        scanner.nextLine(); // consume newline left-over
                        boolean addResult = stub.ajouterComposant(refComposantAjout, quantiteAjout);
                        System.out.println("Resultat de l'ajout du composant: " + addResult);
                        break;
                    case 5:
                        System.out.println("saisir le nom du client:");
                        String nomClientPaiement = scanner.nextLine();
                        System.out.println("saisir le montant:");
                        double montant = scanner.nextDouble();
                        scanner.nextLine(); // consume newline left-over
                        System.out.println("choisir un moyen de paiment:");
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
                                System.out.println("choix invalide. mode par default CARTEBANCAIRE.");
                                modeDePaiment = EnumModeDePaiment.CARTEBANCAIRE;
                        }
                        boolean paymentResult = stub.payerFacture(nomClientPaiement, montant, modeDePaiment);
                        if(paymentResult) {
                            System.out.println("paiment reussi");
                        } else {
                            System.out.println("paiment echoué");
                        }
                        break;
                    case 6:
                        System.out.println("saisir le nom du client:");
                        String nomClientFacture = scanner.nextLine();
                        String facture = stub.ConsulterFacture(nomClientFacture);
                        System.out.println("Facture: " + facture);
                        break;
                    case 7:
                        System.out.println("Fermeture...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("choix invalide. Saisir un nombre entre 1 et 7.");
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}