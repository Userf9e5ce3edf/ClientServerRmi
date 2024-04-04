package Models;

import Interfaces.IClient;

import java.io.Serial;
import java.io.Serializable;

public class Client implements IClient, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String nom;
    private String prenom;
    private String adresse;

    public Client(final String nom, final String adresse) {
        this.nom = nom;
        this.adresse = adresse;
    }
    public Client(final String nom, final String adresse, final int id) {
        this.nom = nom;
        this.adresse = adresse;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public String getAdresse() {
        return adresse;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
