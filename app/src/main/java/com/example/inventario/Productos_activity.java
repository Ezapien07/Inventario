package com.example.inventario;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Productos_activity extends AppCompatActivity {

    private EditText edtClave, edtNombre, edtLinea, edtExistencias, edtCosto, edtPromedio, edtVenta1, edtVenta2;
    Button btnAdd,
            btnDelete,
            btnModify,
            btnView,
            btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        edtClave = findViewById(R.id.edtNoP);
        edtNombre = findViewById(R.id.edtNombreProducto);
        edtLinea = findViewById(R.id.edtLinea);
        edtExistencias = findViewById(R.id.edtExistencias);
        edtCosto = findViewById(R.id.edtCosto);
        edtPromedio = findViewById(R.id.edtPromedio);
        edtVenta1 = findViewById(R.id.edtVenta1);
        edtVenta2 = findViewById(R.id.edtVenta2);

        btnAdd = findViewById(R.id.btnAgregar);
        btnAdd.setOnClickListener((View.OnClickListener) this);

        btnView = findViewById(R.id.btnBuscar);
        btnView.setOnClickListener((View.OnClickListener) this);

        btnModify = findViewById(R.id.btnModificar);
        btnModify.setOnClickListener((View.OnClickListener) this);

        btnDelete = findViewById(R.id.btnBaja);
        btnDelete.setOnClickListener((View.OnClickListener) this);

        btnViewAll = findViewById(R.id.btnConsulta);
        btnViewAll.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View view) {
        if (view == btnAdd) {
            if (edtClave.getText().toString().trim().length() == 0 ||
                    edtNombre.getText().toString().trim().length() == 0 ||
                    edtLinea.getText().toString().trim().length() == 0 ||
                    edtExistencias.getText().toString().trim().length() == 0 ||
                    edtCosto.getText().toString().trim().length() == 0 ||
                    edtPromedio.getText().toString().trim().length() == 0 ||
                    edtVenta1.getText().toString().trim().length() == 0 ||
                    edtVenta2.getText().toString().trim().length() == 0) {
                showMessage("Error!", "Porfavor introduce todos los valores");
                return;
            }

            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            db.execSQL("INSERT INTO producto VALUES('" + edtClave.getText().toString().trim() + "','"
                    + edtNombre.getText().toString().trim() + "','"
                    + edtLinea.getText().toString().trim() + "','"
                    + edtExistencias.getText().toString().trim() + "','"
                    + edtCosto.getText().toString().trim() + "','"
                    + edtPromedio.getText().toString().trim() + "','"
                    + edtVenta1.getText().toString().trim() + "','"
                    + edtVenta2.getText().toString().trim() + "');");
            showMessage("Exito!", "Producto agregado");
            clearText();
        }

        if (view == btnView) {
            if (edtClave.getText().toString().trim().length() == 0) {
                showMessage("Error!", "Introduce Clave");
                return;
            }
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM producto WHERE clave_p='" + edtClave.getText() + "'", null);
            if (c.moveToFirst()) {
                edtClave.setText(c.getString(0));
                edtNombre.setText(c.getString(1));
                edtLinea.setText(c.getString(2));
                edtExistencias.setText(c.getString(3));
                edtCosto.setText(c.getString(4));
                edtPromedio.setText(c.getString(5));
                edtVenta1.setText(c.getString(6));
                edtVenta2.setText(c.getString(7));
            } else {
                showMessage("Error!", "Clave no valida");
                clearText();
            }
        }

        if (view == btnModify) {
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            db.execSQL("UPDATE producto SET " +
                    "nombre_p = '" + edtNombre.getText() + " '," +
                    "linea = '" + edtLinea.getText() + " '," +
                    "existencias = '" + edtExistencias.getText() + " '," +
                    "costo = '" + edtCosto.getText() + " '," +
                    "promedio = '" + edtPromedio.getText() + " '," +
                    "pventa1 = '" + edtVenta1.getText() + " '," +
                    "pventa2 = '" + edtVenta2.getText() + " '" +
                    " WHERE clave_p = " + edtClave.getText() + ';');
            showMessage("Exito!", "Producto modificado");
            clearText();
        }

        if (view == btnDelete) {
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            db.execSQL("DELETE FROM producto WHERE clave_p = " + edtClave.getText().toString().trim() + ";");
            showMessage("Exito!", "Producto eliminado");
            clearText();
        }

        if (view == btnViewAll) {
            SqlConexion sql = new SqlConexion(getApplicationContext());
            SQLiteDatabase db = sql.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM producto", null);
            if (c.getCount() == 0) {
                showMessage("Error!", "No se encontraron productos");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Clave...: " + c.getString(0) + "\n");
                buffer.append("Nombre..: " + c.getString(1) + "\n");
                buffer.append("Linea..: " + c.getString(2) + "\n");
                buffer.append("Existencias..: " + c.getString(3) + "\n");
                buffer.append("Costo..: " + c.getString(4) + "\n");
                buffer.append("Promedio..: " + c.getString(5) + "\n");
                buffer.append("Venta 1..: " + c.getString(6) + "\n");
                buffer.append("Venta 2..: " + c.getString(7) + "\n");
            }
            showMessage("Producto", buffer.toString());
        }
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
        edtLinea.setText("");
        edtExistencias.setText("");
        edtCosto.setText("");
        edtPromedio.setText("");
        edtVenta1.setText("");
        edtVenta2.setText("");
    }
}
