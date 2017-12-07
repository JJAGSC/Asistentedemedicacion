package com.medicacion.juanjose.asistentedemedicacion.volley;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicacion.juanjose.asistentedemedicacion.aplicacion.AppController;
import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.BitacoraProveedor;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;
import com.medicacion.juanjose.asistentedemedicacion.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juanjo on 06/12/2017.
 */

public class MedicamentoVolley {
    final static String ruta = G.RUTA_SERVIDOR + "/medicamentos";
    //ContentResolver resolvedor;
    //RequestQueue queue;
    //Context context;

    //public MedicamentoVolley(ContentResolver resolvedor, Context context) {
    //    this.resolvedor = resolvedor;
    //    //this.queue = Volley.newRequestQueue(context);
    //}

    public static void getAllMedicamento(){
        String tag_json_obj = "getAllMedicamento"; //En realidad debería ser un identificador único para luego poder cancelar la petición.
        String url = ruta;
        // prepare the Request

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        //Log.d("Response", response.toString());
                        Sincronizacion.realizarActualizacionesDelServidorUnaVezRecibidas(response);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        // add it to the RequestQueue
        //queue.add(getRequest);
        AppController.getInstance().addToRequestQueue(getRequest, tag_json_obj);
    }

    public static void addMedicamento(Medicamento medicamento, final boolean conBitacora, final int idBitacora){
        String tag_json_obj = "addMedicamento"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PK_ID", medicamento.getID());
            jsonObject.put("nombre", medicamento.getNombre());
            jsonObject.put("formato", medicamento.getFormato());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.deleteRecord(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateMedicamento(Medicamento medicamento, final boolean conBitacora, final int idBitacora){
        String tag_json_obj = "updateMedicamento"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta + "/" + medicamento.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PK_ID", medicamento.getID());
            jsonObject.put("nombre", medicamento.getNombre());
            jsonObject.put("formato", medicamento.getFormato());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.deleteRecord(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delMedicamento(int id, final boolean conBitacora, final int idBitacora){
        String tag_json_obj = "updateMedicamento"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.deleteRecord(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }
}
