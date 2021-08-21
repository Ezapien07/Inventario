package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDF_activity extends AppCompatActivity {

    String NOMBRE_DIRECTORIO = "MisPDFs";
    String NOMBRE_DOCUMENTO = "ReporteProductos.pdf";
    String NOMBRE_DOCUMENTO1 = "ReporteClientes.pdf";
    String NOMBRE_DOCUMENTO2 = "ReporteVendedores.pdf";
    String NOMBRE_DOCUMENTO3 = "ReporteProveedores.pdf";
    String NOMBRE_DOCUMENTO4= "ReporteCompras.pdf";
    String NOMBRE_DOCUMENTO5= "ReporteVenta.pdf";
    String NOMBRE_DOCUMENTO6="ReporteVentas7.pdf";
    String NOMBRE_DOCUMENTO7="ReporteVentaDia.pdf";
    SQLiteDatabase db;
    Button btnRVendedores,btnRProductos,btnRClientes;
    Button btnReportProveedores;
    Button btnReportCompras;
    Button btnReportVentas, btnVentasRango1,btnVentasDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        //Creacion de la base de datos
        db=openOrCreateDatabase("Inventario", Context.MODE_PRIVATE,null);

        btnRVendedores=(Button)findViewById(R.id.btnReportVendedores);
        btnReportProveedores=(Button)findViewById(R.id.btnReportVendedores);
        btnReportCompras=(Button)findViewById(R.id.btnReportCompra);
        btnReportVentas=(Button)findViewById(R.id.btnReportVentas);
        btnVentasRango1=(Button)findViewById(R.id.btnVentasRango1);
        btnVentasDia=(Button)findViewById(R.id.btnReportVentaDia);
        btnRProductos=(Button)findViewById(R.id.btnRProductos);
        btnRClientes=(Button)findViewById(R.id.btnRClientes);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    1000);
        }
        btnRVendedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFVendores();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Vendedores", Toast.LENGTH_LONG).show();
            }
        });
        btnReportProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFProveedores();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Vendedores", Toast.LENGTH_LONG).show();
            }
        });
        btnReportCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFCompras();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Compras", Toast.LENGTH_LONG).show();
            }
        });
        btnReportVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFVentas();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Ventas", Toast.LENGTH_LONG).show();
            }
        });
        btnVentasRango1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFVentasFecha();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Ventas de 7 dias", Toast.LENGTH_LONG).show();
            }
        });
        btnVentasDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFVentaDia();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Ventas del Dia", Toast.LENGTH_LONG).show();
            }
        });
        btnRProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFProductos();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Productos", Toast.LENGTH_LONG).show();
            }
        });
        btnRClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDFClientes();
                Toast.makeText(PDF_activity.this, "Se Creo el Reporte de Clientes", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void crearPDFClientes() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO1);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM clientes", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE COMPRAS \n\n"));
//db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
//
            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("CLAVE COMPRA");
            tabla.addCell("NO PROVEEDOR");
            tabla.addCell("NOMBRE PROVEEDOR");
            tabla.addCell("CALLE PROVEEDOR");
            tabla.addCell("FECHA");
            tabla.addCell("F/R");
            tabla.addCell("TOTAL");
            tabla.addCell("TOTAL PARES");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    private void crearPDFProductos() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM producto", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE PRODUCTOS \n\n"));
//db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
//
            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("CLAVE PRODUCTOS");
            tabla.addCell("NOMBEW");
            tabla.addCell("LINEA");
            tabla.addCell("EXISTENCIA");
            tabla.addCell("COSTO");
            tabla.addCell("COSTO PROMEDIO");
            tabla.addCell("VENTA MENUDEO");
            tabla.addCell("VENTA MAYOREO");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    private void crearPDFVentaDia() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO5);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM ventas", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE COMPRAS \n\n"));
//db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
//
            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("CLAVE COMPRA");
            tabla.addCell("NO PROVEEDOR");
            tabla.addCell("NOMBRE PROVEEDOR");
            tabla.addCell("CALLE PROVEEDOR");
            tabla.addCell("FECHA");
            tabla.addCell("F/R");
            tabla.addCell("TOTAL");
            tabla.addCell("TOTAL PARES");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    private void crearPDFVentasFecha() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO5);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM ventas", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE COMPRAS \n\n"));
//db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
//
            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("CLAVE COMPRA");
            tabla.addCell("NO PROVEEDOR");
            tabla.addCell("NOMBRE PROVEEDOR");
            tabla.addCell("CALLE PROVEEDOR");
            tabla.addCell("FECHA");
            tabla.addCell("F/R");
            tabla.addCell("TOTAL");
            tabla.addCell("TOTAL PARES");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    private void crearPDFVentas() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO5);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM ventas", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE COMPRAS \n\n"));
//db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
//
            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("CLAVE COMPRA");
            tabla.addCell("NO PROVEEDOR");
            tabla.addCell("NOMBRE PROVEEDOR");
            tabla.addCell("CALLE PROVEEDOR");
            tabla.addCell("FECHA");
            tabla.addCell("F/R");
            tabla.addCell("TOTAL");
            tabla.addCell("TOTAL PARES");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    private void crearPDFCompras() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO4);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM compras", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE COMPRAS \n\n"));
//db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
//
            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("CLAVE COMPRA");
            tabla.addCell("NO PROVEEDOR");
            tabla.addCell("NOMBRE PROVEEDOR");
            tabla.addCell("CALLE PROVEEDOR");
            tabla.addCell("FECHA");
            tabla.addCell("F/R");
            tabla.addCell("TOTAL");
            tabla.addCell("TOTAL PARES");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    private void crearPDFProveedores() {
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO3);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM proveedor", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE PROVEEDORES \n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("NO PROVEEDORES");
            tabla.addCell("NOMBRE");
            tabla.addCell("CALLE");
            tabla.addCell("COLONIA");
            tabla.addCell("CIUDAD");
            tabla.addCell("RFC");
            tabla.addCell("TELEFONO");
            tabla.addCell("EMAIL");
            tabla.addCell("SALDO");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
                tabla.addCell(c.getString(7));
                tabla.addCell(c.getString(8));

            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    public void crearPDFVendores(){
        Document documento = new Document();
        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            //AGREGACION DE LA BASE DE DATOS
            Cursor c=db.rawQuery("SELECT * FROM vendedores", null);

            documento.open();
            documento.add(new Paragraph("REPORTE DE Vendedores \n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(8);
            tabla.addCell("NO VENDEDORES");
            tabla.addCell("NOMBRE");
            tabla.addCell("CALLE");
            tabla.addCell("COLONIA");
            tabla.addCell("TELEFONO");
            tabla.addCell("EMAIL");
            tabla.addCell("COMISION");
            while (c.moveToNext()) {
                tabla.addCell(c.getString(0));
                tabla.addCell(c.getString(1));
                tabla.addCell(c.getString(2));
                tabla.addCell(c.getString(3));
                tabla.addCell(c.getString(4));
                tabla.addCell(c.getString(5));
                tabla.addCell(c.getString(6));
            }
            documento.add(tabla);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    public File crearFichero(String nombreFichero) {
        File ruta = getRuta();

        File fichero = null;
        if(ruta != null) {
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }
    public File getRuta() {
        File ruta = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), NOMBRE_DIRECTORIO);
            if(ruta != null) {
                if(!ruta.mkdirs()) {
                    if(!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }
}