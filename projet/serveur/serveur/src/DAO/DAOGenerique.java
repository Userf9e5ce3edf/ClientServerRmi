package DAO;

import datasourceManagement.MySQLManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe abstraite DAOGenerique qui définit les méthodes de base pour un DAO.
 * @param <T> Le type d'objet que le DAO gère.
 */
public abstract class DAOGenerique<T> {
    // Instance de MySQLManager pour gérer la connexion à la base de données.
    public MySQLManager mySQLManager = MySQLManager.getInstance();

    /**
     * Crée un nouvel objet dans la base de données.
     * @param obj L'objet à créer.
     * @return L'objet créé.
     */
    public abstract T create(T obj);

    /**
     * Met à jour un objet existant dans la base de données.
     * @param obj L'objet à mettre à jour.
     * @return L'objet mis à jour.
     */
    public abstract T update(T obj);

    /**
     * Supprime un objet de la base de données.
     * @param obj L'objet à supprimer.
     */
    public abstract void delete(T obj);

    /**
     * Trouve un objet par son identifiant.
     * @param cle L'identifiant de l'objet.
     * @return L'objet trouvé, ou null si aucun objet n'a été trouvé.
     */
    public abstract T findById(String cle);

    /**
     * Trouve un objet par une valeur de champ spécifique.
     * @param fieldName Le nom du champ.
     * @param valeur La valeur du champ.
     * @return L'objet trouvé, ou null si aucun objet n'a été trouvé.
     */
    public abstract T findBySomeField(String fieldName,String valeur);

    /**
     * Trouve tous les objets qui ont une valeur de champ spécifique.
     * @param fieldName Le nom du champ.
     * @param valeur La valeur du champ.
     * @return Une liste d'objets trouvés, ou une liste vide si aucun objet n'a été trouvé.
     */
    public abstract List<T> findAllBySomeField(String fieldName,String valeur);

    /**
     * Trouve tous les objets dans la base de données.
     * @return Une liste de tous les objets, ou une liste vide si aucun objet n'a été trouvé.
     */
    public abstract List<T> findAll();
}