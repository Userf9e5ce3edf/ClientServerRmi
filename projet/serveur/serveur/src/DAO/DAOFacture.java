package DAO;

import Models.Facture;
import Models.Client;
import Models.EnumModeDePaiment;
import datasourceManagement.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOFacture extends DAOGenerique<Facture> {
    private MySQLManager mySQLManager = MySQLManager.getInstance();

    @Override
    public Facture create(Facture facture) {
        int id = -1;
        try {
            String query = "INSERT INTO factures (clientId, totalFacture, modeDePaiment) VALUES ('"
                    + facture.getClient().getId() + "', "
                    + facture.getTotalFacture() + ", '"
                    + facture.getModeDePaiment().toString() + "')";
            id = mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la creation d'une facture : "
                    + e.getMessage());
        }

        facture.setId(id);
        return facture;
    }

    @Override
    public Facture update(Facture facture) {
        try {
            String query = "UPDATE factures SET totalFacture = "
                    + facture.getTotalFacture() + ", modeDePaiment = '"
                    + facture.getModeDePaiment().toString() + "', statutFacture = '"
                    + facture.getStatutFacture().toString()
                    + "' WHERE clientId = " + facture.getClient().getId();
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la mise à jour d'une facture : "
                    + e.getMessage());
        }
        return facture;
    }

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

    @Override
    public void saveAll(List<Facture> factures) {
        for (Facture facture : factures) {
            create(facture);
        }
    }

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
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par id : "
                    + e.getMessage());
        }
        return facture;
    }

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
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par "
                    + nomChamp + " : " + e.getMessage());
        }
        return facture;
    }

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
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de factures par "
                    + fieldName + " : " + e.getMessage());
        }
        return factures;
    }

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
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de toutes les factures : "
                    + e.getMessage());
        }
        return factures;
    }

    @Override
    public List<Facture> findByName(String name) {
        List<Facture> factures = new ArrayList<>();
        try {
            String query = "SELECT * FROM factures WHERE clientId = '"
                    + new DAOClient().findBySomeField("nom", name).getNom() + "'";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                Facture facture = new Facture(
                        rs.getInt("id"),
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par nom : "
                    + e.getMessage());
        }
        return factures;
    }
}