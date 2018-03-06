package com.medicacion.asistente.pojos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juanjo
 */
@XmlRootElement
public class Usuario {
    
    public int PK_ID;
    String nombre;
    String password;

    public Usuario() {
    }

    public Usuario(int PK_ID, String nombre, String password) {
        this.PK_ID = PK_ID;
        this.nombre = nombre;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
