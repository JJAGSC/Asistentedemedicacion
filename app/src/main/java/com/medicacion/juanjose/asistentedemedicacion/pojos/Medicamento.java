package com.medicacion.juanjose.asistentedemedicacion.pojos;

import android.graphics.Bitmap;

/**
 * Created by Juanjo on 04/11/2017.
 */

public class Medicamento {
    private int ID;
    String nombre;
    String hora;
    private Bitmap imagen;

    public Medicamento() {
    }

    public Medicamento(String nombre, String hora) {
        this.nombre = nombre;
        this.hora = hora;
    }

    public Medicamento(int ID, String nombre, String hora) {
        this.ID = ID;
        this.nombre = nombre;
        this.hora = hora;
    }

    public Medicamento(int ID, String nombre, String hora, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.hora = hora;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
