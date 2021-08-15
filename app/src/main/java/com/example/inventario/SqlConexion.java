package com.example.inventario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlConexion extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Inventario.db";

    public SqlConexion(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE cliente (" +
                "clave TEXT NOT NULL, " +
                "nombre TEXT NOT NULL," +
                "calle TEXT NOT NULL," +
                "colonia TEXT NOT NULL," +
                "ciudad TEXT NOT NULL," +
                "rfc TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "saldo TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE producto (" +
                "clave_p TEXT NOT NULL, " +
                "nombre_p TEXT NOT NULL, " +
                "linea TEXT NOT NULL, " +
                "existencias TEXT NOT NULL, " +
                "costo TEXT NOT NULL, " +
                "promedio TEXT NOT NULL, " +
                "pventa1 TEXT NOT NULL, " +
                "pventa2 TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
