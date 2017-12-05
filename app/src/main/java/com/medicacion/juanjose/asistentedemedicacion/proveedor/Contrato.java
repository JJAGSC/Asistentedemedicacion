package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contrato {

    public static final String AUTHORITY = "com.medicacion.juanjose.asistentedemedicacion.proveedor.ProveedorDeContenido";

    public static final class Medicamento implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Medicamento");

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String FORMATO = "Formato";
    }

    public static final class Bitacora implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Bitacora");

        // Table column
        public static final String ID_MEDICAMENTO = "ID_Medicamento";
        public static final String OPERACION = "Operacion";
    }
}
