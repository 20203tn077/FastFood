package mx.edu.utez.fastfood.fastfood.service;

import java.sql.*;
import java.util.ResourceBundle;

public class MySqlConnection {
    private static String host;
    private static String port;
    private static String database;
    private static String useSSL;
    private static String timezone;
    private static String user;
    private static String password;

    private static ResourceBundle connectionProperties;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if (connectionProperties == null) {
            connectionProperties = ResourceBundle.getBundle("connection");
            host = connectionProperties.getString("host");
            port = connectionProperties.getString("port");
            database = connectionProperties.getString("database");
            useSSL = connectionProperties.getString("useSSL");
            timezone = connectionProperties.getString("timezone");
            user = connectionProperties.getString("user");
            password = connectionProperties.getString("password");
        }

        return DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?useSSL=%s&serverTimezone=%s", host, port, database, useSSL, timezone), user, password);
    }

    public static void closeConnection(Connection con, PreparedStatement pstm, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            System.out.println(MySqlConnection.getConnection() == null ? "Conexión fallida :(" : "Conexión exitosa :)");
        } catch (SQLException e) {
            System.out.println("Conexión fallida");
            e.printStackTrace();
        }
    }
}
