package com.medicacion.juanjose.asistentedemedicacion.pojos;

import android.graphics.Bitmap;

/**
 * Created by Juanjo on 04/11/2017.
 */

public class Medicamento {
    private int ID;
    String nombre;
    String formato;
    private Bitmap imagen;

    public Medicamento() {
    }

    public Medicamento(String nombre, String formato) {
        this.nombre = nombre;
        this.formato = formato;
    }

    public Medicamento(int ID, String nombre, String formato, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.formato = formato;
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

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
