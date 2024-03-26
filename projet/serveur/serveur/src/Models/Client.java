package Models;

public class Client {
    public String nom;
    public String adresse;
    public double total_facture;
    public String mode_paiement;

    public Client(String nom, String adresse, double total_facture, String mode_paiement) {
        this.nom = nom;
        this.adresse = adresse;
        this.total_facture = total_facture;
        this.mode_paiement = mode_paiement;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append(", total_facture=").append(total_facture);
        sb.append(", mode_paiement='").append(mode_paiement).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
