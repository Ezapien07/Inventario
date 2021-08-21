package com.example.inventario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Proveedores_activity extends AppCompatActivity {

    EditText edtNoProveedor,edtNombreProveedor,edtCalleProveedor,edtColoniaProveedor,edtCiudadProveedor,edtRFCProveedor,edtTelefonoProveedor,edtEmailProveedor,edtSaldoProveedor;
    Button btnAgregarProveedor,btnModificarProveedor,btnBuscarProveedor,btnBajaProveedor,btnConsultaProveedor;
    SQLiteDatabase db;
    boolean flag=false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedores);


        edtNoProveedor=(EditText)findViewById(R.id.edtNoProveedores);
        edtNombreProveedor=(EditText)findViewById(R.id.edtNombreProveedores);
        edtCalleProveedor=(EditText)findViewById(R.id.edtCalleProveedor);
        edtColoniaProveedor=(EditText)findViewById(R.id.edtColoniaProveedores);
        edtCiudadProveedor=(EditText)findViewById(R.id.edtCiudadProveedor);
        edtRFCProveedor=(EditText)findViewById(R.id.edtRFCProveedor);
        edtTelefonoProveedor=(EditText)findViewById(R.id.edtTelefonoProveedor);
        edtEmailProveedor=(EditText)findViewById(R.id.edtEmailProveedor);
        edtSaldoProveedor=(EditText)findViewById(R.id.edtTelefonoCliente);

        btnAgregarProveedor=(Button)findViewById(R.id.btnAgregarCompra);
        btnModificarProveedor=(Button)findViewById(R.id.btnModificarCompra);
        btnBuscarProveedor=(Button)findViewById(R.id.btnBuscarCompra);
        btnBajaProveedor=(Button)findViewById(R.id.btnBajaCompra);
        btnConsultaProveedor=(Button)findViewById(R.id.btnReporteCompra);


        db=openOrCreateDatabase("Inventario", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS proveedor(noProveedor VARCHAR,nombre VARCHAR,calle VARCHAR,colonia VARCHAR,ciudad VARCHAR,rfc VARCHAR,telefono VARCHAR,email VARCHAR,saldo VARCHAR);");

        btnAgregarProveedor.setOnClickListener(v -> {agregarProveedor();});
        btnModificarProveedor.setOnClickListener(v -> {modificarProveedor();});
        btnBajaProveedor.setOnClickListener(v -> {bajaProveedor();});
        btnBuscarProveedor.setOnClickListener(v -> {buscarProveedor();});
        btnConsultaProveedor.setOnClickListener(v -> {consultaProveedores();});
    }

    public void agregarProveedor(){
        flag=validarCampos();
        if(flag==true){
            db.execSQL("INSERT INTO proveedor VALUES('" + edtNoProveedor.getText() + "','"
                    + edtNombreProveedor.getText() + "','"
                    + edtCalleProveedor.getText() + "','"
                    + edtColoniaProveedor.getText() + "','"
                    + edtCiudadProveedor.getText() + "','"
                    + edtRFCProveedor.getText() + "','"
                    + edtTelefonoProveedor.getText() + "','"
                    + edtEmailProveedor.getText() + "','"
                    + edtSaldoProveedor.getText() + "');");
            showMessage("Exito!", "Proveedor agregado");
            clearText();
        }else{
            showMessage("Error!", "Porfavor introduce todos los valores");
            return;
        }
    }

    public void modificarProveedor(){
        flag=validarID();

        if(flag==true) {
            boolean flag2=validarCampos();
            if (flag2 == true) {
                String noV, nomV, calleV, colV,ciudadP,rfcP, telV, emailV, saldoP;
                noV = edtNoProveedor.getText().toString();
                nomV = edtNombreProveedor.getText().toString();
                calleV = edtCalleProveedor.getText().toString();
                colV = edtColoniaProveedor.getText().toString();
                ciudadP=edtCiudadProveedor.getText().toString();
                rfcP=edtRFCProveedor.getText().toString();
                telV = edtTelefonoProveedor.getText().toString();
                emailV = edtEmailProveedor.getText().toString();
                saldoP=edtSaldoProveedor.getText().toString();
                try {
                    ContentValues cv = new ContentValues();
                    db.execSQL("UPDATE proveedor SET nombre='" + nomV + "',calle='" +calleV + "',colonia ='" +colV + "',ciudad='"+ciudadP+"',rfc='"+rfcP+"',telefono='" +telV + "',email='" +emailV + "'," +
                            "saldo='" +saldoP+ "' WHERE noProveedor='" + noV + "'");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                showMessage("Exito!", "Proveedor modificado");
                clearText();
            } else {
                showMessage("Error!", "Porfavor introduce todos los valores");
                return;
            }
        }else{
            showMessage("Error!","Por favor introduce el campo No");
        }
    }

    public void bajaProveedor(){
        if(edtNoProveedor.getText().toString().trim().length()==0)
        {
            showMessage("Error!", "Introduce el Numero");
            return;
        }
        db.execSQL("DELETE FROM proveedor where noProveedor='"+edtNoProveedor.getText()+"'");
        showMessage("Exito!", "Proveedor eliminado");
        clearText();
    }

    public void buscarProveedor(){
        if(edtNoProveedor.getText().toString().trim().length()==0)
        {
            showMessage("Error!", "Introduce el No. del Proveedor");
            return;
        }
        Cursor c=db.rawQuery("SELECT * FROM proveedor WHERE noProveedor='"+edtNoProveedor.getText()+"'", null);
        if(c.moveToFirst())
        {
            edtNoProveedor.setText(c.getString(0));
            edtNombreProveedor.setText(c.getString(1));
            edtCalleProveedor.setText(c.getString(2));
            edtColoniaProveedor.setText(c.getString(3));
            edtCiudadProveedor.setText(c.getString(4));
            edtRFCProveedor.setText(c.getString(5));
            edtTelefonoProveedor.setText(c.getString(6));
            edtEmailProveedor.setText(c.getString(7));
            edtSaldoProveedor.setText(c.getString(8));
        }
        else
        {
            showMessage("Error!", "No.   no valida");
            clearText();
        }
    }
    public void consultaProveedores(){
        Cursor c=db.rawQuery("SELECT * FROM proveedor", null);
        if(c.getCount()==0)
        {
            showMessage("Error!", "No se encontraron Proveedores");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("No       : " + c.getString(0)+"\n");
            buffer.append("Nombre       : " + c.getString(1)+"\n");
            buffer.append("Calle        : " + c.getString(2)+"\n");
            buffer.append("Colonia      :" + c.getString(3)+"\n");
            buffer.append("Ciudad       : "+c.getString(4)+"\n");
            buffer.append("RFC          : "+c.getString(5)+"\n");
            buffer.append("Telefono     : " + c.getString(6)+"\n");
            buffer.append("Email        : " + c.getString(7)+"\n");
            buffer.append("Saldo     : " + c.getString(8)+"\n\n");
        }
        showMessage("Proveedores", buffer.toString());
    }

    public boolean validarCampos(){
        if(edtNoProveedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if(edtNoProveedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if (edtCalleProveedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if (edtColoniaProveedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if(edtCiudadProveedor.getText().toString().trim().length()==0){
            return false;
        }
        else if(edtRFCProveedor.getText().toString().trim().length()==0){
            return false;
        }
        else if(edtTelefonoProveedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if(edtEmailProveedor.getText().toString().trim().length()== 0){
            return  false;
        }
        else if(edtSaldoProveedor.getText().toString().trim().length()== 0){
            return  false;
        }
        else{
            return true;
        }
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        edtNoProveedor.setText("");
        edtNombreProveedor.setText("");
        edtCalleProveedor.setText("");
        edtColoniaProveedor.setText("");
        edtCiudadProveedor.setText("");
        edtRFCProveedor.setText("");
        edtTelefonoProveedor.setText("");
        edtEmailProveedor.setText("");
        edtSaldoProveedor.setText("");
        edtNoProveedor.requestFocus();
    }

    public boolean validarID(){
        if(edtNoProveedor.getText().toString().trim().length()==0)
        {
            showMessage("Error!", "Introduce el No");
            return false;
        }
        else{
            return true;
        }
    }
}