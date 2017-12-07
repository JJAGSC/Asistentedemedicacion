package com.medicacion.asistente.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juanjo
 */
@XmlRootElement
public class Medicamento {
    
    public int PK_ID;
    String nombre;
    String formato;

    public Medicamento() {
    }

    public Medicamento(int PK_ID, String nombre, String formato) {
        this.PK_ID = PK_ID;
        this.nombre = nombre;
        this.formato = formato;
    }

    public int getPK_ID() {
        return PK_ID;
    }

    public void setPK_ID(int PK_ID) {
        this.PK_ID = PK_ID;
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
