package com.medicacion.asistente.bd;

import com.medicacion.asistente.pojos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Juanjo
 */
public class UsuarioBD {
    public static ArrayList<Usuario> getAllUsuarios() throws Exception{
        
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            conexion = ConexionBD.getConexion();
            ArrayList<Usuario> usuarios = new ArrayList<>(); 
            Usuario usuario;
            
            ps = conexion.prepareStatement("SELECT * FROM usuario;");
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                usuario = new Usuario(rs.getInt("PK_ID"), rs.getString("nombre"), rs.getString("password"));
                
                usuarios.add(usuario);  
            }
            
            return usuarios;
            
        } catch (Exception ex) {
            throw(ex);
        } finally {
            if (ps!=null) ps.close();
            if (rs!=null) rs.close();
            if (conexion!=null) conexion.close();
        }
    }
    
    static public Usuario getUsuario(int id) throws Exception {
       
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM usuario WHERE PK_ID = " + id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuario registro = new Usuario();

                registro.setPK_ID(rs.getInt("PK_ID"));
                registro.setNombre(rs.getString("nombre"));
                registro.setPassword(rs.getString("password"));

                return registro;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
        return null;
    }

    static public void addUsuario(Usuario registro) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            
            String str;
            
            if (registro.getPK_ID() != -1) {
                str = "INSERT INTO usuario (PK_ID, nombre, password) VALUES ("
                        + registro.getPK_ID() + ",'" + registro.getNombre() + "','" + registro.getPassword()+ "')";
            } else {
                str = "INSERT INTO usuario (nombre, password) VALUES ("
                        + "'" + registro.getNombre() + "','" + registro.getPassword()+ "')";
            }

            ps = conexion.prepareStatement(str);
            ps.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public void delUsuario(int id) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            ps = conexion.prepareStatement("DELETE FROM usuario WHERE PK_ID = " + id + "");

            ps.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public void updateUsuario(Usuario registro) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            
            String str = "UPDATE usuario SET nombre = '" + registro.getNombre() + "'"
                    + ", password = '" + registro.getPassword()+ "'"
                    + " WHERE PK_ID = " + registro.getPK_ID();
            ps = conexion.prepareStatement(str);

            ps.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }
}