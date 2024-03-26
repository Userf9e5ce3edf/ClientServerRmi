package DAO;

import Models.Composant;
import Models.RelationBaseDeDonnees;

import java.util.List;

public class DAOComposant extends DAOGenerique<Composant> {
    private RelationBaseDeDonnees baseDeDonnees;

    public DAOComposant() {
        baseDeDonnees = new RelationBaseDeDonnees();
    }

    @Override
    public Composant create(Composant obj) {
        // Implement the create method
        return obj;
    }

    @Override
    public Composant update(Composant obj) {
        // Implement the update method
        return obj;
    }

    @Override
    public void delete(Composant obj) {
        // Implement the delete method
    }

    @Override
    public void saveAll(List<Composant> obj) {

    }

    @Override
    public Composant findById(String id) {
        // Implement the findById method
        return null;
    }

    @Override
    public List<Composant> findAll() {
        // Implement the findAll method
        return null;
    }

    @Override
    public List<Composant> findByName(String name) {
        // Implement the findByName method
        return null;
    }
}