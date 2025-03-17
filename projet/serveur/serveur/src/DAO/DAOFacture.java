package DAO;

import Models.EnumStatutFacture;
import Models.Facture;
import Models.Client;
import Models.EnumModeDePaiement;
import datasourceManagement.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOFacture qui hérite de DAOGenerique.
 * Cette classe permet de manipuler les données de la table 'factures' dans la base de données.
 */
public class DAOFacture extends DAOGenerique<Facture> {
    private MySQLManager mySQLManager = MySQLManager.getInstance();

    /**
     * Crée une nouvelle facture dans la base de données.
     * @param facture L'objet Facture à ajouter dans la base de données.
     * @return L'objet Facture avec l'ID généré.
     */
    @Override
    public Facture create(Facture facture) {
        int id = -1;
        try {
            String query = "INSERT INTO factures (clientId, totalFacture, modeDePaiment, statutFacture) VALUES ('"
                    + facture.getClient().getId() + "', "
                    + facture.getTotalFacture() + ", '"
                    + facture.getModeDePaiment().toString() + "', '"
                    + facture.getStatutFacture().toString() + "')";
            id = mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la creation d'une facture : "
                    + e.getMessage());
        }
        facture.setId(id);
        return facture;
    }

    /**
     * Met à jour une facture existante dans la base de données.
     * @param facture L'objet Facture à mettre à jour dans la base de données.
     * @return L'objet Facture mis à jour.
     */
    @Override
    public Facture update(Facture facture) {
        try {
            String query = "UPDATE factures SET totalFacture = "
                    + facture.getTotalFacture() + ", modeDePaiment = '"
                    + facture.getModeDePaiment().toString() + "', statutFacture = '"
                    + facture.getStatutFacture().toString()
                    + "' WHERE id = " + facture.getId();
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la mise à jour d'une facture : "
                    + e.getMessage());
        }
        return facture;
    }

    /**
     * Supprime une facture de la base de données.
     * @param facture L'objet Facture à supprimer de la base de données.
     */
    @Override
    public void delete(Facture facture) {
        try {
            String query = "DELETE FROM factures WHERE clientId = '"
                    + facture.getClient().getId() + "'";
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la suppression d'une facture : "
                    + e.getMessage());
        }
    }

    /**
     * Trouve une facture par son ID dans la base de données.
     * @param id L'ID de la facture à trouver.
     * @return L'objet Facture trouvé, ou null si aucune facture n'a été trouvée.
     */
    @Override
    public Facture findById(String id) {
        Facture facture = null;
        try {
            String query = "SELECT * FROM factures WHERE id = '"
                    + id + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                facture = new Facture(
                        rs.getInt("id"),
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiement.valueOf(rs.getString("modeDePaiment")));
                facture.setStatutFacture(
                        EnumStatutFacture.valueOf(rs.getString("statutFacture")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par id : "
                    + e.getMessage());
        }
        return facture;
    }

    /**
     * Trouve une facture par un champ spécifique dans la base de données.
     * @param nomChamp Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return L'objet Facture trouvé, ou null si aucune facture n'a été trouvée.
     */
    @Override
    public Facture findBySomeField(String nomChamp, String valeur) {
        Facture facture = null;
        try {
            String query = "SELECT * FROM factures WHERE "
                    + nomChamp + " = '" + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                facture = new Facture(
                        rs.getInt("id"),
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiement.valueOf(
                                rs.getString("modeDePaiment"))
                        );
                facture.setStatutFacture(
                        EnumStatutFacture.valueOf(rs.getString("statutFacture")));

            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par "
                    + nomChamp + " : " + e.getMessage());
        }
        return facture;
    }

    /**
     * Trouve toutes les factures qui ont une certaine valeur pour un champ spécifique.
     * @param fieldName Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return Une liste de factures qui correspondent à la recherche.
     */
    @Override
    public List<Facture> findAllBySomeField(String fieldName, String valeur) {
        List<Facture> factures = new ArrayList<>();
        try {
            String query = "SELECT * FROM factures WHERE "
                    + fieldName + " = '" + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                Facture facture = new Facture(
                        rs.getInt("id"),
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiement.valueOf(rs.getString("modeDePaiment")));
                facture.setStatutFacture(
                        EnumStatutFacture.valueOf(rs.getString("statutFacture")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de factures par "
                    + fieldName + " : " + e.getMessage());
        }
        return factures;
    }

    /**
     * Trouve toutes les factures dans la base de données.
     * @return Une liste de toutes les factures.
     */
    @Override
    public List<Facture> findAll() {
        List<Facture> factures = new ArrayList<>();
        try {
            String query = "SELECT * FROM factures";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                Facture facture = new Facture(
                        rs.getInt("id"),
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiement.valueOf(rs.getString("modeDePaiment")));
                facture.setStatutFacture(
                        EnumStatutFacture.valueOf(rs.getString("statutFacture")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de toutes les factures : "
                    + e.getMessage());
        }
        return factures;
    }

}