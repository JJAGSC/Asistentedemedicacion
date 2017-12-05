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
import com.medicacion.juanjose.asistentedemedicacion.pojos.Bitacora;

import java.io.IOException;

/**
 * Created by Juanjo on 05/11/2017.
 */

public class BitacoraProveedor {

    // Desactivada la imagen en el bitácora, revisar en el futuro
    public static void insertRecord(ContentResolver resolver, Bitacora bitacora, Context contexto){
        Uri uri = Contrato.Bitacora.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_MEDICAMENTO, bitacora.getID_Medicamento());
        values.put(Contrato.Bitacora.OPERACION, bitacora.getOperacion());

        Uri uriResultado = resolver.insert(uri, values);

        /**
        String medId = uriResultado.getLastPathSegment();

        if (bitacora.getImagen()!=null){
            try {
                Utilidades.storeImage(bitacora.getImagen(), contexto, "img_" + medId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }

    public static void deleteRecord(ContentResolver resolver, int bitacoraID){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacoraID);

        resolver.delete(uri, null, null);
    }

    // Desactivada la imagen en el bitácora, revisar en el futuro
    public static void updateRecord(ContentResolver resolver, Bitacora bitacora, Context contexto){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_MEDICAMENTO, bitacora.getID_Medicamento());
        values.put(Contrato.Bitacora.OPERACION, bitacora.getOperacion());

        resolver.update (uri, values, null, null);

        /**
        if (bitacora.getImagen()!=null){
            try {
                Utilidades.storeImage(bitacora.getImagen(), contexto, "img_" + bitacora.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }

    public static Bitacora readRecord(ContentResolver resolver, int bitacoraID){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacoraID);

        String[] projection = {
                Contrato.Bitacora.ID_MEDICAMENTO,
                Contrato.Bitacora.OPERACION
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        if (cursor.moveToFirst()){

            Bitacora bitacora = new Bitacora();
            bitacora.setID(bitacoraID);
            bitacora.setID_Medicamento(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_MEDICAMENTO)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));

            return bitacora;
        }

        return null;
    }
}