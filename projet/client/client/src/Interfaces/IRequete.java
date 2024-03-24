package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRequete extends Remote {
    public String VoirStock(String refComposant) throws RemoteException;
    public List<String> RechercheComposant(String famille) throws RemoteException;
    public boolean acheterComposant(String refComposant, int quantite) throws RemoteException;
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException;
    public boolean payerFacture(String nomClient, double montant) throws RemoteException;
    public String ConsulterFacture(String nomClient) throws RemoteException;
}