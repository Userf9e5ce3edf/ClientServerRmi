package datasourceManagement;

import java.sql.*;

public class MySQLManager {
    private static MySQLManager instance = null;
    private final String url = "jdbc:mysql://localhost:3306/magasin";
    private final String user = "root";
    private final String password = "root";
    private Connection conn = null;

    private MySQLManager() {
        connexion();
    }

    public static synchronized MySQLManager getInstance() {
        if (instance == null) {
            instance = new MySQLManager();
        }
        return instance;
    }
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }
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
