package Models;

import Interfaces.IRequete;

import javax.management.relation.Relation;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Requete implements IRequete {
    public String VoirStock(String refComposant) {
        List<Composant> composants = new ArrayList<>();

        final String condition = "reference = '" + refComposant + "'";
        final String nomTable = "composants";
        
        // Connection a la base de donnees
        RelationBaseDeDonnees baseDeDonnees = new RelationBaseDeDonnees();
        composants = baseDeDonnees.SelectAllWhere(nomTable, condition);
        // Deconnection de la base de donnees
        baseDeDonnees.close();

        return composants.getFirst().toString();
    }

    @Override
    public List<String> RechercheComposant(String famille) throws RemoteException {
        /*
        	rechercher un composant : en donnant une famille de composant,
        	on doit pouvoir récupérer toutes les références des composants de cette famille.
        	Seules les références dont le stock n’est pas nul doivent être retournées ;
        */
        // select reference from Composant where famille = famille
        // List<String> references = new List<String>() // mettre le retour de la requete
        return null;
    }

    @Override
    public boolean acheterComposant(String refComposant, int quantite) throws RemoteException {
        /*
        	acheter un composant : un client doit pouvoir acheter un composant en stock.
        	S’il demande plus d’exemplaires qu’il n’y en a en stock,
        	la vente doit être complètement refusée ;
        */
        return false;
    }

    @Override
    public boolean ajouterComposant(String refComposant, int quantite) throws RemoteException {
        /*
	        ajouter un produit : on peut ajouter un certain nombre d’exemplaires
	        d’un produit dans le catalogue (la référence du produit doit déjà exister).
        */
        return false;
    }

    @Override
    public boolean payerFacture(String nomClient, double montant) throws RemoteException {
        /*
	        payer une facture : un client peut payer ce qu’il doit à la boutique;
         */
        return false;
    }

    @Override
    public String ConsulterFacture(String nomClient) throws RemoteException {
        /*
            consulter une facture : il doit être possible de voir la facture correspondant à un client;
        */

        return null;
    }
}
