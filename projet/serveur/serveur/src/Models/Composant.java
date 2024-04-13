package Models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Cette classe représente un Composant avec des propriétés comme id, référence, famille, prix et quantité.
 * Elle implémente l'interface Serializable pour supporter la sérialisation et la désérialisation.
 */
public class Composant implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    public int id;
    public String reference;
    public String famille;
    public float prix;
    public int quantite;

    /**
     * Constructeur pour initialiser un Composant avec référence, famille, prix et quantité.
     * @param reference Référence du composant.
     * @param famille Famille du composant.
     * @param prix Prix du composant.
     * @param quantite Quantité du composant.
     */
    public Composant(String reference, String famille, float prix, int quantite) {
        this.reference = reference;
        this.famille = famille;
        this.prix = prix;
        this.quantite = quantite;
    }

    /**
     * Getter pour la référence du composant.
     * @return Référence du composant.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Getter pour la famille du composant.
     * @return Famille du composant.
     */
    public String getFamille() {
        return famille;
    }

    /**
     * Getter pour le prix du composant.
     * @return Prix du composant.
     */
    public float getPrix() {
        return prix;
    }

    /**
     * Getter pour la quantité du composant.
     * @return Quantité du composant.
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Getter pour l'id du composant.
     * @return Id du composant.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'id du composant.
     * @param id Id à définir pour le composant.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode toString redéfinie pour retourner une représentation en chaîne de caractères du composant.
     * @return Représentation en chaîne de caractères du composant.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Composant{");
        sb.append("reference='").append(reference).append('\'');
        sb.append(", famille='").append(famille).append('\'');
        sb.append(", prix=").append(prix);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}