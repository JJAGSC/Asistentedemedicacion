package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.constantes.Utilidades;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Bitacora;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Juanjo on 05/11/2017.
 */

public class MedicamentoProveedor {

    // No hemos desactivado el contexto para que funcionen las imagenes
    public static Uri insertRecord(ContentResolver resolver, Medicamento medicamento, Context contexto){
        Uri uri = Contrato.Medicamento.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Medicamento.NOMBRE, medicamento.getNombre());
        values.put(Contrato.Medicamento.FORMATO, medicamento.getFormato());

        Uri uriResultado = resolver.insert(uri, values);

        String medId = uriResultado.getLastPathSegment();

        // content://autoridad/Medicamento/4

        if (medicamento.getImagen()!=null){
            try {
                Utilidades.storeImage(medicamento.getImagen(), contexto, "img_" + medId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }

        return uriResultado;
    }

    // No hemos desactivado el contexto por ahora
    public static void insertRecordConBitacora(ContentResolver resolver, Medicamento medicamento, Context contexto){

        // Ojo, el contexto se ha dejado solo para las fotos
        Uri uri = insertRecord(resolver, medicamento, contexto);
        medicamento.setID(Integer.parseInt(uri.getLastPathSegment()));

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Medicamento(medicamento.getID());
        bitacora.setOperacion(G.OPERACION_INSERTAR);

        BitacoraProveedor.insertRecord(resolver, bitacora, contexto);
    }

    public static void deleteRecord(ContentResolver resolver, int medicamentoID){
        Uri uri = Uri.parse(Contrato.Medicamento.CONTENT_URI + "/" + medicamentoID);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecordConBitacora(ContentResolver resolver, int medicamentoID){

        deleteRecord(resolver, medicamentoID);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Medicamento(medicamentoID);
        bitacora.setOperacion(G.OPERACION_BORRAR);

        // Ojo, se ha puesto el contexto en null ya que delete no recibe contexto
        BitacoraProveedor.insertRecord(resolver, bitacora, null);
    }

    public static void updateRecord(ContentResolver resolver, Medicamento medicamento, Context contexto){
        Uri uri = Uri.parse(Contrato.Medicamento.CONTENT_URI + "/" + medicamento.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Medicamento.NOMBRE, medicamento.getNombre());
        values.put(Contrato.Medicamento.FORMATO, medicamento.getFormato());

        resolver.update (uri, values, null, null);

        if (medicamento.getImagen()!=null){
            try {
                Utilidades.storeImage(medicamento.getImagen(), contexto, "img_" + medicamento.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void updateRecordConBitacora(ContentResolver resolver, Medicamento medicamento, Context contexto){

        // Ojo, el contexto se ha dejado solo para las fotos
        updateRecord(resolver, medicamento, contexto);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Medicamento(medicamento.getID());
        bitacora.setOperacion(G.OPERACION_MODIFICAR);

        BitacoraProveedor.insertRecord(resolver, bitacora, contexto);
    }

    public static Medicamento readRecord(ContentResolver resolver, int medicamentoID){
        Uri uri = Uri.parse(Contrato.Medicamento.CONTENT_URI + "/" + medicamentoID);

        String[] projection = {
                Contrato.Medicamento.NOMBRE,
                Contrato.Medicamento.FORMATO
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        if (cursor.moveToFirst()){

            Medicamento medicamento = new Medicamento();
            medicamento.setID(medicamentoID);
            medicamento.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Medicamento.NOMBRE)));
            medicamento.setFormato(cursor.getString(cursor.getColumnIndex(Contrato.Medicamento.FORMATO)));

            return medicamento;
        }

        return null;
    }

    public static ArrayList<Medicamento> readAllRecord(ContentResolver resolver){
        Uri uri = Contrato.Medicamento.CONTENT_URI;

        String[] projection = {
                Contrato.Medicamento._ID,
                Contrato.Medicamento.NOMBRE,
                Contrato.Medicamento.FORMATO
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        Medicamento medicamento;

        while (cursor.moveToNext()){
            medicamento = new Medicamento();

            medicamento.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Medicamento._ID)));
            medicamento.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Medicamento.NOMBRE)));
            medicamento.setFormato(cursor.getString(cursor.getColumnIndex(Contrato.Medicamento.FORMATO)));

            medicamentos.add(medicamento);
        }

        return medicamentos;
    }
}
