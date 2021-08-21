package com.example.inventario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

import java.util.ArrayList;

public class Compras_activity extends AppCompatActivity {

    EditText edtNoCompra,edtFechaCompra,edtNombrePCompra,edtCallePCompra,edtFRCompra,edtCantidadProCom,edtSumaCompra,edtTotalParesCompra,edtIvaCompra,edtTotalCompra;
    Button btnAgregarCompra,btnModificarCompra,btnBuscarCompra,btnBajaCompra,btnReportesCompra,btnAPCompra;
    SQLiteDatabase db;
    Spinner spnNoProveedor;
    ArrayList<String> proveedores=new ArrayList<>();
    Spinner spProductos;
    ArrayList<String> productos=new ArrayList<>();
    ArrayList<String> claveProductos=new ArrayList<>();
    ArrayList<String> detalle=new ArrayList<String>();
    ArrayList<String> elemtosProductos=new ArrayList<>();
    int clavePro=0;
    String claveProduc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        edtNoCompra=(EditText)findViewById(R.id.edtNoCompra);
        edtFechaCompra=(EditText)findViewById(R.id.edtFechaCompra);
        edtNombrePCompra=(EditText)findViewById(R.id.edtNombrePCompra);
        edtCallePCompra=(EditText)findViewById(R.id.edtCallePCompra);
        edtFRCompra=(EditText)findViewById(R.id.edtFRCompra);
        edtCantidadProCom=(EditText)findViewById(R.id.edtCantidadProCom);
        edtSumaCompra=(EditText)findViewById(R.id.edtSumaCompra);
        edtTotalParesCompra=(EditText)findViewById(R.id.edtTotalParesCompra);
        edtIvaCompra=(EditText)findViewById(R.id.edtIvaCompra);
        edtTotalCompra=(EditText)findViewById(R.id.edtTotalCompra);

        btnAgregarCompra=(Button)findViewById(R.id.btnAgregarCompra);
        btnModificarCompra=(Button)findViewById(R.id.btnModificarCompra);
        btnBuscarCompra=(Button)findViewById(R.id.btnBuscarCompra);
        btnBajaCompra=(Button)findViewById(R.id.btnBajaCompra);
        btnReportesCompra=(Button)findViewById(R.id.btnReporteCompra);

        spnNoProveedor=(Spinner)findViewById(R.id.spnNoProveedor);
        spProductos=(Spinner)findViewById(R.id.spProductos);

