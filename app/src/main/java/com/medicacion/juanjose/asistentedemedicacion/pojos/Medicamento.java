package com.medicacion.juanjose.asistentedemedicacion.pojos;

/**
 * Created by Juanjo on 04/11/2017.
 */

public class Medicamento {
    private int ID;
    String nombre;
    String formato;

    public Medicamento() {
    }

    public Medicamento(String nombre, String formato) {
        this.nombre = nombre;
        this.formato = formato;
    }

    public Medicamento(int ID, String nombre, String formato) {
        this.ID = ID;
        this.nombre = nombre;
        this.formato = formato;
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
}
