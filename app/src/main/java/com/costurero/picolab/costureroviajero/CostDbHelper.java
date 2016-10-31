package com.costurero.picolab.costureroviajero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.sql.Types.NULL;

/**
 * Created by PiCoLab on 2016-10-09.
 */

public class CostDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CostureroViajero.db";

    private SQLiteDatabase sqLiteDatabaseP;
    private SQLiteOpenHelper sqLiteHelper;

    public CostDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void Read() throws android.database.SQLException {
        sqLiteDatabaseP = this.getReadableDatabase();
    }

    public void Write() throws android.database.SQLException {
        sqLiteDatabaseP = this.getWritableDatabase();
    }

    public void close(){
        sqLiteHelper.close();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Comandos SQL
        sqLiteDatabase.execSQL("CREATE TABLE " + LocalizacionContractClass.LocalizacionEntry.TABLE_NAME + " ("
                + LocalizacionContractClass.LocalizacionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LocalizacionContractClass.LocalizacionEntry.nombre + " TEXT,"
                + LocalizacionContractClass.LocalizacionEntry.municipio + " TEXT NOT NULL,"
                + LocalizacionContractClass.LocalizacionEntry.latitud + " TEXT,"
                + LocalizacionContractClass.LocalizacionEntry.longitud + " TEXT,"
                + LocalizacionContractClass.LocalizacionEntry.sincronizado + " INTEGER,"
                + "UNIQUE (" + LocalizacionContractClass.LocalizacionEntry._ID + "))");
        sqLiteDatabase.execSQL("CREATE TABLE " + CostureroContractClass.CostureroEntry.TABLE_NAME + " ("
                + CostureroContractClass.CostureroEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CostureroContractClass.CostureroEntry.nombre + " TEXT NOT NULL,"
                + CostureroContractClass.CostureroEntry.localizacion + " INTEGER NOT NULL,"
                + CostureroContractClass.CostureroEntry.historia + " TEXT,"
                + CostureroContractClass.CostureroEntry.path + " TEXT,"
                + CostureroContractClass.CostureroEntry.sincronizado + " INTEGER,"
                + "UNIQUE (" + CostureroContractClass.CostureroEntry._ID + "))");
        sqLiteDatabase.execSQL("CREATE TABLE " + EncuentroContractClass.EncuentroEntry.TABLE_NAME + " ("
                + EncuentroContractClass.EncuentroEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EncuentroContractClass.EncuentroEntry.costurero + " INTEGER NOT NULL,"
                + EncuentroContractClass.EncuentroEntry.fecha + " TEXT NOT NULL,"
                + EncuentroContractClass.EncuentroEntry.bitacora + " TEXT,"
                + EncuentroContractClass.EncuentroEntry.sincronizado + " INTEGER,"
                + "UNIQUE (" + EncuentroContractClass.EncuentroEntry._ID + "))");
        sqLiteDatabase.execSQL("CREATE TABLE " + FotoVideoContractClass.FotoVideoEntry.TABLE_NAME + " ("
                + FotoVideoContractClass.FotoVideoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FotoVideoContractClass.FotoVideoEntry.encuentro + " INTEGER NOT NULL,"
                + FotoVideoContractClass.FotoVideoEntry.etiquetas + " TEXT NOT NULL,"
                + FotoVideoContractClass.FotoVideoEntry.etiquetasS + " TEXT,"
                + FotoVideoContractClass.FotoVideoEntry.path + " TEXT NOT NULL,"
                + FotoVideoContractClass.FotoVideoEntry.tipo + " INTEGER NOT NULL,"
                + FotoVideoContractClass.FotoVideoEntry.sincronizado + " INTEGER NOT NULL,"
                + "UNIQUE (" + FotoVideoContractClass.FotoVideoEntry._ID + "))");
        sqLiteDatabase.execSQL("CREATE TABLE " + ParticipanteContractClass.ParticipanteEntry.TABLE_NAME + " ("
                + ParticipanteContractClass.ParticipanteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ParticipanteContractClass.ParticipanteEntry.costurero + " INTEGER NOT NULL,"
                + ParticipanteContractClass.ParticipanteEntry.nombre + " TEXT NOT NULL,"
                + ParticipanteContractClass.ParticipanteEntry.historia + " TEXT,"
                + ParticipanteContractClass.ParticipanteEntry.path + " TEXT,"
                + ParticipanteContractClass.ParticipanteEntry.sincronizado + " INTEGER,"
                + "UNIQUE (" + ParticipanteContractClass.ParticipanteEntry._ID + "))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
    public long insertLocalizacion(String nom, String mun, String lat, String lng){
        ContentValues values = new ContentValues();
        values.put(LocalizacionContractClass.LocalizacionEntry.nombre,nom);
        values.put(LocalizacionContractClass.LocalizacionEntry.municipio,mun);
        values.put(LocalizacionContractClass.LocalizacionEntry.latitud,lat);
        values.put(LocalizacionContractClass.LocalizacionEntry.longitud,lng);
        return sqLiteDatabaseP.insert(LocalizacionContractClass.LocalizacionEntry.TABLE_NAME, null, values);
    }

    public long insertFotoVideo(long idE, String etP, String etS, String pat, int tipo, int sin){
        ContentValues values = new ContentValues();
        values.put(FotoVideoContractClass.FotoVideoEntry.encuentro,idE);
        values.put(FotoVideoContractClass.FotoVideoEntry.etiquetas,etP);
        values.put(FotoVideoContractClass.FotoVideoEntry.etiquetasS,etS);
        values.put(FotoVideoContractClass.FotoVideoEntry.path,pat);
        values.put(FotoVideoContractClass.FotoVideoEntry.tipo,tipo);
        values.put(FotoVideoContractClass.FotoVideoEntry.sincronizado,sin);
        return sqLiteDatabaseP.insert(FotoVideoContractClass.FotoVideoEntry.TABLE_NAME, null, values);
    }

    public long insertCosturero(String nom, long idL, String hist, String path){
        ContentValues values = new ContentValues();
        values.put(CostureroContractClass.CostureroEntry.nombre,nom);
        values.put(CostureroContractClass.CostureroEntry.localizacion,idL);
        values.put(CostureroContractClass.CostureroEntry.historia,hist);
        values.put(CostureroContractClass.CostureroEntry.path,path);
        return sqLiteDatabaseP.insert(CostureroContractClass.CostureroEntry.TABLE_NAME, null, values);
    }

    public long insertParticipante(String nom, String hist, String path, long idC){
        ContentValues values = new ContentValues();
        values.put(ParticipanteContractClass.ParticipanteEntry.costurero,idC);
        values.put(ParticipanteContractClass.ParticipanteEntry.nombre,nom);
        values.put(ParticipanteContractClass.ParticipanteEntry.historia, hist);
        values.put(ParticipanteContractClass.ParticipanteEntry.path,path);
        return sqLiteDatabaseP.insert(ParticipanteContractClass.ParticipanteEntry.TABLE_NAME, null, values);
    }

    public long insertEncuentro(String dat, long idC){
        ContentValues values = new ContentValues();
        values.put(EncuentroContractClass.EncuentroEntry.costurero,idC);
        values.put(EncuentroContractClass.EncuentroEntry.fecha,dat);
        return sqLiteDatabaseP.insert(EncuentroContractClass.EncuentroEntry.TABLE_NAME, null, values);
    }

    public Cursor readLocalizacion(){
        Cursor c=sqLiteDatabaseP.query(
                LocalizacionContractClass.LocalizacionEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }
    /*public Cursor readLocalizacionId(long id){
        String selectQuery = "SELECT "+LocalizacionContractClass.LocalizacionEntry.nombre+", "+ LocalizacionContractClass.LocalizacionEntry.municipio+", "+ LocalizacionContractClass.LocalizacionEntry.latitud+", "+LocalizacionContractClass.LocalizacionEntry.longitud+" FROM " + LocalizacionContractClass.LocalizacionEntry.TABLE_NAME+ " WHERE "+ LocalizacionContractClass.LocalizacionEntry._ID+"= " + id ;
        Cursor c = sqLiteDatabaseP.rawQuery(selectQuery,null);
        return c;
    }*/
    public Cursor readCosturero(){
        Cursor c=sqLiteDatabaseP.query(
                CostureroContractClass.CostureroEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }
    public Cursor readFotoVideo(){
        Cursor c=sqLiteDatabaseP.query(
                FotoVideoContractClass.FotoVideoEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }
    public Cursor readEncuentro(){
        Cursor c=sqLiteDatabaseP.query(
                EncuentroContractClass.EncuentroEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }
    public Cursor readParticipante(){
        Cursor c=sqLiteDatabaseP.query(
                ParticipanteContractClass.ParticipanteEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }
}

