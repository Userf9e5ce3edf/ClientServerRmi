package Models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Cette classe représente un Client avec des propriétés comme id, nom, prénom et adresse.
 * Elle implémente l'interface Serializable pour supporter la sérialisation et la désérialisation.
 */
public class Client implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String nom;
    private String prenom;
    private String adresse;

    /**
     * Constructeur pour initialiser un Client avec nom, prénom et adresse.
     * @param nom Nom du client.
     * @param prenom Prénom du client.
     * @param adresse Adresse du client.
     */
    public Client(final String nom, final String prenom, final String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    /**
     * Constructeur pour initialiser un Client avec nom, prénom, adresse et id.
     * @param nom Nom du client.
     * @param prenom Prénom du client.
     * @param adresse Adresse du client.
     * @param id Id du client.
     */
    public Client(final String nom, final String prenom, final String adresse, final int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.id = id;
    }

    /**
     * Getter pour le nom du client.
     * @return Nom du client.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter pour le prénom du client.
     * @return Prénom du client.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Getter pour l'adresse du client.
     * @return Adresse du client.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Getter pour l'id du client.
     * @return Id du client.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'id du client.
     * @param id Id à définir pour le client.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter pour le nom du client.
     * @param nom Nom à définir pour le client.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter pour le prénom du client.
     * @param prenom Prénom à définir pour le client.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Setter pour l'adresse du client.
     * @param adresse Adresse à définir pour le client.
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Méthode toString redéfinie pour retourner une représentation en chaîne de caractères du client.
     * @return Représentation en chaîne de caractères du client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}