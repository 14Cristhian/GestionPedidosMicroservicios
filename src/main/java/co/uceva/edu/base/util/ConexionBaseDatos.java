package co.uceva.edu.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    //private static String url = "jdbc:sqlite:C:\\Repositorios\\sqlite\\productos.db";
    private static String url = "jdbc:postgresql://localhost:5432/pedidos";
    private static String username = "pedidos";
    private static String password = "12345";

//
//    private static String url = "jdbc:postgresql://129.159.106.89:5432/pedidos3";
//    private static String username = "grupo3";
//    private static String password = "Widfly+2021";


    private ConexionBaseDatos(){

    }
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
}
