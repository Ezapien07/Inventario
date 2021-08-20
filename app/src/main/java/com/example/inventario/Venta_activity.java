package com.example.inventario;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Venta_activity extends AppCompatActivity{

    EditText edtClave, edtNC, edtCaC, edtNV, edtFecha, edtComision;
    Spinner spnCC, spnCV, spnP;
    TextView txtClave, txtDescrip, txtUnidadV, txtLineaV, txtCantidadV, txtPVenta, txtImporteV;

    ArrayList<String> listaCliente;
    ArrayList<Cliente> clienteList;

    ArrayList<String> listaVendedor;
    ArrayList<Vendedor> vendedorList;

    ArrayList<String> listaProducto;
    ArrayList<Producto> productoList;

    Button btnAdd, btnDelete, btnModify, btnView, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        txtClave = findViewById(R.id.txtClave);
        txtDescrip = findViewById(R.id.txtDescrip);
        txtUnidadV = findViewById(R.id.txtUnidadV);
        txtLineaV = findViewById(R.id.txtLineaV);
        txtCantidadV = findViewById(R.id.txtCantidadV);
        txtPVenta = findViewById(R.id.txtPVenta);
        txtImporteV = findViewById(R.id.txtImporteV);

        edtClave = findViewById(R.id.edtNoV);

        spnCC = findViewById(R.id.edtClaveCliente);
        spnCV = findViewById(R.id.edtClaveVendedor);
        spnP = findViewById(R.id.spnP);

        edtNC = findViewById(R.id.edtNCVenta);
        edtCaC = findViewById(R.id.edtCaCVenta);
        edtNV = findViewById(R.id.edtNVVenta);

        edtFecha = findViewById(R.id.edtFechaV);
        edtComision = findViewById(R.id.edtComisionV);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        edtFecha.setText(formattedDate);

        double comi = 0.02;
        edtComision.setText(String.valueOf(comi));

        consultarClientes();

        ArrayAdapter<CharSequence> adaptadorC = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCliente);
        spnCC.setAdapter(adaptadorC);
        spnCC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    edtNC.setText(clienteList.get(position - 1).getNombre());
                    edtCaC.setText(clienteList.get(position - 1).getCalle());
                } else {
                    edtNC.setText("");
                    edtCaC.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        consultarVendedor();

        ArrayAdapter<CharSequence> adaptadorV = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaVendedor);
        spnCV.setAdapter(adaptadorV);
        spnCV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    edtNV.setText(vendedorList.get(position - 1).getNombre());
                } else {
                    edtNV.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        consultarProducto();

        ArrayAdapter<CharSequence> adaptadorP = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProducto);
        spnP.setAdapter(adaptadorP);
        spnP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    txtClave.setText(productoList.get(position - 1).getId());
                    txtDescrip.setText(productoList.get(position - 1).getNombre());
                    txtUnidadV.setText("Par");
                    txtLineaV.setText(productoList.get(position - 1).getLinea());
                    txtCantidadV.setText(String.valueOf(productoList.get(position - 1).getExistencias()));
                    txtPVenta.setText(String.valueOf(productoList.get(position - 1).getCosto()));
                    String impCV;
                    impCV = txtCantidadV.getText().toString();
                    String impPV;
                    impPV = txtPVenta.getText().toString();
                    double importe = 0;
                    importe = Double.parseDouble(impCV) * Double.parseDouble(impPV);
                    txtImporteV.setText(String.valueOf(importe));
                } else {
                    txtClave.setText("");
                    txtDescrip.setText("");
                    txtUnidadV.setText("");
                    txtLineaV.setText("");
                    txtCantidadV.setText("");
                    txtPVenta.setText("");
                    txtImporteV.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd = findViewById(R.id.btnAgregarV);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarVenta();
            }
        });
    }

    private void consultarClientes() {
        SqlConexion sql = new SqlConexion(getApplicationContext());
        SQLiteDatabase db = sql.getWritableDatabase();

        Cliente c = null;
        clienteList = new ArrayList<Cliente>();
        Cursor cursor = db.rawQuery("SELECT * FROM cliente", null);

        while (cursor.moveToNext()) {
            c = new Cliente();
            c.setId(cursor.getInt(0));
            c.setNombre(cursor.getString(1));
            c.setCalle(cursor.getString(2));

            clienteList.add(c);
        }

        obtenerListaC();
    }

    private void obtenerListaC() {
        listaCliente = new ArrayList<String>();
        listaCliente.add("Seleccione: ");

        for (int i = 0; i < clienteList.size(); i++) {
            listaCliente.add(clienteList.get(i).getId() + " - " + clienteList.get(i).getNombre());
        }
    }

    private void consultarVendedor() {
        SqlConexion sql = new SqlConexion(getApplicationContext());
        SQLiteDatabase db = sql.getWritableDatabase();

        Vendedor v = null;
        vendedorList = new ArrayList<Vendedor>();
        Cursor cursor = db.rawQuery("SELECT * FROM vendedores", null);

        while (cursor.moveToNext()) {
            v = new Vendedor();
            v.setId(cursor.getInt(0));
            v.setNombre(cursor.getString(1));

            vendedorList.add(v);
        }

        obtenerListaV();
    }

    private void obtenerListaV() {
        listaVendedor = new ArrayList<String>();
        listaVendedor.add("Seleccione: ");

        for (int i = 0; i < vendedorList.size(); i++) {
            listaVendedor.add(vendedorList.get(i).getId() + " - " + vendedorList.get(i).getNombre());
        }
    }

    private void consultarProducto() {
        SqlConexion sql = new SqlConexion(getApplicationContext());
        SQLiteDatabase db = sql.getWritableDatabase();

        Producto p = null;
        productoList = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT * FROM producto", null);

        while (cursor.moveToNext()) {
            p = new Producto();
            p.setId(cursor.getString(0));
            p.setNombre(cursor.getString(1));
            p.setLinea(cursor.getString(2));
            p.setExistencias(cursor.getInt(3));
            p.setCosto(cursor.getInt(4));

            productoList.add(p);
        }

        obtenerListaP();
    }

    private void obtenerListaP() {
        listaProducto = new ArrayList<String>();
        listaProducto.add("Seleccione: ");

        for (int i = 0; i < productoList.size(); i++) {
            listaProducto.add(productoList.get(i).getId() + " - " + productoList.get(i).getNombre());
        }
    }

    private void agregarVenta(){
        SqlConexion sql = new SqlConexion(getApplicationContext());
        SQLiteDatabase db = sql.getWritableDatabase();
        db.execSQL("INSERT INTO venta VALUES('" + edtClave.getText().toString().trim() + "','"
                + edtNC.getText().toString().trim() + "','"
                + edtCaC.getText().toString().trim() + "','"
                + edtNV.getText().toString().trim() + "','"
                + edtFecha.getText().toString().trim() + "','"
                + txtClave.getText().toString().trim() + "','"
                + txtDescrip.getText().toString().trim() + "','"
                + txtUnidadV.getText().toString().trim() + "','"
                + txtLineaV.getText().toString().trim() + "','"
                + txtCantidadV.getText().toString().trim() + "','"
                + txtPVenta.getText().toString().trim() + "','"
                + txtImporteV.getText().toString().trim() + "');");
        showMessage("Exito!", "Venta agregado");
        clearText();
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
    }
}
