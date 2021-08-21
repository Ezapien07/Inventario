package com.example.inventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Productos_activity extends AppCompatActivity {

    private EditText edtClave, edtNombre, edtLinea, edtExistencias, edtCosto, edtPromedio, edtVenta1, edtVenta2;
    Button btnAdd,
            btnDelete,
            btnModify,
            btnView,
            btnViewAll;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        edtClave =(EditText)findViewById(R.id.edtNoP);
        edtNombre =(EditText) findViewById(R.id.edtNombreProducto);
        edtLinea =(EditText)findViewById(R.id.edtLinea);
        edtExistencias =(EditText)findViewById(R.id.edtExistencias);
        edtCosto =(EditText)findViewById(R.id.edtCosto);
        edtPromedio =(EditText)findViewById(R.id.edtPromedio);
        edtVenta1 =(EditText)findViewById(R.id.edtVenta1);
        edtVenta2 =(EditText)findViewById(R.id.edtVenta2);

        btnAdd =(Button)findViewById(R.id.btnAgregar);
        btnAdd.setOnClickListener(v -> {agregarProductos();});

        btnView =(Button)findViewById(R.id.btnBuscar);
        btnView.setOnClickListener(v -> {viewProductos();});

        btnModify =(Button)findViewById(R.id.btnModificar);
        btnModify.setOnClickListener(v -> {modificarProductos();});

        btnDelete =(Button)findViewById(R.id.btnBaja);
        btnDelete.setOnClickListener(v -> {bajaProductos();});

        btnViewAll =(Button)findViewById(R.id.btnConsulta);
        btnViewAll.setOnClickListener(v -> {viewAllProductos();});

        db=openOrCreateDatabase("Inventario", Context.MODE_PRIVATE, null);
        //db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("CREATE TABLE IF NOT EXISTS producto(clave_p VARCHAR primary key,nombre_p VARCHAR , linea VARCHAR,existencias VARCHAR, costo VARCHAR,promedio VARCHAR, pventa1 VARCHAR, pventa2 VARCHAR);");
    }
    public void agregarProductos(){
        if (edtClave.getText().toString().trim().length() == 0 ||
                edtNombre.getText().toString().trim().length() == 0 ||
                edtLinea.getText().toString().trim().length() == 0 ||
                edtExistencias.getText().toString().trim().length() == 0 ||
                edtCosto.getText().toString().trim().length() == 0 ||
                edtPromedio.getText().toString().trim().length() == 0 ){
            showMessage("Error!", "Porfavor introduce todos los valores");
            return;
        }
        else{
            String venta = edtCosto.getText().toString().trim();
            double venta1 = 0;
            venta1 = Double.parseDouble(venta) * 1.4;
            double venta2 = 0;
            venta2 = Double.parseDouble(venta) * 1.28;
            db.execSQL("INSERT INTO producto VALUES('" + edtClave.getText()+ "','"
                    + edtNombre.getText()+ "','"
                    + edtLinea.getText() + "','"
                    + edtExistencias.getText() + "','"
                    + edtCosto.getText() + "','"
                    + edtPromedio.getText() + "','"
                    + venta1 + "','"
                    + venta2  + "');");
            showMessage("Exito!", "Producto agregado");
            clearText();
        }

    }

    public void modificarProductos(){
        if (edtClave.getText().toString().trim().length() == 0) {
            showMessage("Error!", "Introduce Clave");
            return;
        }
        else{
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

    }

    public void viewProductos(){
        if (edtClave.getText().toString().trim().length() == 0) {
            showMessage("Error!", "Introduce Clave");
            return;
        }
        else{
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

    }

    public void bajaProductos(){
        if (edtClave.getText().toString().trim().length() == 0) {
            showMessage("Error!", "Introduce Clave");
            return;
        }
        else{
            db.execSQL("DELETE FROM producto WHERE clave_p = " + edtClave.getText().toString().trim() + ";");
            showMessage("Exito!", "Producto eliminado");
            clearText();
        }

    }

    public void viewAllProductos(){
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
            buffer.append("Venta 2..: " + c.getString(7) + "\n\n");
        }
        showMessage("Producto", buffer.toString());

    }

    public void showMessage(String title,String message) {
        androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(this);
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
