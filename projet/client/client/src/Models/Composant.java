package Models;

import java.io.Serializable;

public class Composant implements Serializable {
    public int id;
    public String reference;
    public String famille;
    public float prix;
    public int quantite;
    public Composant(String reference, String famille, float prix, int quantite) {
        this.reference = reference;
        this.famille = famille;
        this.prix = prix;
        this.quantite = quantite;
    }

    public String getReference() {
        return reference;
    }
    public String getFamille() {
        return famille;
    }
    public float getPrix() {
        return prix;
    }
    public int getQuantite() {
        return quantite;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

