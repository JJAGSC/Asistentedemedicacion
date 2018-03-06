package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContratoUsuario {

    public static final String AUTHORITY = "com.medicacion.juanjose.asistentedemedicacion.proveedor.ProveedorDeContenidoUsuario";

    public static final class Usuario implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Usuario"); // comprobar

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String PASSWORD = "Password";
    }

    public static final class BitacoraUsuario implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/BitacoraUsuario");

        // Table column
        public static final String ID_USUARIO = "ID_Usuario";
        public static final String OPERACION = "Operacion";
    }
}
