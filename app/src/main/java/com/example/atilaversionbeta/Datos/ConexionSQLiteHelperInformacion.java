package com.example.atilaversionbeta.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.atilaversionbeta.BaseDatos.AtilaBD;

public class ConexionSQLiteHelperInformacion extends SQLiteOpenHelper {
    public ConexionSQLiteHelperInformacion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(AtilaBD.CREAR_TABLA_INFORMACION);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS informacion");
        onCreate(db);
        //db.setVersion(newVersion);
    }

}
