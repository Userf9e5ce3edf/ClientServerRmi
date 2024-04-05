package Interfaces;

import Models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.*;
import java.util.Map;

public interface IRequete extends Remote {
    public String VoirStock(String refComposant) throws RemoteException;
    public List<String> GetAllFamilles() throws RemoteException;
    public List<Composant> RechercheComposant(String famille) throws RemoteException;
    /*
     * Ajouter un composant au panier
     * @param refComposant : reference du composant
     * @param quantite : quantite du composant
     * @param nomClient : nom du client
     * @return true si l'ajout a ete effectue, false sinon
     * Ajoute créer une nouvelle ligne dans la table FactureItem
     * si le composant n'existe pas dans le panier du client
     * sinon, met à jour la quantité du composant
     * Si le client n'a pas de facture en cours, une nouvelle facture est créée
     */
    public boolean ajouterAuPanier(String refComposant, int quantite, String nomClient) throws RemoteException;

    /*
     * Ajouter un composant au stock
     * Si la reference du composant existe, met à jour la quantité
     * sinon return false
     * Si la quantité en stock depasse 100, return false
     * @param refComposant : reference du composant
     * @param quantite : quantite du composant
     * @return true si l'ajout a ete effectue, false sinon
     */
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException;
   public  boolean retirerComposantDuStock(String refComposant, int quantite) throws RemoteException;
    public boolean payerFacture(String nomClient, EnumModeDePaiment modeDePaiment) throws RemoteException;
    public String ConsulterFacture(String nomClient) throws RemoteException;
    public Facture getFacture(int idClient) throws RemoteException;
    public List<FactureItem> getAllFactureItem(int idFacture) throws RemoteException;
    public boolean retirerDuPanier(int quantite, int  id) throws RemoteException;
    // CRUD operations for Client
    public Client createClient(String nom, String adresse) throws RemoteException;
    public boolean deleteClient(int id) throws RemoteException;
    public Client updateClient(int id, String nom, String adresse) throws RemoteException;
    public Client getClient(int id) throws RemoteException;
    public List<Client> getAllClients() throws RemoteException;
}
