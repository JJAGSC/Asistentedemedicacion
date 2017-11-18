package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;

/**
 * Created by Juanjo on 05/11/2017.
 */

public class MedicamentoProveedor {
    public static void insertRecord(ContentResolver resolver, Medicamento medicamento){
        Uri uri = Contrato.Medicamento.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Medicamento.NOMBRE, medicamento.getNombre());
        values.put(Contrato.Medicamento.FORMATO, medicamento.getFormato());

        resolver.insert(uri, values);
    }

    public static void deleteRecord(ContentResolver resolver, int medicamentoID){
        Uri uri = Uri.parse(Contrato.Medicamento.CONTENT_URI + "/" + medicamentoID);
        resolver.delete(uri, null, null);
    }

    public static void updateRecord(ContentResolver resolver, Medicamento medicamento){
        Uri uri = Uri.parse(Contrato.Medicamento.CONTENT_URI + "/" + medicamento.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Medicamento.NOMBRE, medicamento.getNombre());
        values.put(Contrato.Medicamento.FORMATO, medicamento.getFormato());

        resolver.update (uri, values, null, null);
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
}
