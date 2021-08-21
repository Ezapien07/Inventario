package com.example.inventario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    Button btnReportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClientes = (Button) findViewById(R.id.btnClientes);
        Button btnProductos = (Button) findViewById(R.id.btnProductos);

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirClientes();
            }
        });


        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirProductos();
            }
        });
        Button btnProveedor=(Button)findViewById(R.id.btnProveedores);
        btnProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirProveedor();
            }
        });
        Button btnVendedores=(Button)findViewById(R.id.btnVendedores);
        btnVendedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVendedores();
            }
        });

        Button btnCompras=(Button)findViewById(R.id.btnCompras);
        btnCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCompras();
            }
        });
        btnReportes=(Button)findViewById(R.id.btnReportes);
        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirReportes();
            }
        });

    }

    private void abrirReportes() {
        Intent ireport=new Intent(this,PDF_activity.class);
        startActivity(ireport);
    }

    private void abrirCompras() {
        Intent icompras= new Intent(this ,Compras_activity.class);
        startActivity(icompras);
    }

    public void abrirClientes() {
        Intent iclientes = new Intent(this, Clientes_activity.class);
        startActivity(iclientes);
    }


    public void abrirProductos() {
        Intent iproductos = new Intent(this, Productos_activity.class);
        startActivity(iproductos);
    }

    public void abrirProveedor(){
        Intent iproveedores=new Intent(this, Proveedores_activity.class);
        startActivity(iproveedores);
    }
    public void abrirVendedores(){
        Intent ivendedores=new Intent(this,Vendedores_activity.class);
        startActivity(ivendedores);

    }
}