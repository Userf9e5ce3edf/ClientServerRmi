package DAO;

import Models.Facture;
import Models.Client;
import Models.EnumModeDePaiment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOFacture extends DAOGenerique<Facture> {

    @Override
    public Facture create(Facture facture) {
        try {
            String query = "INSERT INTO factures (clientId, totalFacture, modeDePaiment) VALUES (?, ?, ?)";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, facture.getClient().nom);
            stmt.setDouble(2, facture.getTotalFacture());
            stmt.setString(3, facture.getModeDePaiment().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la creation d'une facture : " + e.getMessage());
        }
        return facture;
    }

    @Override
    public Facture update(Facture facture) {
        try {
            String query = "UPDATE factures SET totalFacture = ?, modeDePaiment = ? WHERE clientId = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setDouble(1, facture.getTotalFacture());
            stmt.setString(2, facture.getModeDePaiment().toString());
            stmt.setString(3, facture.getClient().nom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la mise à jour d'une facture : " + e.getMessage());
        }
        return facture;
    }

    @Override
    public void delete(Facture facture) {
        try {
            String query = "DELETE FROM factures WHERE clientId = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, facture.getClient().nom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la suppression d'une facture : " + e.getMessage());
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
            String query = "SELECT * FROM factures WHERE clientId = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                facture = new Facture(
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par id : " + e.getMessage());
        }
        return facture;
    }

    @Override
    public List<Facture> findAll() {
        List<Facture> factures = new ArrayList<>();
        try {
            String query = "SELECT * FROM factures";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                Facture facture = new Facture(
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de toutes les factures : " + e.getMessage());
        }
        return factures;
    }

    @Override
    public List<Facture> findByName(String name) {
        List<Facture> factures = new ArrayList<>();
        try {
            String query = "SELECT * FROM factures WHERE clientId = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, new DAOClient().findByName(name).get(0).nom);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new DAOClient().findById(rs.getString("clientId"));
                Facture facture = new Facture(
                        client,
                        rs.getDouble("totalFacture"),
                        EnumModeDePaiment.valueOf(rs.getString("modeDePaiment")));
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'une facture par nom : " + e.getMessage());
        }
        return factures;
    }
}