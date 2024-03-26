package DAO;

import Models.Client;
import Models.RelationBaseDeDonnees;

import java.util.List;

public class DAOClient extends DAOGenerique<Client> {
    private RelationBaseDeDonnees baseDeDonnees;

    public DAOClient() {
        baseDeDonnees = new RelationBaseDeDonnees();
    }

    @Override
    public Client create(Client obj) {
        // Implement the create method
        return obj;
    }

    @Override
    public Client update(Client obj) {
        // Implement the update method
    }

    @Override
    public void delete(Client obj) {
        // Implement the delete method
    }

    @Override
    public void saveAll(List<Client> obj) {

    }

    @Override
    public Client findById(String id) {
        // Implement the findById method
        return null;
    }

    @Override
    public List<Client> findAll() {
        // Implement the findAll method
        return null;
    }

    @Override
    public List<Client> findByName(String name) {
        // Implement the findByName method
        return null;
    }
}