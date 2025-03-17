package datasourceManagement;

import java.sql.*;

/**
 * Cette classe gère la connexion à la base de données MySQL.
 */
public class MySQLManager {
    private static MySQLManager instance = null;
    private final String url = "jdbc:mysql://localhost:3306/magasin";
    private final String user = "root";
    private final String password = "root";
    private Connection conn = null;

    /**
     * Constructeur privé pour initialiser la connexion à la base de données.
     */
    private MySQLManager() {
        connexion();
    }

    /**
     * Méthode pour obtenir une instance de MySQLManager (Singleton).
     * @return Une instance de MySQLManager.
     */
    public static synchronized MySQLManager getInstance() {
        if (instance == null) {
            instance = new MySQLManager();
        }
        return instance;
    }

    /**
     * Prépare une déclaration SQL.
     * @param query La requête SQL à préparer.
     * @return Une déclaration SQL préparée.
     * @throws SQLException Si une exception SQL se produit.
     */
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }

    /**
     * Établit la connexion à la base de données.
     */
    private void connexion()
    {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    /**
     * Récupère les données de la base de données en fonction de la requête SQL fournie.
     * @param req La requête SQL.
     * @return Un ResultSet contenant les données récupérées.
     */
    public ResultSet getData(String req)
    {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(req);
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    /**
     * Met à jour les données dans la base de données en fonction de la requête SQL fournie.
     * @param req La requête SQL.
     * @return Un entier représentant le nombre de lignes affectées par la mise à jour.
     */
    public int setData(String req)
    {
        int res=0;
        Statement stmt = null;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            res = stmt.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if( rs.next() ) {
                res = rs.getInt(1);
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }
                stmt = null;
            }
        }
        return res;
    }
}