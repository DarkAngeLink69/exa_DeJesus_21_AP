package com.example.exa_dejesus_21_ap;

import  android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteDatabase.CursorFactory;
import  android.database.sqlite.SQLiteOpenHelper;
import  android.content.Context;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea tabla en base de datos administracion
        db.execSQL("CREATE TABLE estudiantes (matri INTEGER PRIMARY KEY, nombre TEXT, carrera TEXT, calificacion1 REAL, calificacion2 REAL, promedio REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists estudiantes");
        db.execSQL("CREATE TABLE estudiantes (matri INTEGER PRIMARY KEY, nombre TEXT, carrera TEXT, calificacion1 REAL, calificacion2 REAL, promedio REAL)");
    }//inicia clase

}