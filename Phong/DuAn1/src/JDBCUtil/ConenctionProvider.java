//package JDBCUtil;
//
//import java.sql.*;
//
//public class ConenctionProvider {
//    // Connection
//    private static Connection conn;
//    private static String DB_SERVERNAME = "localhost";
//    private static String DB_DATABASENAME = "DuAn1";
//    private static String DB_USERNAME = "sa";
//    private static String DB_PASSWORD = "12345678";
//   
//    public static Connection getConnection () {
//        if (conn!=null) {
//            return conn;
//        } else {
//             String URL = String.format("jdbc:sqlserver://%s;databaseName=%s;trustServerCertificate=true;", DB_SERVERNAME,DB_DATABASENAME);
//        try {
//            conn = DriverManager.getConnection(URL,DB_USERNAME,DB_PASSWORD);
//            System.out.println(conn);
//            return conn;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        }
//    }
//    public static void main(String[] args) {
//        getConnection();
//    }
//}
///*

package JDBCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hangnt
 */
public class ConenctionProvider {

    public static final String HOSTNAME = "localhost";
    public static final String PORT = "1433";
    public static final String DBNAME = "DuAn1";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "12345678";

    /**
     * Get connection to MSSQL Server
     *
     * @return Connection
     */
    public static Connection getConnection() {

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";"
                + "databaseName=" + DBNAME + ";encrypt=true;trustServerCertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl, USERNAME, PASSWORD);
        } // Handle any errors that may have occurred.
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
