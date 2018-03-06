package com.medicacion.juanjose.asistentedemedicacion.pojos;

import android.graphics.Bitmap;

/**
 * Created by Juanjo on 04/11/2017.
 */

public class Usuario {
    private int ID;
    String nombre;
    String password = "";
    private Bitmap imagen;

    public Usuario() {
    }

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario(int ID, String nombre, String password) {
        this.ID = ID;
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario(int ID, String nombre, String password, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.password = password;
        this.imagen = imagen;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
