package Models;

import DAO.DAOClient;
import DAO.DAOComposant;
import Interfaces.IRequete;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Requete implements IRequete {
    private DAOClient daoClient = new DAOClient();
    private DAOComposant daoComposant = new DAOComposant();

    public String VoirStock(String refComposant) {
        Composant composant = daoComposant.findById(refComposant);
        return composant != null ? composant.toString() : null;
    }

    @Override
    public List<String> RechercheComposant(String famille) throws RemoteException {
        List<Composant> composants = daoComposant.findByName(famille);
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
        Composant composant = daoComposant.findById(refComposant);
        if (composant != null && composant.quantite >= quantite) {
            composant.quantite -= quantite;
            daoComposant.update(composant);
            return true;
        }
        return false;
    }

    @Override
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException {
        Composant composant = daoComposant.findById(refComposant);
        if (composant != null) {
            composant.quantite += quantite;
            daoComposant.update(composant);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String nomClient, double montant) throws RemoteException {
        Client client = daoClient.findByName(nomClient).get(0);
        if (client != null && client.total_facture >= montant) {
            client.total_facture -= montant;
            daoClient.update(client);
            return true;
        }
        return false;
    }

    @Override
    public String ConsulterFacture(String nomClient) throws RemoteException {
        Client client = daoClient.findByName(nomClient).get(0);
        if (client != null) {
            return "Client: " + client.nom + "\n" +
                    "Total bill: " + client.total_facture + "\n" +
                    "Payment method: " + client.mode_paiement;
        }
        return "Client does not exist";
    }
}