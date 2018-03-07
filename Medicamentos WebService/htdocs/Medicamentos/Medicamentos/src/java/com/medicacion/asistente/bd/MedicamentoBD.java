package com.medicacion.asistente.bd;

import com.medicacion.asistente.pojos.Medicamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Juanjo
 */
public class MedicamentoBD {
    public static ArrayList<Medicamento> getAllMedicamentos() throws Exception{
        
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            conexion = ConexionBD.getConexion();
            ArrayList<Medicamento> medicamentos = new ArrayList<>(); 
            Medicamento medicamento;
            
            ps = conexion.prepareStatement("SELECT * FROM medicamento;");
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                medicamento = new Medicamento(rs.getInt("PK_ID"), rs.getString("nombre"), rs.getString("hora"));
                
                medicamentos.add(medicamento);  
            }
            
            return medicamentos;
            
        } catch (Exception ex) {
            throw(ex);
        } finally {
            if (ps!=null) ps.close();
            if (rs!=null) rs.close();
            if (conexion!=null) conexion.close();
        }
    }
    
    static public Medicamento getMedicamento(int id) throws Exception {
       
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            ps = conexion.prepareStatement("SELECT * FROM medicamento WHERE PK_ID = " + id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Medicamento registro = new Medicamento();

                registro.setPK_ID(rs.getInt("PK_ID"));
                registro.setNombre(rs.getString("nombre"));
                registro.setHora(rs.getString("hora"));

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

    static public void addMedicamento(Medicamento registro) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            
            String str;
            
            if (registro.getPK_ID() != -1) {
                str = "INSERT INTO medicamento (PK_ID, nombre, hora) VALUES ("
                        + registro.getPK_ID() + ",'" + registro.getNombre() + "','" + registro.getHora()+ "')";
            } else {
                str = "INSERT INTO medicamento (nombre, hora) VALUES ("
                        + "'" + registro.getNombre() + "','" + registro.getHora()+ "')";
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

    static public void delMedicamento(int id) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            ps = conexion.prepareStatement("DELETE FROM medicamento WHERE PK_ID = " + id + "");

            ps.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public void updateMedicamento(Medicamento registro) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexion = ConexionBD.getConexion();
            
            String str = "UPDATE medicamento SET nombre = '" + registro.getNombre() + "'"
                    + ", hora = '" + registro.getHora()+ "'"
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