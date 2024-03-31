package Models;

public class Client {
    private int id;
    private final String nom;
    private final String adresse;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
