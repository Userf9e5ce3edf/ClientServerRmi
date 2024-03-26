package DAO;

import Models.Composant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOComposant extends DAOGenerique<Composant> {

    @Override
    public Composant create(Composant composant) {
        try {
            String query = "INSERT INTO composants (reference, famille, prix_unitaire, quantite_en_stock) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, composant.reference);
            stmt.setString(2, composant.famille);
            stmt.setFloat(3, composant.prix);
            stmt.setInt(4, composant.quantite);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la creation d'un composant : " + e.getMessage());
        }
        return composant;
    }

    @Override
    public Composant update(Composant composant) {
        try {
            String query = "UPDATE composants SET famille = ?, prix_unitaire = ?, quantite_en_stock = ? WHERE reference = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, composant.famille);
            stmt.setFloat(2, composant.prix);
            stmt.setInt(3, composant.quantite);
            stmt.setString(4, composant.reference);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la mise à jour d'un composant : " + e.getMessage());
        }
        return composant;
    }

    @Override
    public void delete(Composant composant) {
        try {
            String query = "DELETE FROM composants WHERE reference = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, composant.reference);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la suppression d'un composant : " + e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Composant> composants) {
        for (Composant composant : composants) {
            create(composant);
        }
    }

    @Override
    public Composant findById(String id) {
        Composant composant = null;
        try {
            String query = "SELECT * FROM composants WHERE reference = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix_unitaire"),
                        rs.getInt("quantite_en_stock"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un composant par id : " + e.getMessage());
        }
        return composant;
    }

    @Override
    public List<Composant> findAll() {
        List<Composant> composants = new ArrayList<>();
        try {
            String query = "SELECT * FROM composants";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Composant composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix_unitaire"),
                        rs.getInt("quantite_en_stock"));
                composants.add(composant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de tous les composants : " + e.getMessage());
        }
        return composants;
    }

    @Override
    public List<Composant> findByName(String name) {
        List<Composant> composants = new ArrayList<>();
        try {
            String query = "SELECT * FROM composants WHERE famille = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Composant composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix_unitaire"),
                        rs.getInt("quantite_en_stock"));
                composants.add(composant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un composant par nom : " + e.getMessage());
        }
        return composants;
    }
}