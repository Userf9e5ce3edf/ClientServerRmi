package DAO;

import Models.Client;
import Models.Facture;
import datasourceManagement.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAOClient est une classe qui gère les opérations de base de données pour les objets Client.
 */
public class DAOClient extends DAOGenerique<Client> {
    private MySQLManager mySQLManager = MySQLManager.getInstance();

    /**
     * Crée un nouveau client dans la base de données.
     * @param client L'objet Client à créer.
     * @return L'objet Client créé.
     */
    @Override
    public Client create(Client client) {
        try {
            String query = "INSERT INTO clients (nom, prenom, adresse) VALUES ('"
                    + client.getNom() + "', '"
                    + client.getPrenom() + "', '"
                    + client.getAdresse() + "')";
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la creation d'un client : "
                    + e.getMessage());
        }
        return client;
    }

    /**
     * Met à jour un client existant dans la base de données.
     * @param client L'objet Client à mettre à jour.
     * @return L'objet Client mis à jour.
     */
    @Override
    public Client update(Client client) {
        try {
            String query = "UPDATE clients SET "
                    + "nom = '" + client.getNom()
                    + "', prenom = '" + client.getPrenom()
                    + "', adresse = '"  + client.getAdresse()
                    + "' WHERE id = " + client.getId();
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la mise à jour d'un client : "
                    + e.getMessage());
        }
        return client;
    }

    /**
     * Supprime un client de la base de données.
     * @param client L'objet Client à supprimer.
     */
    @Override
    public void delete(Client client) {
        try {
            DAOFacture daoFacture = new DAOFacture();
            Facture facture = daoFacture.findBySomeField("clientId", String.valueOf(client.getId()));

            if (facture != null) {
                String queryFactureItems = "DELETE FROM factureItems WHERE idfacture = " + facture.getId();
                mySQLManager.setData(queryFactureItems);

                String queryFactures = "DELETE FROM factures WHERE id = " + facture.getId();
                mySQLManager.setData(queryFactures);
            }

            String queryClients = "DELETE FROM clients WHERE id = " + client.getId();
            mySQLManager.setData(queryClients);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la suppression d'un client : " + e.getMessage());
        }
    }

    /**
     * Trouve un client par son identifiant.
     * @param id L'identifiant du client à trouver.
     * @return L'objet Client trouvé, ou null si aucun client n'a été trouvé.
     */
    @Override
    public Client findById(String id) {
        Client client = null;
        try {
            String query = "SELECT * FROM clients WHERE id = '"
                    + id + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un client par id : "
                    + e.getMessage());
        }
        return client;
    }

    /**
     * Trouve un client par un champ spécifique.
     * @param nomChamp Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return L'objet Client trouvé, ou null si aucun client n'a été trouvé.
     */
    @Override
    public Client findBySomeField(String nomChamp, String valeur) {
        Client client = null;
        try {
            String query = "SELECT * FROM clients WHERE " + nomChamp + " = '"
                    + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getInt("id"));

            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un client par "
                    + nomChamp + " : " + e.getMessage());
        }
        return client;
    }

    /**
     * Trouve tous les clients qui ont une certaine valeur pour un champ spécifique.
     * @param fieldName Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return Une liste des objets Client trouvés.
     */
    @Override
    public List<Client> findAllBySomeField(String fieldName, String valeur) {
        List<Client> clients = new ArrayList<>();
        try {
            String query = "SELECT * FROM clients WHERE "
                    + fieldName + " = '" + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getInt("id"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de clients par "
                    + fieldName + " : " + e.getMessage());
        }
        return clients;
    }

    /**
     * Trouve tous les clients dans la base de données.
     * @return Une liste de tous les objets Client.
     */
    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            String query = "SELECT * FROM clients";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getInt("id"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de tous les clients : "
                    + e.getMessage());
        }
        return clients;
    }

}