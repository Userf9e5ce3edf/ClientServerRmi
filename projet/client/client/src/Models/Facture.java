package Models;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.Remote;

/**
 * Cette classe représente une Facture avec des propriétés comme id, client, totalFacture, modeDePaiment et statutFacture.
 * Elle implémente les interfaces Remote et Serializable pour supporter la communication RMI et la sérialisation/désérialisation.
 */
public class Facture implements Remote, Serializable {

    @Serial
    private static final long serialVersionUID = 3L;
    private int id;
    private final Client client;
    private double totalFacture;
    private EnumModeDePaiement modeDePaiment;
    private EnumStatutFacture statutFacture;

    /**
     * Constructeur pour initialiser une Facture avec client, totalFacture et modeDePaiment.
     * Le statutFacture est initialisé à EN_COURS par défaut.
     * @param client Client associé à la facture.
     * @param totalFacture Montant total de la facture.
     * @param modeDePaiment Mode de paiement de la facture.
     */
    public Facture(Client client,
                   double totalFacture,
                   EnumModeDePaiement modeDePaiment) {
        this.client = client;
        this.totalFacture = totalFacture;
        this.modeDePaiment = modeDePaiment;
        this.statutFacture = EnumStatutFacture.ENCOURS;
    }

    /**
     * Constructeur pour initialiser une Facture avec id, client, totalFacture et modeDePaiment.
     * Le statutFacture est initialisé à EN_COURS par défaut.
     * @param id Id de la facture.
     * @param client Client associé à la facture.
     * @param totalFacture Montant total de la facture.
     * @param modeDePaiment Mode de paiement de la facture.
     */
    public Facture(int id, Client client,
                   double totalFacture,
                   EnumModeDePaiement modeDePaiment) {
        this.id = id;
        this.client = client;
        this.totalFacture = totalFacture;
        this.modeDePaiment = modeDePaiment;
        this.statutFacture = EnumStatutFacture.ENCOURS;
    }

    /**
     * Getter pour le statut de la facture.
     * @return Statut de la facture.
     */
    public EnumStatutFacture getStatutFacture() {
        return statutFacture;
    }

    /**
     * Setter pour le statut de la facture.
     * @param statutFacture Statut à définir pour la facture.
     */
    public void setStatutFacture(EnumStatutFacture statutFacture) {
        this.statutFacture = statutFacture;
    }

    /**
     * Getter pour le total de la facture.
     * @return Total de la facture.
     */
    public double getTotalFacture() {
        return totalFacture;
    }

    /**
     * Getter pour le mode de paiement de la facture.
     * @return Mode de paiement de la facture.
     */
    public EnumModeDePaiement getModeDePaiment() {
        return modeDePaiment;
    }

    /**
     * Setter pour le total de la facture.
     * @param totalFacture Total à définir pour la facture.
     */
    public void setTotalFacture(double totalFacture) {
        this.totalFacture = totalFacture;
    }

    /**
     * Setter pour le mode de paiement de la facture.
     * @param modeDePaiment Mode de paiement à définir pour la facture.
     */
    public void setModeDePaiment(EnumModeDePaiement modeDePaiment) {
        this.modeDePaiment = modeDePaiment;
    }

    /**
     * Getter pour le client associé à la facture.
     * @return Client associé à la facture.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Getter pour l'id de la facture.
     * @return Id de la facture.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pour l'id de la facture.
     * @param id Id à définir pour la facture.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode toString redéfinie pour retourner une représentation en chaîne de caractères de la facture.
     * @return Représentation en chaîne de caractères de la facture.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Facture{");
        sb.append("client=").append(client);
        sb.append(", totalFacture=").append(totalFacture);
        sb.append(", modeDePaiment=").append(modeDePaiment);
        sb.append(", statutFacture=").append(statutFacture);
        sb.append('}');
        return sb.toString();
    }
}