package com.medicacion.asistente.bd;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Juanjo
 */
public class ConexionBD {
    
    public static final String URL = "jdbc:mysql://localhost/medicamentos";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    
    public static Connection getConexion() throws Exception{
        
        Connection conexion = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (Exception ex) {
            throw(ex);
        }
        
        return conexion;
    }
}
