package com.medicacion.juanjose.asistentedemedicacion.pojos;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;

/**
 * Created by Juanjo on 05/12/2017.
 */

public class Bitacora {
    int ID;
    int ID_Medicamento;
    int operacion;

    public Bitacora() {
        this.ID = G.SIN_VALOR_INT;
        this.ID_Medicamento = G.SIN_VALOR_INT;
        this.operacion = G.SIN_VALOR_INT;
    }

    public Bitacora(int ID, int ID_Medicamento, int operacion) {
        this.ID = ID;
        this.ID_Medicamento = ID_Medicamento;
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Medicamento() {
        return ID_Medicamento;
    }

    public void setID_Medicamento(int ID_Medicamento) {
        this.ID_Medicamento = ID_Medicamento;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
