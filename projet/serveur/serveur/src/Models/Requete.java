package Models;

import DAO.DAOClient;
import DAO.DAOComposant;
import DAO.DAOFacture;
import DAO.DAOFactureItem;
import Interfaces.IRequete;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class Requete implements IRequete, Serializable {
    private DAOClient daoClient = new DAOClient();
    private DAOComposant daoComposant = new DAOComposant();
    private DAOFacture daoFacture = new DAOFacture();
    private DAOFactureItem daoFactureItem = new DAOFactureItem();

    @Override
    public String VoirStock(String refComposant) {
        Composant composant = daoComposant.findBySomeField("reference" ,refComposant);
        return composant != null ? composant.toString() : null;
    }
    @Override
    public List<String> GetAllFamilles() {
        List<String> familles = daoComposant.findAllFamilles();
        return familles;
    }
    @Override
    public List<Composant> RechercheComposant(String famille) throws RemoteException {
        List<Composant> composants = daoComposant.findAllBySomeField("famille", famille);
        List<Composant> availableComposants = new ArrayList<>();
        for (Composant composant : composants) {
            if (composant.quantite > 0) {
                availableComposants.add(composant);
            }
        }
        return availableComposants;
    }
    @Override
    public boolean ajouterAuPanier(String refComposant, int quantite, String nomClient) throws RemoteException {
        // Check if the client has an ongoing invoice
        Client client = daoClient.findBySomeField("nom", nomClient);
        Facture facture = daoFacture.findBySomeField("clientId", String.valueOf(client.getId()));
        if (facture == null) {
            // si pas de facture en curs, on en crée une
            facture = new Facture(client, 0, EnumModeDePaiment.NON_DEFINI);
            daoFacture.create(facture);
        }

        // Find the composant
        Composant composant = daoComposant.findBySomeField("reference", refComposant);

        // Check if the client has already added this specific composant to his facture
        Map<String, String> fields = new HashMap<>();
        fields.put("idfacture", String.valueOf(facture.getId()));
        fields.put("idcomposant", String.valueOf(composant.getId()));
        FactureItem factureItem = daoFactureItem.findBySomeFields(fields);

        if (factureItem != null) {
            // If yes, update the quantity
            factureItem.setQuantite(factureItem.getQuantite() + quantite);
            daoFactureItem.update(factureItem);
        } else {
            // If no, create a new line in the FactureItem table
            factureItem = new FactureItem(composant, facture, quantite);
            daoFactureItem.create(factureItem);
            if(factureItem == null) {
                return false;
            }
        }
        // retire  les composants du stock
        retirerComposantDuStock(refComposant, quantite);

        // Update the totalFacture of the Facture
        double costOfAddedItems = factureItem.getComposant().getPrix() * quantite;
        facture.setTotalFacture(facture.getTotalFacture() + costOfAddedItems);
        daoFacture.update(facture);

        return true;
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
    public boolean retirerComposantDuStock(String refComposant, int quantite) throws RemoteException {
        Composant composant = daoComposant.findBySomeField("reference", refComposant);
        if (composant == null) {
            return false;
        }
        int nouvelleQuantite = composant.quantite - quantite;

        if (nouvelleQuantite < 0) {
            return false;
        }

        composant.quantite -= quantite;
        Composant updatedComposant = daoComposant.update(composant);
        if (updatedComposant == null) {
            return false;
        }
        return true;
    }
    @Override
    public boolean payerFacture(String nomClient, EnumModeDePaiment modeDePaiment) throws RemoteException {
        Client client = daoClient.findBySomeField("nom", nomClient);
        if (client != null) {
            Facture facture = daoFacture.findBySomeField("clientId", String.valueOf(client.getId()));
            if (facture != null && facture.getStatutFacture() == EnumStatutFacture.ENCOURS) {
                facture.setModeDePaiment(modeDePaiment);
                facture.setStatutFacture(EnumStatutFacture.FERMER);
                daoFacture.update(facture);
                return true;
            }
        }
        return false;
    }
    @Override
    public String ConsulterFacture(String nomClient) throws RemoteException {
        // Find the client
        Client client = daoClient.findBySomeField("nom", nomClient);
        if (client == null) {
            return "Client n'existe pas";
        }

        // Find the invoice for the client
        Facture facture = daoFacture.findBySomeField("clientId", String.valueOf(client.getId()));
        if (facture == null) {
            return "Facture n'existe pas pour le client: " + nomClient;
        }

        // Build the invoice details string
        StringBuilder factureDetails = new StringBuilder();
        factureDetails.append("Client: ").append(facture.getClient().getNom()).append("\n");
        factureDetails.append("Total facture: ").append(facture.getTotalFacture()).append("\n");
        factureDetails.append("Mode de paiment: ").append(facture.getModeDePaiment()).append("\n");
        factureDetails.append("Statut facture: ").append(facture.getStatutFacture()).append("\n");

        // Add the items in the facture
        List<FactureItem> factureItems = daoFactureItem.findAllBySomeField("idfacture", String.valueOf(facture.getId()));
        for (FactureItem factureItem : factureItems) {
            factureDetails.append("Item: ").append(factureItem.getComposant().getReference()).append(", Quantite: ").append(factureItem.getQuantite()).append("\n");
        }

        return factureDetails.toString();
    }

    // CRUD operations pour Client
    @Override
    public Client createClient(String nom, String adresse) {
        Client client = new Client(nom, adresse);
        return daoClient.create(client);
    }
    @Override
    public Client updateClient(int id, String nom, String adresse) {
        Client client = daoClient.findById(String.valueOf(id));
        if (client != null) {
            client.setAdresse(adresse);
            client.setNom(nom);
            return daoClient.update(client);
        }
        return null;
    }
    @Override
    public boolean deleteClient(int id) {
        Client client = daoClient.findById(String.valueOf(id));
        if (client != null) {
            daoClient.delete(client);
            return true;
        }
        return  false;
    }
    @Override
    public Client getClient(int id) {
        return daoClient.findById(String.valueOf(id));
    }
    @Override
    public List<Client> getAllClients() {
        return daoClient.findAll();
    }
}
