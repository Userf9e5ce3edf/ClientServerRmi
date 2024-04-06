package Interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    public String getNom() throws RemoteException;
    public String getPrenom() throws RemoteException;
    public String getAdresse() throws RemoteException;
    public int getId() throws RemoteException;
    public void setId(int id) throws RemoteException;
    public void setNom(String nom) throws RemoteException;
    public void setPrenom(String prenom) throws RemoteException;
    public void setAdresse(String adresse) throws RemoteException;
}
