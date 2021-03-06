package com.medicacion.juanjose.asistentedemedicacion.pojos;

import android.graphics.Bitmap;

/**
 * Created by Juanjo on 04/11/2017.
 */

public class Medicamento {
    private int ID;
    String nombre;
    String hora = "00:00";
    private Bitmap imagen;
    String usuarioalarma;

    public Medicamento() {
    }

    public Medicamento(String nombre, String hora, String usuarioalarma) {
        this.nombre = nombre;
        this.hora = hora;
        this.usuarioalarma = usuarioalarma;
    }

    public Medicamento(int ID, String nombre, String hora, String usuarioalarma) {
        this.ID = ID;
        this.nombre = nombre;
        this.hora = hora;
        this.usuarioalarma = usuarioalarma;
    }

    public Medicamento(int ID, String nombre, String hora, Bitmap imagen, String usuarioalarma) {
        this.ID = ID;
        this.nombre = nombre;
        this.hora = hora;
        this.imagen = imagen;
        this.usuarioalarma = usuarioalarma;
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

    public String getUsuarioAlarma() {
        return usuarioalarma;
    }

    public void setUsuarioAlarma(String usuarioalarma) {
        this.usuarioalarma = usuarioalarma;
    }

    public int getHoraNum (){

        return Integer.parseInt(hora.substring(0,2));
    }

    public int getMinuteNum(){

        return Integer.parseInt(hora.substring(3,5));
    }
}
