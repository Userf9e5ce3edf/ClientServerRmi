package Interfaces;
import Models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Cette interface définit les méthodes pour gérer un stock de composants,
 * gérer les commandes et les paiements des clients, et effectuer des opérations CRUD sur les clients.
 */
public interface IRequete extends Remote {

    /**
     * Méthode pour consulter le stock d'un composant spécifique.
     * @param refComposant La référence du composant.
     * @return Le statut du stock du composant.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public String VoirStock(String refComposant) throws RemoteException;

    /**
     * Méthode pour obtenir toutes les familles de composants.
     * @return Une liste de toutes les familles de composants.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public List<String> GetAllFamilles() throws RemoteException;

    /**
     * Méthode pour rechercher un composant par sa famille.
     * @param famille La famille du composant.
     * @return Une liste de composants appartenant à la famille spécifiée.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public List<Composant> RechercheComposant(String famille) throws RemoteException;

    /**
     * Méthode pour ajouter un composant au panier du client.
     * @param refComposant La référence du composant.
     * @param quantite La quantité du composant.
     * @param nomClient Le nom du client.
     * @return Vrai si l'ajout a réussi, faux sinon.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public boolean ajouterAuPanier(String refComposant, int quantite, String nomClient) throws RemoteException;

    /**
     * Méthode pour ajouter un composant au stock.
     * @param refComposant La référence du composant.
     * @param quantite La quantité du composant.
     * @return Vrai si l'ajout a réussi, faux sinon.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException;

    /**
     * Méthode pour retirer un composant du stock.
     * @param refComposant La référence du composant.
     * @param quantite La quantité du composant.
     * @return Vrai si le retrait a réussi, faux sinon.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public boolean retirerComposantDuStock(String refComposant, int quantite) throws RemoteException;

    /**
     * Méthode pour qu'un client paie sa facture.
     * @param nomClient Le nom du client.
     * @param modeDePaiment Le mode de paiement.
     * @return Vrai si le paiement a réussi, faux sinon.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public boolean payerFacture(String nomClient, EnumModeDePaiement modeDePaiment) throws RemoteException;

    /**
     * Méthode pour qu'un client consulte sa facture.
     * @param nomClient Le nom du client.
     * @return La facture du client.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public String ConsulterFacture(String nomClient) throws RemoteException;

    /**
     * Méthode pour obtenir la facture en cours d'un client.
     * @param idClient L'ID du client.
     * @return La facture en cours du client.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public Facture getFactureEnCours(int idClient) throws RemoteException;

    /**
     * Méthode pour obtenir tous les articles d'une facture.
     * @param idFacture L'ID de la facture.
     * @return Une liste de tous les articles de la facture.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public List<FactureItem> getAllFactureItem(int idFacture) throws RemoteException;

    /**
     * Méthode pour retirer un article du panier du client.
     * @param quantite La quantité de l'article.
     * @param id L'ID de l'article.
     * @return Vrai si le retrait a réussi, faux sinon.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public boolean retirerDuPanier(int quantite, int  id) throws RemoteException;

    /**
     * Méthode pour créer un nouveau client.
     * @param nom Le nom du client.
     * @param prenom Le prénom du client.
     * @param adresse L'adresse du client.
     * @return Le client créé.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public Client createClient(String nom,String prenom, String adresse) throws RemoteException;

    /**
     * Méthode pour supprimer un client.
     * @param id L'ID du client.
     * @return Vrai si la suppression a réussi, faux sinon.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public boolean deleteClient(int id) throws RemoteException;

    /**
     * Méthode pour mettre à jour les informations d'un client.
     * @param id L'ID du client.
     * @param nom Le nom du client.
     * @param prenom Le prénom du client.
     * @param adresse L'adresse du client.
     * @return Le client mis à jour.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public Client updateClient(int id, String nom,String prenom, String adresse) throws RemoteException;

    /**
     * Méthode pour obtenir un client par son ID.
     * @param id L'ID du client.
     * @return Le client avec l'ID spécifié.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public Client getClient(int id) throws RemoteException;

    /**
     * Méthode pour obtenir tous les clients.
     * @return Une liste de tous les clients.
     * @throws RemoteException Si une exception liée à la communication se produit.
     */
    public List<Client> getAllClients() throws RemoteException;
}