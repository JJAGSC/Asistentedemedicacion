package com.medicacion.juanjose.asistentedemedicacion.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

public class ProveedorDeContenidoUsuario extends ContentProvider {

    // content://com.medicacion.juanjose.asistentedemedicacion.proveedor.ProveedorDeContenidoUsuario/Usuario/#
    private static final int USUARIO_ONE_REG = 1;

    // content://com.medicacion.juanjose.asistentedemedicacion.proveedor.ProveedorDeContenidoUsuario/Usuario
    private static final int USUARIO_ALL_REGS = 2;

    private static final int BITACORAUSUARIO_ONE_REG = 3;
    private static final int BITACORAUSUARIO_ALL_REGS = 4;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "Usuarios.db";
    private static final int DATABASE_VERSION = 13;

    private static final String USUARIO_TABLE_NAME = "Usuario";
    private static final String BITACORAUSUARIO_TABLE_NAME = "BitacoraUsuario";

    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;

    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                ContratoUsuario.AUTHORITY,
                USUARIO_TABLE_NAME,
                USUARIO_ALL_REGS);
        sUriMatcher.addURI(
                ContratoUsuario.AUTHORITY,
                USUARIO_TABLE_NAME + "/#",
                USUARIO_ONE_REG);

        sUriMatcher.addURI(
                ContratoUsuario.AUTHORITY,
                BITACORAUSUARIO_TABLE_NAME,
                BITACORAUSUARIO_ALL_REGS);
        sUriMatcher.addURI(
                ContratoUsuario.AUTHORITY,
                BITACORAUSUARIO_TABLE_NAME + "/#",
                BITACORAUSUARIO_ONE_REG);

        // Specifies a custom MIME type for the picture URL table

        sMimeTypes.put(
                USUARIO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        ContratoUsuario.AUTHORITY + "." + USUARIO_TABLE_NAME);
        sMimeTypes.put(
                USUARIO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        ContratoUsuario.AUTHORITY + "." + USUARIO_TABLE_NAME);

        sMimeTypes.put(
                BITACORAUSUARIO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        ContratoUsuario.AUTHORITY + "." + BITACORAUSUARIO_TABLE_NAME);
        sMimeTypes.put(
                BITACORAUSUARIO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        ContratoUsuario.AUTHORITY + "." + BITACORAUSUARIO_TABLE_NAME);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            //if (!db.isReadOnly()){
            //Habilitamos la integridad referencial
            //db.execSQL("PRAGMA foreign_keys=ON;");
            //}
        }

        // Añadimos esto para resolver el problema de la integridad referencial ya que a partir de la API 16 el método ha cambiado
        @Override
        public void onConfigure(SQLiteDatabase db){
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                            + USUARIO_TABLE_NAME
                            + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                            + ContratoUsuario.Usuario.NOMBRE + " TEXT , "
                            + ContratoUsuario.Usuario.PASSWORD + " TEXT ); "
            );

            db.execSQL("Create table "
                    + BITACORAUSUARIO_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + ContratoUsuario.BitacoraUsuario.ID_USUARIO + " INTEGER , "
                    + ContratoUsuario.BitacoraUsuario.OPERACION + " INTEGER ); "
            );

            // Se ha comentado la línea que inicializa los datos, para no tener que crear su bitácora, se puede reactivar en un futuro
            //inicializarDatos(db);

        }

        // Se ha comentado la línea que inicializa los datos, para no tener que crear su bitácora, hacer en un futuro
        void inicializarDatos(SQLiteDatabase db){

//            db.execSQL("INSERT INTO " + USUARIO_TABLE_NAME + " (" +  ContratoUsuario.Usuario._ID + "," + ContratoUsuario.Usuario.NOMBRE + "," + ContratoUsuario.Usuario.PASSWORD + ") " +
//                    "VALUES (1,'Ibuprofeno','Granulado')");
//            db.execSQL("INSERT INTO " + USUARIO_TABLE_NAME + " (" +  ContratoUsuario.Usuario._ID + "," + ContratoUsuario.Usuario.NOMBRE + "," + ContratoUsuario.Usuario.PASSWORD + ") " +
//                    "VALUES (2,'Ibuprofeno','Comprimido')");
//            db.execSQL("INSERT INTO " + USUARIO_TABLE_NAME + " (" +  ContratoUsuario.Usuario._ID + "," + ContratoUsuario.Usuario.NOMBRE + "," + ContratoUsuario.Usuario.PASSWORD + ") " +
//                    "VALUES (3,'Aspirina','Comprimido')");
//            db.execSQL("INSERT INTO " + USUARIO_TABLE_NAME + " (" +  ContratoUsuario.Usuario._ID + "," + ContratoUsuario.Usuario.NOMBRE + "," + ContratoUsuario.Usuario.PASSWORD + ") " +
//                    "VALUES (4,'Nolotil','Inyectable')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + USUARIO_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + BITACORAUSUARIO_TABLE_NAME);

            onCreate(db);
        }

    }

    public ProveedorDeContenidoUsuario() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case USUARIO_ALL_REGS:
                table = USUARIO_TABLE_NAME;
                break;
            case BITACORAUSUARIO_ALL_REGS:
                table = BITACORAUSUARIO_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insertRecord row into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insertRecord record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoUsuario.Usuario._ID + " = "
                        + uri.getLastPathSegment();
                table = USUARIO_TABLE_NAME;
                break;
            case USUARIO_ALL_REGS:
                table = USUARIO_TABLE_NAME;
                break;
            case BITACORAUSUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoUsuario.BitacoraUsuario._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORAUSUARIO_TABLE_NAME;
                break;
            case BITACORAUSUARIO_ALL_REGS:
                table = BITACORAUSUARIO_TABLE_NAME;
                break;
        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to deleteRecord row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (sUriMatcher.match(uri)) {
            case USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoUsuario.Usuario._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(USUARIO_TABLE_NAME);
                break;
            case USUARIO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        ContratoUsuario.Usuario._ID + " ASC";
                qb.setTables(USUARIO_TABLE_NAME);
                break;
            case BITACORAUSUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoUsuario.BitacoraUsuario._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(BITACORAUSUARIO_TABLE_NAME);
                break;
            case BITACORAUSUARIO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        ContratoUsuario.BitacoraUsuario._ID + " ASC";
                qb.setTables(BITACORAUSUARIO_TABLE_NAME);
                break;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null,
                        sortOrder);
        //c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insertRecord record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case USUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoUsuario.Usuario._ID + " = "
                        + uri.getLastPathSegment();
                table = USUARIO_TABLE_NAME;
                break;
            case USUARIO_ALL_REGS:
                table = USUARIO_TABLE_NAME;
                break;
            case BITACORAUSUARIO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoUsuario.BitacoraUsuario._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORAUSUARIO_TABLE_NAME;
                break;
            case BITACORAUSUARIO_ALL_REGS:
                table = BITACORAUSUARIO_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to updateRecord row into " + uri);
    }
}
