package Models;

import java.rmi.Remote;

public class Facture implements Remote {
    private final String nomClient;
    private final String adresseClient;
    private final double totalFacture;
    private final ENUMModeDePaiment modeDePaiment;

    public Facture(String nomClient,
                   String adresseClient,
                   double totalFacture,
                   ENUMModeDePaiment modeDePaiment) {
        this.nomClient = nomClient;
        this.adresseClient = adresseClient;
        this.totalFacture = totalFacture;
        this.modeDePaiment = modeDePaiment;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getAdresseClient() {
        return adresseClient;
    }

    public double getTotalFacture() {
        return totalFacture;
    }

    public ENUMModeDePaiment getModeDePaiment() {
        return modeDePaiment;
    }
}
