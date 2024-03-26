package DAO;

import Models.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOClient extends DAOGenerique<Client> {
    @Override
    public Client create(Client client) {
        try {
            String query = "INSERT INTO clients (nom, adresse) VALUES (?, ?)";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, client.nom);
            stmt.setString(2, client.adresse);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la creation d'un client : " + e.getMessage());
        }
        return client;
    }

    @Override
    public Client update(Client client) {
        try {
            String query = "UPDATE clients SET adresse = ?, total_facture = ?, mode_paiement = ? WHERE nom = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, client.adresse);
            stmt.setString(4, client.nom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la mise à jour d'un client : " + e.getMessage());
        }
        return client;
    }

    @Override
    public void delete(Client client) {
        try {
            String query = "DELETE FROM clients WHERE nom = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, client.nom);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la suppression d'un client : " + e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Client> clients) {
        for (Client client : clients) {
            create(client);
        }
    }

    @Override
    public Client findById(String id) {
        Client client = null;
        try {
            String query = "SELECT * FROM clients WHERE id = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = new Client(
                        rs.getString("nom"),
                        rs.getString("adresse"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un client par id : " + e.getMessage());
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            String query = "SELECT * FROM clients";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("adresse"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche de tous les clients : " + e.getMessage());
        }
        return clients;
    }

    @Override
    public List<Client> findByName(String name) {
        List<Client> clients = new ArrayList<>();
        try {
            String query = "SELECT * FROM clients WHERE nom = ?";
            PreparedStatement stmt = mySQLManager.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("adresse"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la recherche d'un client par nom : " + e.getMessage());
        }
        return clients;
    }
}