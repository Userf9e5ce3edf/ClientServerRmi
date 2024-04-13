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

public class DAOFactureItem extends DAOGenerique<FactureItem> {

    private MySQLManager mySQLManager = MySQLManager.getInstance();

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

    @Override
    public void saveAll(List<FactureItem> factureItems) {
        for (FactureItem factureItem : factureItems) {
            create(factureItem);
        }
    }

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

    @Override
    public List<FactureItem> findByName(String name) {
        return null;
    }
}
