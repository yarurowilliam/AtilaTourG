package com.example.atilaversionbeta.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.atilaversionbeta.BaseDatos.AtilaBD;

public class ConexionSQLiteHelperActividad extends SQLiteOpenHelper {

    public ConexionSQLiteHelperActividad(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(AtilaBD.CREAR_TABLA_ACTIVIDAD);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + AtilaBD.TABLA_ACTIVIDAD);
        onCreate(db);*/
        db.setVersion(newVersion);
    }



}
