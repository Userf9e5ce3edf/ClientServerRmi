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
        List<Facture> factures = (List<Facture>) daoFacture.findByName(nomClient).get(0);

        Facture facture = factures.get(0);
        if (facture != null && facture.getTotalFacture() >= montant) {
            facture.setTotalFacture(facture.getTotalFacture() - montant);
            daoFacture.update(facture);
            return true;
        }
        return false;
    }

    @Override
    public String ConsulterFacture(String nomClient) throws RemoteException {
        Facture facture = daoFacture.findByName(nomClient).get(0);
        if (facture != null) {
            return "Client: " + facture.getClient().getNom() + "\n" +
                    "Total facture: " + facture.getTotalFacture() + "\n" +
                    "Mode de paiment: " + facture.getModeDePaiment();
        }
        return "Client n'existe pas";
    }
}