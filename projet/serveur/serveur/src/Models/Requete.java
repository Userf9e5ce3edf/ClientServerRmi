package Models;

import Interfaces.IRequete;

import javax.management.relation.Relation;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Requete implements IRequete {
    public String VoirStock(String refComposant) {
        List<Composant> composants = new ArrayList<>();

        final String condition = "reference = '" + refComposant + "'";
        final String nomTable = "composants";

        // Connection a la base de donnees
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();
        composants = (List<Composant>) baseDeDonnees.SelectAllWhere(nomTable, condition);

        // Deconnection de la base de donnees
        baseDeDonnees.close();

        return composants.get(0).toString();
    }
    @Override
    public List<String> RechercheComposant(String famille) throws RemoteException {
        /*
        	rechercher un composant : en donnant une famille de composant,
        	on doit pouvoir récupérer toutes les références des composants de cette famille.
        	Seules les références dont le stock n’est pas nul doivent être retournées ;
        */
        List<String> references = new ArrayList<>();

        // Connection to the database
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();

        // Select all components where the family is equal to the input parameter and the quantity is not zero
        String condition = "famille = '" + famille + "' AND quantite_en_stock > 0";
        String nomTable = "composants";
        List<Composant> composants = (List<Composant>) baseDeDonnees.SelectAllWhere(nomTable, condition);

        // Iterate over the returned list of components
        for (Composant composant : composants) {
            // For each component, add the reference to the list of component references
            references.add(composant.reference);
        }

        // Close the database connection
        baseDeDonnees.close();

        return references;
    }
    @Override
    public boolean acheterComposant(String refComposant, int quantite, String nomClient) throws RemoteException {
        /*
        	acheter un composant : un client doit pouvoir acheter un composant en stock.
        	S’il demande plus d’exemplaires qu’il n’y en a en stock,
        	la vente doit être complètement refusée ;
        */
        // Connection to the database
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();

        // Select the component where the reference is equal to the input parameter
        String condition = "reference = '" + refComposant + "'";
        String nomTable = "composants";
        List<Composant> composants = (List<Composant>) baseDeDonnees.SelectAllWhere(nomTable, condition);

        // If the quantity of the component in stock is less than the quantity requested by the client, return false
        if (composants.get(0).quantite < quantite) {
            baseDeDonnees.close();
            return false;
        }

        // Subtract the quantity requested by the client from the quantity of the component in stock
        int newQuantity = composants.get(0).quantite - quantite;

        // Update the quantity of the component in the database
        String query = "UPDATE " + nomTable + " SET quantite_en_stock = " + newQuantity + " WHERE reference = '" + refComposant + "'";
        try {
            PreparedStatement stmt = baseDeDonnees.conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQl lors de la mise à jour de la quantité d'un composant : " + e.getMessage());
            baseDeDonnees.close();
            return false;
        }

        // Close the database connection
        baseDeDonnees.close();

        return true;
    }
    @Override
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException {
        /*
	        ajouter un produit : on peut ajouter un certain nombre d’exemplaires
	        d’un produit dans le catalogue (la référence du produit doit déjà exister).
        */
        // Connection to the database
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();

        // Select the component where the reference is equal to the input parameter
        String condition = "reference = '" + refComposant + "'";
        String nomTable = "composants";
        List<Composant> composants = (List<Composant>) baseDeDonnees.SelectAllWhere(nomTable, condition);

        // If the component does not exist, return false
        if (composants.isEmpty()) {
            baseDeDonnees.close();
            return false;
        }

        // Add the quantity requested by the client to the quantity of the component in stock
        int newQuantity = composants.get(0).quantite + quantite;

        // Update the quantity of the component in the database
        String query = "UPDATE " + nomTable + " SET quantite_en_stock = " + newQuantity + " WHERE reference = '" + refComposant + "'";
        try {
            PreparedStatement stmt = baseDeDonnees.conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQl lors de l'ajout de la quantité d'un composant : " + e.getMessage());
            baseDeDonnees.close();
            return false;
        }

        // Close the database connection
        baseDeDonnees.close();

        return true;
    }
    @Override
    public boolean payerFacture(String nomClient, double montant) throws RemoteException {
        /*
	        payer une facture : un client peut payer ce qu’il doit à la boutique;
         */
        // Connection to the database
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();

        // Select the client where the name is equal to the input parameter
        String condition = "nom = '" + nomClient + "'";
        String nomTable = "clients";
        List<Client> clients = (List<Client>) baseDeDonnees.SelectAllWhere(nomTable, condition);

        // If the client does not exist or the total bill of the client is less than the amount to be paid, return false
        if (clients.isEmpty() || clients.get(0).total_facture < montant) {
            baseDeDonnees.close();
            return false;
        }

        // Subtract the amount to be paid from the total bill of the client
        double newTotalFacture = clients.get(0).total_facture - montant;

        // Update the total bill of the client in the database
        String query = "UPDATE " + nomTable + " SET total_facture = " + newTotalFacture + " WHERE nom = '" + nomClient + "'";
        try {
            PreparedStatement stmt = baseDeDonnees.conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQl lors de la mise à jour de la facture d'un client : " + e.getMessage());
            baseDeDonnees.close();
            return false;
        }

        // Close the database connection
        baseDeDonnees.close();

        return true;
    }
    @Override
    public String ConsulterFacture(String nomClient) throws RemoteException {
        /*
            consulter une facture : il doit être possible de voir la facture correspondant à un client;
        */
        // Connection to the database
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();

        // Select the client where the name is equal to the input parameter
        String condition = "nom = '" + nomClient + "'";
        String nomTable = "clients";
        List<Client> clients = (List<Client>) baseDeDonnees.SelectAllWhere(nomTable, condition);

        // If the client does not exist, return a message indicating that the client does not exist
        if (clients.isEmpty()) {
            baseDeDonnees.close();
            return "Client does not exist";
        }

        // Return a string representation of the client's bill
        String facture = "Client: " + clients.get(0).nom + "\n" +
                "Total bill: " + clients.get(0).total_facture + "\n" +
                "Payment method: " + clients.get(0).mode_paiement;

        // Close the database connection
        baseDeDonnees.close();

        return facture;
    }
}
