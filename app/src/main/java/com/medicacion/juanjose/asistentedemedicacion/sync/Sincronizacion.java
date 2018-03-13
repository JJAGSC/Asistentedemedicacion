package com.medicacion.juanjose.asistentedemedicacion.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Bitacora;
import com.medicacion.juanjose.asistentedemedicacion.pojos.BitacoraUsuario;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Usuario;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.BitacoraProveedor;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.BitacoraProveedorUsuario;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.UsuarioProveedor;
import com.medicacion.juanjose.asistentedemedicacion.volley.MedicamentoVolley;
import com.medicacion.juanjose.asistentedemedicacion.volley.UsuarioVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Juanjo on 06/12/2017.
 */
public class Sincronizacion {
    private static final String LOGTAG = "Juanjo - Sincronizacion";
    private static ContentResolver resolvedor;
    private static Context contexto;
    private static boolean esperandoRespuestaDeServidor = false;

    public Sincronizacion(Context contexto){
        this.resolvedor = contexto.getContentResolver();
        this.contexto = contexto;

        //La primera vez se cargan los datos siempre
        //recibirActualizacionesDelServidor();
        //recibirActualizacionesDelServidorUsuario();
    }

    public synchronized static boolean isEsperandoRespuestaDeServidor() {
        return esperandoRespuestaDeServidor;
    }

    public synchronized static void setEsperandoRespuestaDeServidor(boolean esperandoRespuestaDeServidor) {
        Sincronizacion.esperandoRespuestaDeServidor = esperandoRespuestaDeServidor;
    }

    public synchronized boolean sincronizar(){
        Log.i("sincronizacion","SINCRONIZAR");

        if(isEsperandoRespuestaDeServidor()){
            return true;
        }


        if(G.VERSION_ADMINISTRADOR){
            enviarActualizacionesAlServidor();
            enviarActualizacionesAlServidorUsuario();
            recibirActualizacionesDelServidor();
            recibirActualizacionesDelServidorUsuario();
        } else {
            recibirActualizacionesDelServidor();
            recibirActualizacionesDelServidorUsuario();
        }

        return true;
    }

    private static void enviarActualizacionesAlServidor(){
        ArrayList<Bitacora> registrosBitacora = BitacoraProveedor.readAllRecord(resolvedor);
        for(Bitacora bitacora : registrosBitacora){

            switch(bitacora.getOperacion()){
                case G.OPERACION_INSERTAR:
                    Medicamento medicamento = null;
                    try {
                        medicamento = MedicamentoProveedor.readRecord(resolvedor, bitacora.getID_Medicamento());
                        MedicamentoVolley.addMedicamento(medicamento, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_MODIFICAR:
                    try {
                        medicamento = MedicamentoProveedor.readRecord(resolvedor, bitacora.getID_Medicamento());
                        MedicamentoVolley.updateMedicamento(medicamento, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_BORRAR:
                    MedicamentoVolley.delMedicamento(bitacora.getID_Medicamento(), true, bitacora.getID());
                    break;
            }
            Log.i("sincronizacion", "acabo de enviar");
        }
    }

    public static void enviarActualizacionesAlServidorUsuario() {
        ArrayList<BitacoraUsuario> registrosBitacoraProveedor = BitacoraProveedorUsuario.readAllRecord(resolvedor);
        for (BitacoraUsuario bitacoraUsuario : registrosBitacoraProveedor) {

            switch (bitacoraUsuario.getOperacion()) {
                case G.OPERACION_INSERTAR:
                    Usuario usuario = null;
                    try {
                        usuario = UsuarioProveedor.readRecord(resolvedor, bitacoraUsuario.getID_Usuario());
                        UsuarioVolley.addUsuario(usuario, true, bitacoraUsuario.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_MODIFICAR:
                    try {
                        usuario = UsuarioProveedor.readRecord(resolvedor, bitacoraUsuario.getID_Usuario());
                        UsuarioVolley.updateUsuario(usuario, true, bitacoraUsuario.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_BORRAR:
                    UsuarioVolley.delUsuario(bitacoraUsuario.getID_Usuario(), true, bitacoraUsuario.getID());
                    break;
            }
            Log.i("sincronizacion", "acabo de enviar");
        }
    }

    // Medicamentos
    private static void recibirActualizacionesDelServidor(){
        MedicamentoVolley.getAllMedicamento();
    }

    // Usuarios
    public static void recibirActualizacionesDelServidorUsuario(){
        UsuarioVolley.getAllUsuario();
    }

    // Medicamentos
    public static void realizarActualizacionesDelServidorUnaVezRecibidas(JSONArray jsonArray){
        Log.i("sincronizacion", "recibirActualizacionesDelServidor");

        try {
            ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
            ArrayList<Medicamento> registrosNuevos = new ArrayList<>();
            ArrayList<Medicamento> registrosViejos = MedicamentoProveedor.readAllRecord(resolvedor);
            ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
            for(Medicamento i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++ ){
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Medicamento(obj.getInt("PK_ID"), obj.getString("nombre"), obj.getString("hora"), obj.getString("usuarioalarma")));
            }

            for(Medicamento medicamento: registrosNuevos) {
                try {
                    if(identificadoresDeRegistrosViejos.contains(medicamento.getID())) {
                        MedicamentoProveedor.updateRecord(resolvedor, medicamento, null);
                    } else {
                        MedicamentoProveedor.insertRecord(resolvedor, medicamento, null);
                    }
                    identificadoresDeRegistrosActualizados.add(medicamento.getID());
                } catch (Exception e){
                    Log.i("sincronizacion",
                            "Probablemente el registro ya existía en la BD."+"" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
            }

            for(Medicamento medicamento: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(medicamento.getID())){
                    try {
                        MedicamentoProveedor.deleteRecord(resolvedor, medicamento.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + medicamento.getID());
                    }
                }
            }

            //MedicamentoVolley.getAllMedicamento(); //Los baja y los guarda en SQLite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Usuarios
    public static void realizarActualizacionesDelServidorUnaVezRecibidasUsuario(JSONArray jsonArray){
        Log.i("sincronizacion", "recibirActualizacionesDelServidor");

        try {
            ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
            ArrayList<Usuario> registrosNuevos = new ArrayList<>();
            ArrayList<Usuario> registrosViejos = UsuarioProveedor.readAllRecord(resolvedor);
            ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
            for(Usuario i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++ ){
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Usuario(obj.getInt("PK_ID"), obj.getString("nombre"), obj.getString("password")));
            }

            for(Usuario usuario: registrosNuevos) {
                try {
                    if(identificadoresDeRegistrosViejos.contains(usuario.getID())) {
                        UsuarioProveedor.updateRecord(resolvedor, usuario, null);
                    } else {
                        UsuarioProveedor.insertRecord(resolvedor, usuario, null);
                    }
                    identificadoresDeRegistrosActualizados.add(usuario.getID());
                } catch (Exception e){
                    Log.i("sincronizacion",
                            "Probablemente el registro ya existía en la BD."+"" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
            }

            for(Usuario usuario: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(usuario.getID())){
                    try {
                        UsuarioProveedor.deleteRecord(resolvedor, usuario.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + usuario.getID());
                    }
                }
            }

            //UsuarioVolley.getAllUsuario(); //Los baja y los guarda en SQLite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
