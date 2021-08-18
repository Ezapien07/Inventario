package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Clientes_activity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtClave, edtNombre, edtCalle, edtColonia, edtCiudad, edtRfc, edtTelefono, edtEmail, edtSaldo;
    Button btnAdd,
            btnDelete,
            btnModify,
            btnView,
            btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        edtClave = findViewById(R.id.edtNo);
        edtNombre = findViewById(R.id.edtNombreCliente);
        edtCalle = findViewById(R.id.edtCalleCliente);
        edtColonia = findViewById(R.id.edtColoniaCliente);
        edtCiudad = findViewById(R.id.edtCiudadCliente);
        edtRfc = findViewById(R.id.edtRFCCliente);
        edtTelefono = findViewById(R.id.edtTelefonoCliente);
        edtEmail = findViewById(R.id.edtEmailCliente);
        edtSaldo = findViewById(R.id.edtSaldoCliente);

        btnAdd = findViewById(R.id.btnAgregar);
        btnAdd.setOnClickListener(this);

        btnView = findViewById(R.id.btnBuscar);
        btnView.setOnClickListener(this);

        btnModify = findViewById(R.id.btnModificar);
        btnModify.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnBaja);
        btnDelete.setOnClickListener(this);

        btnViewAll = findViewById(R.id.btnConsulta);
        btnViewAll.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == btnAdd) {
            if (edtClave.getText().toString().trim().length() == 0 ||
                    edtNombre.getText().toString().trim().length() == 0 ||
                    edtCalle.getText().toString().trim().length() == 0 ||
                    edtColonia.getText().toString().trim().length() == 0 ||
                    edtCiudad.getText().toString().trim().length() == 0 ||
                    edtRfc.getText().toString().trim().length() == 0 ||
                    edtTelefono.getText().toString().trim().length() == 0 ||
                    edtEmail.getText().toString().trim().length() == 0 ||
                    edtSaldo.getText().toString().trim().length() == 0) {
                showMessage("Error!", "Porfavor introduce todos los valores");
                return;
            }

            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            db.execSQL("INSERT INTO cliente VALUES('" + edtClave.getText().toString().trim() + "','"
                    + edtNombre.getText().toString().trim() + "','"
                    + edtCalle.getText().toString().trim() + "','"
                    + edtColonia.getText().toString().trim() + "','"
                    + edtCiudad.getText().toString().trim() + "','"
                    + edtRfc.getText().toString().trim() + "','"
                    + edtTelefono.getText().toString().trim() + "','"
                    + edtEmail.getText().toString().trim() + "','"
                    + edtSaldo.getText().toString().trim() + "');");
            showMessage("Exito!", "Cliente agregado");
            clearText();
        }

        if (view == btnView) {
            if (edtClave.getText().toString().trim().length() == 0) {
                showMessage("Error!", "Introduce Clave");
                return;
            }
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM cliente WHERE clave='" + edtClave.getText() + "'", null);
            if (c.moveToFirst()) {
                edtClave.setText(c.getString(0));
                edtNombre.setText(c.getString(1));
                edtCalle.setText(c.getString(2));
                edtColonia.setText(c.getString(3));
                edtCiudad.setText(c.getString(4));
                edtRfc.setText(c.getString(5));
                edtTelefono.setText(c.getString(6));
                edtEmail.setText(c.getString(7));
                edtSaldo.setText(c.getString(8));
            } else {
                showMessage("Error!", "Clave no valida");
                clearText();
            }
        }

        if (view == btnModify) {
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            db.execSQL("UPDATE cliente SET " +
                    "nombre = '" + edtNombre.getText() + " '," +
                    "calle = '" + edtCalle.getText() + " '," +
                    "colonia = '" + edtColonia.getText() + " '," +
                    "ciudad = '" + edtCiudad.getText() + " '," +
                    "rfc = '" + edtRfc.getText() + " '," +
                    "telefono = '" + edtTelefono.getText() + " '," +
                    "email = '" + edtEmail.getText() + " '," +
                    "saldo = '" + edtSaldo.getText() + " '" +
                    " WHERE clave = " + edtClave.getText() + ';');
            showMessage("Exito!", "Cliente modificado");
            clearText();
        }

        if (view == btnDelete) {
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            db.execSQL("DELETE FROM cliente WHERE clave = " + edtClave.getText().toString().trim() + ";");
            showMessage("Exito!", "Cliente eliminado");
            clearText();
        }

        if (view == btnViewAll) {
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM cliente", null);
            if (c.getCount() == 0) {
                showMessage("Error!", "No se encontraron clientes");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Clave...: " + c.getString(0) + "\n");
                buffer.append("Nombre..: " + c.getString(1) + "\n");
                buffer.append("Calle..: " + c.getString(2) + "\n");
                buffer.append("Colonia..: " + c.getString(3) + "\n");
                buffer.append("Ciudad..: " + c.getString(4) + "\n");
                buffer.append("RFC..: " + c.getString(5) + "\n");
                buffer.append("Telefono..: " + c.getString(6) + "\n");
                buffer.append("Email..: " + c.getString(7) + "\n");
                buffer.append("Saldo..: " + c.getString(8) + "\n\n");
            }
            showMessage("Cliente", buffer.toString());
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        edtClave.setText("");
        edtNombre.setText("");
        edtCalle.setText("");
        edtColonia.setText("");
        edtCiudad.setText("");
        edtRfc.setText("");
        edtTelefono.setText("");
        edtEmail.setText("");
        edtSaldo.setText("");
    }
}