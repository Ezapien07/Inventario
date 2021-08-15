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
    }

    public void abrirClientes() {
        Intent iclientes = new Intent(this, Clientes_activity.class);
        startActivity(iclientes);
    }

    public void abrirProductos() {
        Intent iproductos = new Intent(this, Productos_activity.class);
        startActivity(iproductos);
    }
}