        db=openOrCreateDatabase("Inventario", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS compras(claveCompra VARCHAR primary key,noProveedor VARCHAR,nombreProveedor VARCHAR,calleP VARCHAR,fechaCompra VARCHAR,F_R VARCHAR,total VARCHAR,iva VARCHAR,totalPares VARCHAR,foreign key (noProveedor) references proveedor(noProveedor));");
        db.execSQL("CREATE TABLE IF NOT EXISTS detallecompra(claveDetalle INTEGER primary key autoincrement,claveCompra VARCHAR,claveProducto VARCHAR, cantidad VARCHAR," +
                "foreign key (claveCompra)references compras(claveCompra), foreign key (claveProducto) references producto(clave_p));");
        //Prodcutos
        //db.execSQL("CREATE TABLE IF NOT EXISTS producto(clave_p VARCHAR primary key,nombre_p VARCHAR , linea VARCHAR,existencias VARCHAR, costo VARCHAR,promedio VARCHAR, pventa1 VARCHAR, pventa2 VARCHAR);");


        Cursor c=db.rawQuery("SELECT noProveedor,nombre FROM proveedor", null);
        while(c.moveToNext())
        {
            String idPro=c.getString(0);
            String proveedornombre= c.getString(1);
            proveedores.add(idPro);
        }
        Cursor cp=db.rawQuery("SELECT clave_p,nombre_p FROM producto",null);
        while(cp.moveToNext()){
            String clave=cp.getString(0);
            String cadena=clave+".- "+cp.getString(1);
            claveProductos.add(clave);
            productos.add(cadena);
        }

        spnNoProveedor.setAdapter(new ArrayAdapter<String>(Compras_activity.this, android.R.layout.simple_spinner_dropdown_item,proveedores));
        spProductos.setAdapter(new ArrayAdapter<String>(Compras_activity.this, android.R.layout.simple_spinner_dropdown_item,productos));

        spnNoProveedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int itemSeleccionado, long l) {
                proveedores.toString();
                if(itemSeleccionado==0) {
                    Cursor c2 = db.rawQuery("SELECT nombre,calle from proveedor WHERE noproveedor=1", null);
                    if (c2.moveToFirst()) {
                        edtNombrePCompra.setText(c2.getString(0));
                        edtCallePCompra.setText(c2.getString(1));
                        clavePro=1;
                    }
                }
                else if(itemSeleccionado==1){
                    Cursor c2 = db.rawQuery("SELECT nombre,calle from proveedor WHERE noproveedor=2", null);
                    if (c2.moveToFirst()) {
                        edtNombrePCompra.setText(c2.getString(0));
                        edtCallePCompra.setText(c2.getString(1));
                        clavePro=2;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int itemSeleccionado, long l) {
                Tabla tabla = new Tabla(Compras_activity.this, (TableLayout) findViewById(R.id.tablaCompra));

                float totalPrecio=0;
                float total=0;
                float iva=0;
                int pares = 0;
                float subTotal = 0;
                String cantidad="";
                if(itemSeleccionado == 0) {
                    System.out.println("Entro aqui..");
                    Cursor c3 = db.rawQuery("SELECT clave_p,nombre_p,linea,costo from producto WHERE clave_p='" + claveProductos.get(itemSeleccionado)+ "'", null);
                    if (c3.moveToFirst()) {
                        if(edtCantidadProCom.getText().toString().trim().length()== 0) {
                            cantidad="1";
                        }
                        else {
                            cantidad = edtCantidadProCom.getText().toString();
                        }
                        claveProduc+=c3.getString(0)+",";
                        elemtosProductos.add(claveProduc);
                            elemtosProductos.add(c3.getString(1));
                            elemtosProductos.add("PAR");
                            elemtosProductos.add(c3.getString(2));
                            float precioCosto=Float.parseFloat(c3.getString(3));
                            totalPrecio = Integer.parseInt(cantidad) * precioCosto;
                            elemtosProductos.add(edtCantidadProCom.getText().toString());
                            elemtosProductos.add(String.valueOf(precioCosto));
                            elemtosProductos.add(String.valueOf(totalPrecio));
                            elemtosProductos.add("Eliminar");
                            tabla.agregarFilaTabla(elemtosProductos);
                        System.out.println("Array d eproductos"+"\n"+elemtosProductos.toString());
                        detalle.add(String.valueOf(elemtosProductos));
                            elemtosProductos.clear();
                            subTotal +=totalPrecio;
                            pares +=Integer.parseInt(cantidad);


                            showMessage("Producto Agregado", "El producto se agrego correctamente");
                            iva+= (float) (subTotal*0.16);
                            edtSumaCompra.setText(String.valueOf(subTotal));
                            edtIvaCompra.setText(String.valueOf(iva));
                            edtTotalParesCompra.setText(String.valueOf(pares));
                            total+=subTotal+iva;
                            edtTotalCompra.setText(String.valueOf(total));


                        }
                }
                else if(itemSeleccionado==1){
                    Cursor c2 = db.rawQuery("SELECT clave_p,nombre_p,linea,costo from producto WHERE clave_p='" + claveProductos.get(itemSeleccionado)+ "'", null);
                    if (c2.moveToFirst()) {
                        if(edtCantidadProCom.getText().toString().trim().length()== 0) {
                            cantidad="1";
                        }
                        else {
                            cantidad = edtCantidadProCom.getText().toString();
                        }
                        claveProduc+=c2.getString(0)+",";
                        elemtosProductos.add(claveProduc);
                            elemtosProductos.add(c2.getString(1));
                            elemtosProductos.add("PAR");
                            elemtosProductos.add(c2.getString(2));
                            float precioCosto = Float.parseFloat(c2.getString(3));
                            totalPrecio = Float.parseFloat(edtCantidadProCom.getText().toString()) * precioCosto;
                            elemtosProductos.add(edtCantidadProCom.getText().toString());
                            elemtosProductos.add(String.valueOf(precioCosto));
                            elemtosProductos.add(String.valueOf(totalPrecio));
                            elemtosProductos.add("Eliminar");
                            tabla.agregarFilaTabla(elemtosProductos);
                        System.out.println("Array d eproductos"+"\n"+elemtosProductos.toString());
                        detalle.add(String.valueOf(elemtosProductos));
                            elemtosProductos.clear();
                            subTotal += totalPrecio;
                            pares += Integer.parseInt(cantidad);

                            showMessage("Producto Agregado", "El producto se agrego correctamente");
                            iva += (float) (subTotal * 0.16);
                            edtSumaCompra.setText(String.valueOf(subTotal));
                            edtIvaCompra.setText(String.valueOf(iva));
                            edtTotalParesCompra.setText(String.valueOf(pares));
                            total += subTotal + iva;
                            edtTotalCompra.setText(String.valueOf( total));

                    }
                }
                else if(itemSeleccionado==2){
                    Cursor c2 = db.rawQuery("SELECT clave_p,nombre_p,linea,costo from producto WHERE clave_p='" + claveProductos.get(itemSeleccionado)+ "'", null);
                    if (c2.moveToFirst()) {
                        if (edtCantidadProCom.getText().toString().trim().length() == 0) {
                            cantidad = "1";
                        } else {
                            cantidad = edtCantidadProCom.getText().toString();
                        }
                        claveProduc+=c2.getString(0)+",";
                        elemtosProductos.add(claveProduc);
                            elemtosProductos.add(c2.getString(1));
                            elemtosProductos.add("PAR");
                            elemtosProductos.add(c2.getString(2));
                            float precioCosto = Float.parseFloat(c2.getString(3));
                            totalPrecio = Float.parseFloat(edtCantidadProCom.getText().toString()) * precioCosto;
                            elemtosProductos.add(edtCantidadProCom.getText().toString());
                            elemtosProductos.add(String.valueOf(precioCosto));
                            elemtosProductos.add(String.valueOf(totalPrecio));
                            elemtosProductos.add("Eliminar");
                            tabla.agregarFilaTabla(elemtosProductos);
                        System.out.println("Array d eproductos"+"\n"+elemtosProductos.toString());
                            detalle.add(String.valueOf(elemtosProductos));
                            elemtosProductos.clear();
                            subTotal += totalPrecio;
                            pares += Integer.parseInt(cantidad);
                            showMessage("Producto Agregado", "El producto se agrego correctamente");
                            iva += (float) (subTotal * 0.16);
                            edtSumaCompra.setText(String.valueOf(subTotal));
                            edtIvaCompra.setText(String.valueOf(iva));
                            edtTotalParesCompra.setText(String.valueOf(pares));
                            total += subTotal + iva;
                            edtTotalCompra.setText(String.valueOf(total));

                    }
                }
                else if(itemSeleccionado==3){
                    Cursor c2 = db.rawQuery("SELECT clave_p,nombre_p,linea,costo from producto WHERE clave_p='" + claveProductos.get(itemSeleccionado)+ "'", null);
                    if (c2.moveToFirst()) {
                        if (edtCantidadProCom.getText().toString().trim().length() == 0) {
                            cantidad = "1";
                        } else {
                            cantidad = edtCantidadProCom.getText().toString();
                        }
                        claveProduc+=c2.getString(0)+",";
                        elemtosProductos.add(claveProduc);
                            elemtosProductos.add(c2.getString(1));
                            elemtosProductos.add("PAR");
                            elemtosProductos.add(c2.getString(2));
                            float precioCosto = Float.parseFloat(c2.getString(3));
                            totalPrecio = Float.parseFloat(cantidad) * precioCosto;
                            elemtosProductos.add(edtCantidadProCom.getText().toString());
                            elemtosProductos.add(String.valueOf(precioCosto));
                            elemtosProductos.add(String.valueOf(totalPrecio));
                            elemtosProductos.add("Eliminar");
                            tabla.agregarFilaTabla(elemtosProductos);
                        detalle.add(String.valueOf(elemtosProductos));
                            elemtosProductos.clear();
                            subTotal += totalPrecio;
                            pares += Integer.parseInt(cantidad);
                            showMessage("Producto Agregado", "El producto se agrego correctamente");
                            iva += (float) (subTotal * 0.16);
                            edtSumaCompra.setText(String.valueOf(subTotal));
                            edtIvaCompra.setText(String.valueOf(iva));
                            edtTotalParesCompra.setText(String.valueOf(pares));
                            total += subTotal + iva;
                            edtTotalCompra.setText(String.valueOf(total));
                        }

                }
                else if(itemSeleccionado==4) {
                    Cursor c2 = db.rawQuery("SELECT clave_p,nombre_p,linea,costo from producto WHERE clave_p='" + claveProductos.get(itemSeleccionado) + "'", null);
                    if (c2.moveToFirst()) {
                        if (edtCantidadProCom.getText().toString().trim().length() == 0) {
                            cantidad = "1";
                        } else {
                            cantidad = edtCantidadProCom.getText().toString();
                        }
                        claveProduc+=c2.getString(0)+",";
                        elemtosProductos.add(claveProduc);
                            elemtosProductos.add(c2.getString(1));
                            elemtosProductos.add("PAR");
                            elemtosProductos.add(c2.getString(2));
                            float precioCosto = Float.parseFloat(c2.getString(3));
                            totalPrecio = Float.parseFloat(cantidad) * precioCosto;
                            elemtosProductos.add(edtCantidadProCom.getText().toString());
                            elemtosProductos.add(String.valueOf(precioCosto));
                            elemtosProductos.add(String.valueOf(totalPrecio));
                            elemtosProductos.add("Eliminar");
                            tabla.agregarFilaTabla(elemtosProductos);
                        System.out.println("Array d eproductos"+"\n"+elemtosProductos.toString());
                        detalle.add(String.valueOf(elemtosProductos));
                            elemtosProductos.clear();
                            subTotal += totalPrecio;
                            pares += Integer.parseInt(cantidad);
                            showMessage("Producto Agregado", "El producto se agrego correctamente");
                            iva += (float) (subTotal * 0.16);
                            edtSumaCompra.setText(String.valueOf(subTotal));
                            edtIvaCompra.setText(String.valueOf(iva));
                            edtTotalParesCompra.setText(String.valueOf(pares));
                            total += subTotal + iva;
                            edtTotalCompra.setText(String.valueOf(total));

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tablaCompra));
        tabla.agregarCabecera(R.array.cabecera_tabla);


        btnAgregarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCompra();
            }
        });
        btnBajaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bajaCompra();
            }
        });
        btnModificarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarCompra();
            }
        });
        btnBuscarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCompra();
            }
        });
        btnReportesCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirReportesCompra();
            }
        });

    }


    public void agregarCompra(){
        if(edtNoCompra.getText().toString().trim().length()==0 ||
        edtFechaCompra.getText().toString().trim().length()==0 ||
        edtNombrePCompra.getText().toString().trim().length()==0 ||
        edtFRCompra.getText().toString().trim().length()==0){
            if(detalle.size()==1){
                showMessage("Error!", "Porfavor introduce todos los valores");
                return;
            }
        }
        else {
            System.out.println("D E T A L L E ");
            //System.out.println(detalle.get(1).toString());
            String detalle1=detalle.get(1).toString();
            /*String detalle1 =detalle.get(0);
            */
            String[] parts = detalle1.split(", ");
            String clave = parts[0];
            String nombre = parts[1];
            String unidad=parts[2];
            String linea=parts[3];
            String cantidad =parts[4];
            String precio=parts[5];
            String total=parts[6];
            cantidad= cantidad.replaceAll("\\s+","");
            String[] clavesProductos=claveProduc.split(",");
            String cantidad1="";


            System.out.println(" datos: "+claveProduc+","+nombre+", "+linea+", "+precio+", "+total+".");
            System.out.println("D E T A L L E ");
            String  claveP=String.valueOf(clavePro);
            /*
            detallecompra(claveDetalle INTEGER primary key autoincrement,claveCompra VARCHAR,claveProducto VARCHAR, cantidad VARCHAR
             */

            db.execSQL("INSERT INTO compras VALUES('" + edtNoCompra.getText() + "','"
                    + claveP + "','"
                    + edtNombrePCompra.getText() + "','"
                    + edtCallePCompra.getText()  + "','"
                    + edtFechaCompra.getText() + "','"
                    + edtFRCompra.getText() + "','"
                    + edtTotalCompra.getText() + "','"
                    +edtIvaCompra.getText()+"','"
                    + edtTotalParesCompra.getText() + "');");

           /* for (int i=0;i<=detalle.size();i++){
                db.execSQL("INSERT INTO detallecompra(claveCompra,claveProducto,cantidad) VALUES('"+ edtNoCompra.getText() + "','"
                        + clavesProductos[i] + "','"
                        + cantidad +  "');");
                Cursor c=db.rawQuery("SELECT existencias FROM producto WHERE clave_p ='" + clavesProductos[i]+ "'", null);
                if(c.moveToFirst())
                {
                    cantidad1=c.getString(0);
                }
                cantidad1= cantidad1.replaceAll("\\s+","");
                //int cantidadtotal=Integer.parseInt(cantidad)+Integer.parseInt(cantidad1);
                int cant1=Integer.parseInt(cantidad);
                int cant2=Integer.parseInt(cantidad1);
                int catnidadTotal=cant1+cant2;
                String newexistencia=String.valueOf(catnidadTotal);
                db.execSQL("UPDATE producto SET existencias = '" + newexistencia + "' WHERE clave_p ='" + clavesProductos[i]+ "'");
            }*/
            clearText();
            showMessage("Compra Agregada","La compra se agrego correctamente");

        }

    }

    public void bajaCompra(){
        if (edtNoCompra.getText().toString().trim().length() == 0) {
            showMessage("Error!", "Introduce Clave");
            return;
        }
        else{
            db.execSQL("DELETE FROM compras WHERE clave_p = " + edtNoCompra.getText() + ";");
            showMessage("Exito!", "Compra eliminado");
            clearText();
        }

    }
    public void modificarCompra(){

    }
    private void abrirReportesCompra() {

    }
    public void buscarCompra(){
        if (edtNoCompra.getText().toString().trim().length() == 0) {
            showMessage("Error!", "Introduce Clave");
            return;
        }
        else{
            Cursor c=db.rawQuery("SELECT * FROM compras WHERE noProveedor='"+edtNoCompra.getText()+"'", null);
            if(c.moveToFirst())
            {
                edtNoCompra.setText(c.getString(0));
                String noP=c.getString(1);
                edtNombrePCompra.setText(c.getString(2));
                edtCallePCompra.setText(c.getString(3));
                edtFechaCompra.setText(c.getString(4));
                edtFRCompra.setText(c.getString(5));
                edtSumaCompra.setText(c.getString(6));
                edtIvaCompra.setText(c.getString(7));
                edtTotalCompra.setText(c.getString(8));
            }
        }

    }


    public void showMessage(String title,String message) {
        androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
       /* edtClave.setText("");
        edtNombre.setText("");
        edtLinea.setText("");
        edtExistencias.setText("");
        edtCosto.setText("");
        edtPromedio.setText("");
        edtVenta1.setText("");
        edtVenta2.setText("");*/
    }

}