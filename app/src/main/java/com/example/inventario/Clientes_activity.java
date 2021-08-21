package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Clientes_activity extends AppCompatActivity {

    private EditText edtClave, edtNombre, edtCalle, edtColonia, edtCiudad, edtRfc, edtTelefono, edtEmail, edtSaldo;
    Button btnAdd,
            btnDelete,
            btnModify,
            btnView,
            btnViewAll;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        edtClave =(EditText)findViewById(R.id.edtClaveCliente);
        edtNombre =(EditText)findViewById(R.id.edtNombreCliente);
        edtCalle =(EditText)findViewById(R.id.edtCalleCliente);
        edtColonia =(EditText)findViewById(R.id.edtColoniaCliente);
        edtCiudad =(EditText)findViewById(R.id.edtColoniaCliente);
        edtRfc =(EditText)findViewById(R.id.edtRFCCliente);
        edtTelefono =(EditText)findViewById(R.id.edtTelefonoCliente);
        edtEmail =(EditText)findViewById(R.id.edtEmailCliente);
        edtSaldo =(EditText)findViewById(R.id.edtSaldoCliente);

        db=openOrCreateDatabase("Inventario", Context.MODE_PRIVATE, null);
        //db.execSQL("DROP TABLE IF  EXISTS  clientes ");
        db.execSQL("CREATE TABLE IF NOT EXISTS  clientes (clave VARCHAR  , nombre VARCHAR  ,calle VARCHAR  , colonia VARCHAR  ,ciudad VARCHAR  , rfc VARCHAR  , telefono VARCHAR ,email VARCHAR  ,saldo VARCHAR  )");


        btnAdd =(Button)findViewById(R.id.btnAgregar);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCliente();
            }
        });

        btnView =(Button)findViewById(R.id.btnBuscar);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCliente();
            }
        });

        btnModify =(Button)findViewById(R.id.btnModificar);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarCliente();
            }
        });

        btnDelete =(Button)findViewById(R.id.btnBaja);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarCliente();
            }
        });

        btnViewAll =(Button)findViewById(R.id.btnConsulta);
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllClientes();
            }
        });
    }
    public void agregarCliente(){
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
        db.execSQL("INSERT INTO clientes VALUES('" + edtClave.getText() + "','"
                + edtNombre.getText() + "','"
                + edtCalle.getText()+ "','"
                + edtColonia.getText()+ "','"
                + edtCiudad.getText() + "','"
                + edtRfc.getText()+ "','"
                + edtTelefono.getText()+ "','"
                + edtEmail.getText() + "','"
                + edtSaldo.getText() + "');");
        showMessage("Exito!", "clientes agregado");
        clearText();
    }

    public void buscarCliente(){
        if (edtClave.getText().toString().trim().length() == 0) {
            showMessage("Error!", "Introduce Clave");
            return;
        }
        Cursor c = db.rawQuery("SELECT * FROM clientes WHERE clave='" + edtClave.getText() + "'", null);
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

    public void modificarCliente(){
        db.execSQL("UPDATE clientes SET " +
                "nombre = '" + edtNombre.getText() + " '," +
                "calle = '" + edtCalle.getText() + " '," +
                "colonia = '" + edtColonia.getText() + " '," +
                "ciudad = '" + edtCiudad.getText() + " '," +
                "rfc = '" + edtRfc.getText() + " '," +
                "telefono = '" + edtTelefono.getText() + " '," +
                "email = '" + edtEmail.getText() + " '," +
                "saldo = '" + edtSaldo.getText() + " '" +
                " WHERE clave = " + edtClave.getText() + ';');
        showMessage("Exito!", "clientes modificado");
        clearText();

    }

    public void borrarCliente(){
        db.execSQL("DELETE FROM clientes WHERE clave = " + edtClave.getText().toString().trim() + ";");
        showMessage("Exito!", "clientes eliminado");
        clearText();

    }

    public void ViewAllClientes(){
        Cursor c = db.rawQuery("SELECT * FROM clientes", null);
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

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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