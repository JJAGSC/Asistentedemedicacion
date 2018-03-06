package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.constantes.Utilidades;
import com.medicacion.juanjose.asistentedemedicacion.pojos.BitacoraUsuario;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Usuario;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Juanjo on 05/11/2017.
 */

public class UsuarioProveedor {

    // No hemos desactivado el contexto para que funcionen las imagenes
    public static Uri insertRecord(ContentResolver resolver, Usuario usuario, Context contexto){
        Uri uri = ContratoUsuario.Usuario.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(ContratoUsuario.Usuario.NOMBRE, usuario.getNombre());
        values.put(ContratoUsuario.Usuario.PASSWORD, usuario.getPassword());

        Uri uriResultado = resolver.insert(uri, values);

        String medId = uriResultado.getLastPathSegment();

        // content://autoridad/Usuario/4

        if (usuario.getImagen()!=null){
            try {
                Utilidades.storeImage(usuario.getImagen(), contexto, "img_" + medId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }

        return uriResultado;
    }

    // No hemos desactivado el contexto por ahora
    public static void insertRecordConBitacora(ContentResolver resolver, Usuario usuario, Context contexto){

        // Ojo, el contexto se ha dejado solo para las fotos
        Uri uri = insertRecord(resolver, usuario, contexto);
        usuario.setID(Integer.parseInt(uri.getLastPathSegment()));

        BitacoraUsuario bitacoraUsuario = new BitacoraUsuario();
        bitacoraUsuario.setID_Usuario(usuario.getID());
        bitacoraUsuario.setOperacion(G.OPERACION_INSERTAR);

        BitacoraProveedorUsuario.insertRecord(resolver, bitacoraUsuario, contexto);
    }

    public static void deleteRecord(ContentResolver resolver, int usuarioID){
        Uri uri = Uri.parse(ContratoUsuario.Usuario.CONTENT_URI + "/" + usuarioID);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecordConBitacoraUsuario(ContentResolver resolver, int usuarioID){

        deleteRecord(resolver, usuarioID);

        BitacoraUsuario bitacoraUsuario = new BitacoraUsuario();
        bitacoraUsuario.setID_Usuario(usuarioID);
        bitacoraUsuario.setOperacion(G.OPERACION_BORRAR);

        // Ojo, se ha puesto el contexto en null ya que delete no recibe contexto
        BitacoraProveedorUsuario.insertRecord(resolver, bitacoraUsuario, null);
    }

    public static void updateRecord(ContentResolver resolver, Usuario usuario, Context contexto){
        Uri uri = Uri.parse(ContratoUsuario.Usuario.CONTENT_URI + "/" + usuario.getID());

        ContentValues values = new ContentValues();
        values.put(ContratoUsuario.Usuario.NOMBRE, usuario.getNombre());
        values.put(ContratoUsuario.Usuario.PASSWORD, usuario.getPassword());

        resolver.update (uri, values, null, null);

        if (usuario.getImagen()!=null){
            try {
                Utilidades.storeImage(usuario.getImagen(), contexto, "img_" + usuario.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Ha ocurrido un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void updateRecordConBitacoraUsuario(ContentResolver resolver, Usuario usuario, Context contexto){

        // Ojo, el contexto se ha dejado solo para las fotos
        updateRecord(resolver, usuario, contexto);

        BitacoraUsuario bitacoraUsuario = new BitacoraUsuario();
        bitacoraUsuario.setID_Usuario(usuario.getID());
        bitacoraUsuario.setOperacion(G.OPERACION_MODIFICAR);

        BitacoraProveedorUsuario.insertRecord(resolver, bitacoraUsuario, contexto);
    }

    public static Usuario readRecord(ContentResolver resolver, int usuarioID){
        Uri uri = Uri.parse(ContratoUsuario.Usuario.CONTENT_URI + "/" + usuarioID);

        String[] projection = {
                ContratoUsuario.Usuario.NOMBRE,
                ContratoUsuario.Usuario.PASSWORD
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        if (cursor.moveToFirst()){

            Usuario usuario = new Usuario();
            usuario.setID(usuarioID);
            usuario.setNombre(cursor.getString(cursor.getColumnIndex(ContratoUsuario.Usuario.NOMBRE)));
            usuario.setPassword(cursor.getString(cursor.getColumnIndex(ContratoUsuario.Usuario.PASSWORD)));

            return usuario;
        }

        return null;
    }

    public static ArrayList<Usuario> readAllRecord(ContentResolver resolver){
        Uri uri = ContratoUsuario.Usuario.CONTENT_URI;

        String[] projection = {
                ContratoUsuario.Usuario._ID,
                ContratoUsuario.Usuario.NOMBRE,
                ContratoUsuario.Usuario.PASSWORD
        };

        Cursor cursor = resolver.query (uri, projection, null, null, null);

        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario usuario;

        while (cursor.moveToNext()){
            usuario = new Usuario();

            usuario.setID(cursor.getInt(cursor.getColumnIndex(ContratoUsuario.Usuario._ID)));
            usuario.setNombre(cursor.getString(cursor.getColumnIndex(ContratoUsuario.Usuario.NOMBRE)));
            usuario.setPassword(cursor.getString(cursor.getColumnIndex(ContratoUsuario.Usuario.PASSWORD)));

            usuarios.add(usuario);
        }

        return usuarios;
    }
}
