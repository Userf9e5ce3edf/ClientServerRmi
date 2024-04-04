package Models;

public class FactureItem {
    private int id;
    private Composant composant;
    private Facture facture;
    private int quantite;

    public FactureItem(int id, Composant composant, Models.Facture facture, int quantite) {
        this.id = id;
        this.composant = composant;
        this.facture = facture;
        this.quantite = quantite;
    }

    public FactureItem(Composant composant, Models.Facture facture, int quantite) {
        this.composant = composant;
        this.facture = facture;
        this.quantite = quantite;
    }

    public Composant getComposant() {
        return composant;
    }

    public void setComposant(Composant composant) {
        this.composant = composant;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}