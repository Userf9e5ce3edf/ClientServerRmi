package Models;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Remote;

/**
 * Cette classe représente un élément de Facture avec des propriétés comme id, composant, facture et quantité.
 * Elle implémente les interfaces Remote et Serializable pour supporter la communication RMI et la sérialisation/désérialisation.
 */
public class FactureItem implements Remote, Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private int id;
    private Composant composant;
    private Facture facture;
    private int quantite;

    /**
     * Constructeur pour initialiser un élément de Facture avec id, composant, facture et quantité.
     * @param id Id de l'élément de facture.
     * @param composant Composant associé à l'élément de facture.
     * @param facture Facture associée à l'élément de facture.
     * @param quantite Quantité du composant dans l'élément de facture.
     */
    public FactureItem(int id, Composant composant, Facture facture, int quantite) {
        this.id = id;
        this.composant = composant;
        this.facture = facture;
        this.quantite = quantite;
    }

    /**
     * Constructeur pour initialiser un élément de Facture avec composant, facture et quantité.
     * @param composant Composant associé à l'élément de facture.
     * @param facture Facture associée à l'élément de facture.
     * @param quantite Quantité du composant dans l'élément de facture.
     */
    public FactureItem(Composant composant, Facture facture, int quantite) {
        this.composant = composant;
        this.facture = facture;
        this.quantite = quantite;
    }

    /**
     * Getter pour le composant de l'élément de facture.
     * @return Composant de l'élément de facture.
     */
    public Composant getComposant() {
        return composant;
    }

    /**
     * Setter pour le composant de l'élément de facture.
     * @param composant Composant à définir pour l'élément de facture.
     */
    public void setComposant(Composant composant) {
        this.composant = composant;
    }

    /**
     * Getter pour la facture associée à l'élément de facture.
     * @return Facture associée à l'élément de facture.
     */
    public Facture getFacture() {
        return facture;
    }

    /**
     * Setter pour la facture associée à l'élément de facture.
     * @param facture Facture à définir pour l'élément de facture.
     */
    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    /**
     * Getter pour la quantité du composant dans l'élément de facture.
     * @return Quantité du composant dans l'élément de facture.
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Setter pour la quantité du composant dans l'élément de facture.
     * @param quantite Quantité à définir pour le composant dans l'élément de facture.
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Getter pour l'id de l'élément de facture.
     * @return Id de l'élément de facture.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'id de l'élément de facture.
     * @param id Id à définir pour l'élément de facture.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode toString redéfinie pour retourner une représentation en chaîne de caractères de l'élément de facture.
     * @return Représentation en chaîne de caractères de l'élément de facture.
     */
    @Override
    public String toString() {
        return "FactureItem{" +
                "id=" + id +
                ", composant=" + composant +
                ", facture=" + facture +
                ", quantite=" + quantite +
                '}';
    }
}