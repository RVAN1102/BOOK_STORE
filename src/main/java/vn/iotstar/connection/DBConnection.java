package vn.iotstar.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String serverName = "localhost";
    private final String dbName = "BookStoreDB";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String passwordPrimary = "Ngobavan@11021997";
    private final String passwordBackup = "123456";

    public Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber 
                   + ";databaseName=" + dbName + ";encrypt=false;trustServerCertificate=true;";
        try {
            return DriverManager.getConnection(url, userID, passwordPrimary);
        } catch (SQLException e) {
            try {
                return DriverManager.getConnection(url, userID, passwordBackup);
            } catch (SQLException ex) {
                throw e; 
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("--- ĐANG KIỂM TRA KẾT NỐI CƠ SỞ DỮ LIỆU BookStoreDB ---");
            Connection conn = new DBConnection().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("==> KẾT NỐI THÀNH CÔNG TỚI SQL SERVER: " + conn.getCatalog());
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("==> KẾT NỐI THẤT BẠI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
