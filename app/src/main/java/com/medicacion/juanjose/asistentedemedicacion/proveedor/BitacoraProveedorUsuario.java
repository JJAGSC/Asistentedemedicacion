package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.medicacion.juanjose.asistentedemedicacion.pojos.BitacoraUsuario;

import java.util.ArrayList;

/**
 * Created by Juanjo on 05/11/2017.
 */

public class BitacoraProveedorUsuario {

    // Desactivada la imagen en el bitácora, revisar en el futuro
    public static void insertRecord(ContentResolver resolver, BitacoraUsuario bitacoraUsuario, Context contexto){
        Uri uri = ContratoUsuario.BitacoraUsuario.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(ContratoUsuario.BitacoraUsuario.ID_USUARIO, bitacoraUsuario.getID_Usuario());
        values.put(ContratoUsuario.BitacoraUsuario.OPERACION, bitacoraUsuario.getOperacion());

        Uri uriResultado = resolver.insert(uri, values);

        /**
        String medId = uriResultado.getLastPathSegment();

        if (BitacoraUsuarioUsuario.getImagen()!=null){
            try {
                Utilidades.storeImage(bitacoraUsuario.getImagen(), contexto, "img_" + medId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }

    public static void deleteRecord(ContentResolver resolver, int bitacoraUsuarioID){
        Uri uri = Uri.parse(ContratoUsuario.BitacoraUsuario.CONTENT_URI + "/" + bitacoraUsuarioID);

        resolver.delete(uri, null, null);
    }

    // Desactivada la imagen en el bitácora, revisar en el futuro
    public static void updateRecord(ContentResolver resolver, BitacoraUsuario bitacoraUsuario, Context contexto){
        Uri uri = Uri.parse(ContratoUsuario.BitacoraUsuario.CONTENT_URI + "/" + bitacoraUsuario.getID());

        ContentValues values = new ContentValues();
        values.put(ContratoUsuario.BitacoraUsuario.ID_USUARIO, bitacoraUsuario.getID_Usuario());
        values.put(ContratoUsuario.BitacoraUsuario.OPERACION, bitacoraUsuario.getOperacion());

        resolver.update (uri, values, null, null);

        /**
        if (bitacoraUsuario.getImagen()!=null){
            try {
                Utilidades.storeImage(bitacoraUsuario.getImagen(), contexto, "img_" + bitacoraUsuario.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }

    public static BitacoraUsuario readRecord(ContentResolver resolver, int bitacoraUsuarioID){
        Uri uri = Uri.parse(ContratoUsuario.BitacoraUsuario.CONTENT_URI + "/" + bitacoraUsuarioID);

        String[] projection = {
                ContratoUsuario.BitacoraUsuario.ID_USUARIO,
                ContratoUsuario.BitacoraUsuario.OPERACION
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        if (cursor.moveToFirst()){

            BitacoraUsuario bitacoraUsuario = new BitacoraUsuario();
            bitacoraUsuario.setID(bitacoraUsuarioID);
            bitacoraUsuario.setID_Usuario(cursor.getInt(cursor.getColumnIndex(ContratoUsuario.BitacoraUsuario.ID_USUARIO)));
            bitacoraUsuario.setOperacion(cursor.getInt(cursor.getColumnIndex(ContratoUsuario.BitacoraUsuario.OPERACION)));

            return bitacoraUsuario;
        }

        return null;
    }

    public static ArrayList<BitacoraUsuario> readAllRecord(ContentResolver resolver){
        Uri uri = ContratoUsuario.BitacoraUsuario.CONTENT_URI;

        String[] projection = {
                ContratoUsuario.BitacoraUsuario._ID,
                ContratoUsuario.BitacoraUsuario.ID_USUARIO,
                ContratoUsuario.BitacoraUsuario.OPERACION
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        ArrayList<BitacoraUsuario> bitacoraUsuarios = new ArrayList<>();
        BitacoraUsuario bitacoraUsuario;

        while (cursor.moveToNext()){
            bitacoraUsuario = new BitacoraUsuario();

            bitacoraUsuario.setID(cursor.getInt(cursor.getColumnIndex(ContratoUsuario.BitacoraUsuario._ID)));
            bitacoraUsuario.setID_Usuario(cursor.getInt(cursor.getColumnIndex(ContratoUsuario.BitacoraUsuario.ID_USUARIO)));
            bitacoraUsuario.setOperacion(cursor.getInt(cursor.getColumnIndex(ContratoUsuario.BitacoraUsuario.OPERACION)));

            bitacoraUsuarios.add(bitacoraUsuario);
        }

        cursor.close();
        return bitacoraUsuarios;
    }
}