package DAO;

import Models.Composant;
import datasourceManagement.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOComposant extends DAOGenerique<Composant> {
    private MySQLManager mySQLManager = MySQLManager.getInstance();

    @Override
    public Composant create(Composant composant) {
        try {
            String query = "INSERT INTO composants (" +
                    "reference, famille, prix, quantite) VALUES ('"
                    + composant.getReference() + "', '"
                    + composant.getFamille() + "', "
                    + composant.getPrix() + ", "
                    + composant.getQuantite() + ")";
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la creation d'un composant : "
                    + e.getMessage());
        }
        return composant;
    }

    @Override
    public Composant update(Composant composant) {
        try {
            String query = "UPDATE composants SET famille = '"
                    + composant.getFamille()
                    + "', prix = " + composant.getPrix()
                    + ", quantite = " + composant.getQuantite()
                    + " WHERE reference = '" + composant.getReference() + "'";
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la mise à jour d'un composant : "
                    + e.getMessage());
        }
        return composant;
    }

    @Override
    public void delete(Composant composant) {
        try {
            String query = "DELETE FROM composants WHERE reference = '"
                    + composant.getReference() + "'";
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la suppression d'un composant : "
                    + e.getMessage());
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
            String query = "SELECT * FROM composants WHERE reference = '"
                    + id + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix"),
                        rs.getInt("quantite"));
                composant.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un composant par id : "
                    + e.getMessage());
        }
        return composant;
    }

    @Override
    public Composant findBySomeField(String nomChamp, String valeur) {
        Composant composant = null;
        try {
            String query = "SELECT * FROM composants WHERE "
                    + nomChamp + " = '" + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix"),
                        rs.getInt("quantite"));
                composant.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un composant par "
                    + nomChamp + " : " + e.getMessage());
        }
        return composant;
    }

    @Override
    public List<Composant> findAllBySomeField(String fieldName, String valeur) {
        List<Composant> composants = new ArrayList<>();
        try {
            String query = "SELECT * FROM composants WHERE " + fieldName + " = '" + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Composant composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix"),
                        rs.getInt("quantite"));
                composant.setId(rs.getInt("id"));
                composants.add(composant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de composants par "
                    + fieldName + " : " + e.getMessage());
        }
        return composants;
    }

    @Override
    public List<Composant> findAll() {
        List<Composant> composants = new ArrayList<>();
        try {
            String query = "SELECT * FROM composants";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Composant composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix"),
                        rs.getInt("quantite"));
                composant.setId(rs.getInt("id"));
                composants.add(composant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de tous les composants : "
                    + e.getMessage());
        }
        return composants;
    }

    @Override
    public List<Composant> findByName(String name) {
        List<Composant> composants = new ArrayList<>();
        try {
            String query = "SELECT * FROM composants WHERE famille = '"
                    + name + "'";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Composant composant = new Composant(
                        rs.getString("reference"),
                        rs.getString("famille"),
                        rs.getFloat("prix"),
                        rs.getInt("quantite"));
                composant.setId(rs.getInt("id"));
                composants.add(composant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un composant par nom : "
                    + e.getMessage());
        }
        return composants;
    }
}