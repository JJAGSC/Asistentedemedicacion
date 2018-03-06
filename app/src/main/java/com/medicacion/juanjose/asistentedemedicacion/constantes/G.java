package com.medicacion.juanjose.asistentedemedicacion.constantes;

/**
 * Created by Juanjo on 05/11/2017.
 */

public class G {

    public static final String RUTA_SERVIDOR = "http://192.168.1.31:8080/Medicamentos/webresources";
    //public static final String RUTA_SERVIDOR = "http://81.61.76.185:8080/Medicamentos/webresources"; // no funciona

    public static final int SYNC_INTERVAL = 60; // Se puede poner mínimo 60 segundos
    public static final boolean VERSION_ADMINISTRADOR = true;
    public static final int SIN_VALOR_INT = -1;
    //public static final String SIN_VALOR_STRING = "";

    public static final int INSERTAR = 1;
    public static final int GUARDAR = 2;

    public static final int OPERACION_INSERTAR = 1;
    public static final int OPERACION_MODIFICAR = 2;
    public static final int OPERACION_BORRAR = 3;
}