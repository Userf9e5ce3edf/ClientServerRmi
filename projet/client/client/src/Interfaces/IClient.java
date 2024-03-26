package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    public String getNom() throws RemoteException;
    public String getAdresse() throws RemoteException;
    public double getTotalFacture() throws RemoteException;
    public String getModePaiement() throws RemoteException;
}
