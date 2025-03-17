package DAO;

import Models.FactureItem;
import Models.Composant;
import Models.Facture;
import datasourceManagement.MySQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe DAO pour les éléments de facture (FactureItem).
 * Cette classe hérite de DAOGenerique et implémente ses méthodes pour FactureItem.
 */
public class DAOFactureItem extends DAOGenerique<FactureItem> {

    // Instance du gestionnaire MySQL
    private MySQLManager mySQLManager = MySQLManager.getInstance();

    /**
     * Crée un nouvel élément de facture dans la base de données.
     * @param factureItem L'élément de facture à créer.
     * @return L'élément de facture avec son ID de base de données.
     */
    @Override
    public FactureItem create(FactureItem factureItem) {
        int id = -1;
        try {
            String query = "INSERT INTO factureItems (idcomposant, idfacture, quantite) VALUES ('"
                    + factureItem.getComposant().getId() + "', '"
                    + factureItem.getFacture().getId() + "', "
                    + factureItem.getQuantite() + ")";
            id = mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la creation d'un FactureItem : "
                    + e.getMessage());
        }

        factureItem.setId(id);
        return factureItem;
    }

    /**
     * Met à jour un élément de facture existant dans la base de données.
     * @param factureItem L'élément de facture à mettre à jour.
     * @return L'élément de facture mis à jour.
     */
    @Override
    public FactureItem update(FactureItem factureItem) {
        try {
            String query = "UPDATE FactureItems SET quantite = "
                + factureItem.getQuantite()
                + " WHERE id = " + factureItem.getId();
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la mise à jour d'un FactureItem : "
                    + e.getMessage());
        }
        return factureItem;
    }

    /**
     * Supprime un élément de facture de la base de données.
     * @param factureItem L'élément de facture à supprimer.
     */
    @Override
    public void delete(FactureItem factureItem) {
        try {
            String query = "DELETE FROM FactureItems WHERE id = '"
                    + factureItem.getId() + "'";
            mySQLManager.setData(query);
        } catch (Exception e) {
            System.err.println("Erreur SQL lors de la suppression d'un FactureItem : "
                    + e.getMessage());
        }
    }

    /**
     * Trouve un élément de facture par son ID.
     * @param id L'ID de l'élément de facture à trouver.
     * @return L'élément de facture trouvé, ou null si aucun élément de facture n'a été trouvé avec cet ID.
     */
    @Override
    public FactureItem findById(String id) {
        FactureItem factureItem = null;
        try {
            String query = "SELECT * FROM FactureItems WHERE id = '"
                    + id + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                Composant composant = new DAOComposant().findById(rs.getString("idcomposant"));
                Facture facture = new DAOFacture().findById(rs.getString("idfacture"));
                factureItem = new FactureItem(
                        rs.getInt("id"),
                        composant,
                        facture,
                        rs.getInt("quantite"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un FactureItem par id : "
                    + e.getMessage());
        }
        return factureItem;
    }

    /**
     * Trouve un élément de facture par un champ spécifique.
     * @param fieldName Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return L'élément de facture trouvé, ou null si aucun élément de facture n'a été trouvé avec cette valeur de champ.
     */
    @Override
    public FactureItem findBySomeField(String fieldName, String valeur) {
        FactureItem factureItem = null;
        try {
            String query = "SELECT * FROM factureitems WHERE " + fieldName + " = '"
                    + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            if (rs.next()) {
                Composant composant = new DAOComposant().findById(String.valueOf(rs.getInt("idcomposant")));
                Facture facture = new DAOFacture().findById(String.valueOf(rs.getInt("idfacture")));
                factureItem = new FactureItem(
                        rs.getInt("id"),
                        composant,
                        facture,
                        rs.getInt("quantite"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un FactureItem par " + fieldName + " : "
                    + e.getMessage());
        }
        return factureItem;
    }

    /**
     * Trouve un élément de facture par plusieurs champs.
     * @param fields Une carte des noms de champs et des valeurs à utiliser pour la recherche.
     * @return L'élément de facture trouvé, ou null si aucun élément de facture n'a été trouvé avec ces valeurs de champ.
     */
    public FactureItem findBySomeFields(Map<String, String> fields) {
        FactureItem factureItem = null;
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM factureitems WHERE ");
            boolean isFirst = true;
            for (Map.Entry<String, String> field : fields.entrySet()) {
                if (!isFirst) {
                    query.append(" AND ");
                }
                query.append(field.getKey()).append(" = '").append(field.getValue()).append("'");
                isFirst = false;
            }
            ResultSet rs = mySQLManager.getData(query.toString());
            if (rs.next()) {
                Composant composant = new DAOComposant().findById(String.valueOf(rs.getInt("idcomposant")));
                Facture facture = new DAOFacture().findById(String.valueOf(rs.getInt("idfacture")));
                factureItem = new FactureItem(
                        rs.getInt("id"),
                        composant,
                        facture,
                        rs.getInt("quantite"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un FactureItem par " + fields.keySet() + " : " + e.getMessage());
        }
        return factureItem;
    }

    /**
     * Trouve tous les éléments de facture avec une valeur de champ spécifique.
     * @param fieldName Le nom du champ à utiliser pour la recherche.
     * @param valeur La valeur du champ à utiliser pour la recherche.
     * @return Une liste des éléments de facture trouvés, ou une liste vide si aucun élément de facture n'a été trouvé avec cette valeur de champ.
     */
    @Override
    public List<FactureItem> findAllBySomeField(String fieldName, String valeur) {
        List<FactureItem> factureItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM FactureItems WHERE " + fieldName + " = '"
                    + valeur + "'";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Composant composant = new DAOComposant().findById(String.valueOf(rs.getInt("idcomposant")));
                Facture facture = new DAOFacture().findById(String.valueOf(rs.getInt("idfacture")));
                FactureItem factureItem = new FactureItem(
                        rs.getInt("id"),
                        composant,
                        facture,
                        rs.getInt("quantite"));
                factureItems.add(factureItem);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de FactureItems par " + fieldName + " : "
                    + e.getMessage());
        }
        return factureItems;
    }

    /**
     * Trouve tous les éléments de facture dans la base de données.
     * @return Une liste de tous les éléments de facture, ou une liste vide si aucun élément de facture n'a été trouvé.
     */
    @Override
    public List<FactureItem> findAll() {
        List<FactureItem> factureItems = new ArrayList<>();
        try {
            String query = "SELECT * FROM FactureItems";
            ResultSet rs = mySQLManager.getData(query);
            while (rs.next()) {
                Composant composant = new DAOComposant().findById(rs.getString("idcomposant"));
                Facture facture = new DAOFacture().findById(rs.getString("idfacture"));
                FactureItem factureItem = new FactureItem(
                        rs.getInt("id"),
                        composant,
                        facture,
                        rs.getInt("quantite"));
                factureItems.add(factureItem);
            }
        } catch (SQLException e) {
        }
        return factureItems;
    }

}
