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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClientes = (Button)findViewById(R.id.btnClientes);

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirClientes();
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


    }
    public void abrirClientes(){
        Intent iclientes= new Intent(this,Clientes_activity.class);
        startActivity(iclientes);
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