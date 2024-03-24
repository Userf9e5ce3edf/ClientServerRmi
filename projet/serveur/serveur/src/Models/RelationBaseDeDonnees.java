package Models;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RelationBaseDeDonnees {
    // url vers la bdd
    private final String url = "jdbc:mysql://localhost:3306/Magasin";
    private final String user = "root";
    private final String password = "root";
    private Connection conn;

    // Initialisation de la variable de connection a la bdd + driver
    public RelationBaseDeDonnees() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.err.println("Erreur ClassNotFound sur le driver : " + e.getMessage());
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            System.err.println("Erreur SQL lors de la connection a la base : " + e.getMessage());
        }
    }

    // Methode pour créer les tables uniquement si elles n'existent pas deja
    public boolean createTables() {

        String queryComposants = """ 
            CREATE TABLE IF NOT EXISTS composants (
                reference VARCHAR(10) PRIMARY KEY,
                famille VARCHAR(50),
                prix_unitaire DECIMAL(10, 2),
                quantite_en_stock INT
            );
        """;

        String queryClients = """               
            CREATE TABLE IF NOT EXISTS clients (
                id SERIAL PRIMARY KEY,
                nom VARCHAR(100),
                adresse VARCHAR(100),
                total_facture DECIMAL(10, 2),
                mode_paiement VARCHAR(50)
            );
            """;

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(queryComposants);
            stmt.executeUpdate(queryClients);
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur SQl lors de la creation des tables : " + e.getMessage());
            return false;
        }
    }

    public List<Composant> SelectAllWhere(String nomTable, String condition) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<Composant> composants = new ArrayList<>();
        final String query = "SELECT * FROM " + nomTable + " WHERE " + condition;

        try {
            stmt = conn.prepareStatement(query);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String reference = resultSet.getString("reference");
                String famille = resultSet.getString("famille");
                float prix = resultSet.getFloat("prix_unitaire");
                int quantite = resultSet.getInt("quantite_en_stock");

                Composant composant = new Composant(
                        reference, famille , prix, quantite);
                composants.add(composant);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQl lors du Select * d'une table : " + e.getMessage());
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources GetLecteurBD : " + e.getMessage());
            }
        }

        return composants;
    }


    // Methode pour se deconnecter de la base de donnees
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de la fermeture de la connection a la base de donnee : " + e.getMessage());
        }
    }
}