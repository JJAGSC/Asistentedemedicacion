package com.medicacion.juanjose.asistentedemedicacion.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Bitacora;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.BitacoraProveedor;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;
import com.medicacion.juanjose.asistentedemedicacion.volley.MedicamentoVolley;

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
        recibirActualizacionesDelServidor(); //La primera vez se cargan los datos siempre
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
            recibirActualizacionesDelServidor();
        } else {
            recibirActualizacionesDelServidor();
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

    private static void recibirActualizacionesDelServidor(){
        MedicamentoVolley.getAllMedicamento();
    }

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
                registrosNuevos.add(new Medicamento(obj.getInt("PK_ID"), obj.getString("nombre"), obj.getString("hora")));
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
}
