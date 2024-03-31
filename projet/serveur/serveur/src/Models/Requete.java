package Models;

import DAO.DAOClient;
import DAO.DAOComposant;
import DAO.DAOFacture;
import Interfaces.IRequete;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Requete implements IRequete {
    private DAOClient daoClient = new DAOClient();
    private DAOComposant daoComposant = new DAOComposant();
    private DAOFacture daoFacture = new DAOFacture();

    public String VoirStock(String refComposant) {
        Composant composant = daoComposant.findBySomeField("reference" ,refComposant);
        return composant != null ? composant.toString() : null;
    }

    @Override
    public List<String> RechercheComposant(String famille) throws RemoteException {
        List<Composant> composants = daoComposant.findAllBySomeField("famille", famille);
        List<String> references = new ArrayList<>();
        for (Composant composant : composants) {
            if (composant.quantite > 0) {
                references.add(composant.reference);
            }
        }
        return references;
    }

    @Override
    public boolean acheterComposant(String refComposant, int quantite, String nomClient) throws RemoteException {
        Composant composant = daoComposant.findBySomeField("reference",refComposant);
        Client client = daoClient.findBySomeField("nom", nomClient);
        if (composant != null && composant.quantite >= quantite && client != null) {
            composant.quantite -= quantite;
            daoComposant.update(composant);

            Facture facture = daoFacture.findBySomeField("clientId", String.valueOf(client.getId()));
            if (facture == null) {
                facture = new Facture(client, 0, EnumModeDePaiment.NON_DEFINI);
                daoFacture.create(facture);
            }
            facture.setTotalFacture(facture.getTotalFacture() + composant.prix * quantite);
            daoFacture.update(facture);

            return true;
        }
        return false;
    }

    @Override
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException {
        Composant composant = daoComposant.findBySomeField("reference",refComposant);
        if (composant != null) {
            composant.quantite += quantite;
            daoComposant.update(composant);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String nomClient, double montant, EnumModeDePaiment modeDePaiment) throws RemoteException {
        Client client = daoClient.findBySomeField("nom", nomClient);
        System.out.println("client: " + client);
        if (client != null) {
            Facture facture = daoFacture.findBySomeField("clientId",
                    String.valueOf(client.getId()));
            System.out.println("facture: " + facture);
            if (facture != null && facture.getTotalFacture() >= montant) {
                double newTotal = facture.getTotalFacture() - montant;
                if (newTotal < 0) {
                    return false; // trop payer
                }
                facture.setTotalFacture(newTotal);
                facture.setModeDePaiment(modeDePaiment);
                daoFacture.update(facture);
                return true;
            }
        }
        return false;
    }

    @Override
    public String ConsulterFacture(String nomClient) throws RemoteException {
        Client client = daoClient.findBySomeField("nom", nomClient);
        if (client != null) {
            Facture facture = daoFacture.findBySomeField("clientId",
                    String.valueOf(client.getId()));
            if (facture != null) {
                return "Client: " + facture.getClient().getNom() + "\n" +
                        "Total facture: " + facture.getTotalFacture() + "\n" +
                        "Mode de paiment: " + facture.getModeDePaiment() + "\n" +
                        "Statut facture: " + facture.getStatutFacture();
            }
            return "Facture n'existe pas pour le client: " + nomClient;
        }
        return "Client n'existe pas";
    }
}