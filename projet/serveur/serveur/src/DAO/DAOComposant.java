package DAO;

import Models.Composant;
import datasourceManagement.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAOComposant qui hérite de DAOGenerique.
 * Cette classe permet de manipuler les données de la table 'composants' dans la base de données.
 */
public class DAOComposant extends DAOGenerique<Composant> {
    private MySQLManager mySQLManager = MySQLManager.getInstance();

    /**
     * Crée un nouveau composant dans la base de données.
     * @param composant L'objet Composant à ajouter dans la base de données.
     * @return L'objet Composant avec l'ID généré.
     */
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

    /**
     * Met à jour un composant existant dans la base de données.
     * @param composant L'objet Composant à mettre à jour dans la base de données.
     * @return L'objet Composant mis à jour.
     */
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

    /**
     * Supprime un composant de la base de données.
     * @param composant L'objet Composant à supprimer de la base de données.
     */
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

    /**
     * Trouve un composant par son ID dans la base de données.
     * @param id L'ID du composant à trouver.
     * @return L'objet Composant trouvé, ou null si aucun composant n'a été trouvé.
     */
    @Override
    public Composant findById(String id) {
        Composant composant = null;
        try {
            String query = "SELECT * FROM composants WHERE id = '"
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
    /**
     * Trouve un composant par un champ spécifique dans la base de données.
     * @param nomChamp Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return L'objet Composant trouvé, ou null si aucun composant n'a été trouvé.
     */

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

    /**
     * Trouve tous les composants qui ont une certaine valeur pour un champ spécifique.
     * @param fieldName Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return Une liste de composants qui correspondent à la recherche.
     */
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

    /**
     * Trouve tous les composants dans la base de données.
     * @return Une liste de tous les composants.
     */
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

    /**
     * Trouve toutes les familles de composants dans la base de données.
     * @return Une liste de toutes les familles de composants.
     */
    public List<String> findAllFamilles() {
        List<String> familles = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT famille FROM composants";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                familles.add(rs.getString("famille"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de toutes les familles : "
                    + e.getMessage());
        }
        return familles;
    }
}