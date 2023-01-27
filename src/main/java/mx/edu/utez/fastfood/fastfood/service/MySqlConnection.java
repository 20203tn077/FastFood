package mx.edu.utez.fastfood.fastfood.service;

import java.sql.Connection;
import java.util.ResourceBundle;

public class MySqlConnection {
    public static Connection getConnection() {
        ResourceBundle bundle = ResourceBundle.getBundle("/properties");
    }
}
