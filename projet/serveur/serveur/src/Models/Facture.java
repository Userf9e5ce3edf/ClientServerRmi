package Models;

import java.rmi.Remote;

public class Facture implements Remote {
    private final Client client;
    private double totalFacture;
    private EnumModeDePaiment modeDePaiment;
    private EnumStatutFacture statutFacture;
    public Facture(Client client,
                   double totalFacture,
                   EnumModeDePaiment modeDePaiment) {
        this.client = client;
        this.totalFacture = totalFacture;
        this.modeDePaiment = modeDePaiment;
        this.statutFacture = EnumStatutFacture.ENCOURS;
    }

    public EnumStatutFacture getStatutFacture() {
        return statutFacture;
    }
    public void setStatutFacture(EnumStatutFacture statutFacture) {
        this.statutFacture = statutFacture;
    }
    public double getTotalFacture() {
        return totalFacture;
    }
    public EnumModeDePaiment getModeDePaiment() {
        return modeDePaiment;
    }
    public void setTotalFacture(double totalFacture) {
        this.totalFacture = totalFacture;
    }
    public void setModeDePaiment(EnumModeDePaiment modeDePaiment) {
        this.modeDePaiment = modeDePaiment;
    }
    public Client getClient() {
        return client;
    }
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
