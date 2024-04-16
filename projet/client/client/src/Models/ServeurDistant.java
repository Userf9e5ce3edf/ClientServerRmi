package Models;

import Interfaces.IRequete;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * La classe ClientDistant est utilisée pour établir une connexion à un serveur distant via RMI.
 * Elle utilise le design pattern Singleton pour s'assurer qu'une seule instance de cette classe est créée.
 */
public class ServeurDistant {
    // L'instance unique de la classe
    private static ServeurDistant instance = null;
    // Le stub pour l'interface de la requête RMI
    public IRequete stub = null;
    // L'adresse IP de l'hôte
    private static String hostIP = "127.0.0.1";

    /**
     * Constructeur privé pour le design pattern Singleton.
     * Il initialise le stub RMI.
     * @throws RemoteException si une erreur de communication se produit pendant l'appel de la méthode distante
     * @throws NotBoundException si le nom recherché n'est pas actuellement lié
     */
    private ServeurDistant() throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(hostIP);
        stub = (IRequete) reg.lookup("Requete");
    }

    /**
     * Méthode pour obtenir l'instance unique de la classe.
     * Si l'instance n'existe pas, elle est créée.
     * @return l'instance unique de la classe
     * @throws RemoteException si une erreur de communication se produit pendant l'appel de la méthode distante
     * @throws NotBoundException si le nom recherché n'est pas actuellement lié
     */
    public static ServeurDistant getInstance() throws RemoteException, NotBoundException {
        if (instance == null) {
            instance = new ServeurDistant();
        }
        return instance;
    }

    /**
     * Méthode pour définir l'adresse IP de l'hôte.
     * Elle réinitialise également l'instance unique de la classe.
     * @param newHostIP la nouvelle adresse IP de l'hôte
     */
    public static void setHostIP(String newHostIP) {
        hostIP = newHostIP;
        instance = null;
    }
}