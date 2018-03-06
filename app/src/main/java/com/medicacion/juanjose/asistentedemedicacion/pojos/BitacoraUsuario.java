package com.medicacion.juanjose.asistentedemedicacion.pojos;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;

/**
 * Created by Juanjo on 05/12/2017.
 */

public class BitacoraUsuario {
    int ID;
    int ID_Usuario;
    int operacion;

    public BitacoraUsuario() {
        this.ID = G.SIN_VALOR_INT;
        this.ID_Usuario = G.SIN_VALOR_INT;
        this.operacion = G.SIN_VALOR_INT;
    }

    public BitacoraUsuario(int ID, int ID_Usuario, int operacion) {
        this.ID = ID;
        this.ID_Usuario = ID_Usuario;
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
