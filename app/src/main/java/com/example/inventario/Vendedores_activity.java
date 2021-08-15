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

public class Vendedores_activity extends AppCompatActivity {

    EditText edtNoVendedor,edtNombreVendedor,edtCalleVendedor,edtColoniaVendedor,edtTelefonoVendedor,edtEmailVendedor,edtComision;
    Button btnAgregarVendedor,btnModificarVendedor,btnBuscarVendedor,btnBajaVendedor,btnConsultaVendedor;
    SQLiteDatabase db;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedores);


        edtNoVendedor=(EditText)findViewById(R.id.edtNoVendedores);
        edtNombreVendedor=(EditText)findViewById(R.id.edtNombreVendedores);
        edtCalleVendedor=(EditText)findViewById(R.id.edtCalleVendedor);
        edtColoniaVendedor=(EditText)findViewById(R.id.edtColoniaVendedores);
        edtTelefonoVendedor=(EditText)findViewById(R.id.edtTelefonoVendedor);
        edtEmailVendedor=(EditText)findViewById(R.id.edtEmailVendedor);
        edtComision=(EditText)findViewById(R.id.edtSaldoProveedor);

        btnAgregarVendedor=(Button)findViewById(R.id.btnAgregarVendedor);
        btnModificarVendedor=(Button)findViewById(R.id.btnModificarVendedor);
        btnBuscarVendedor=(Button)findViewById(R.id.btnBuscarVendedor);
        btnBajaVendedor=(Button)findViewById(R.id.btnBajaVendedor);
        btnConsultaVendedor=(Button)findViewById(R.id.btnConsultaVendedor);


        db=openOrCreateDatabase("Inventario", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS vendedores(noVendedor VARCHAR,nombre VARCHAR,calle VARCHAR,colonia VARCHAR,telefono VARCHAR,email VARCHAR,comision VARCHAR);");

        btnAgregarVendedor.setOnClickListener(v -> {agregarVendedor();});
        btnModificarVendedor.setOnClickListener(v -> {modificarVendedor();});
        btnBajaVendedor.setOnClickListener(v -> {bajaVendedor();});
        btnBuscarVendedor.setOnClickListener(v -> {buscarVendedor();});
        btnConsultaVendedor.setOnClickListener(v -> {consultaVendedores();});
    }

    public void agregarVendedor(){
        flag=validarCampos();
        if(flag==true){
            db.execSQL("INSERT INTO vendedores VALUES('" + edtNoVendedor.getText() + "','"
                    + edtNombreVendedor.getText() + "','"
                    + edtCalleVendedor.getText() + "','"
                    + edtColoniaVendedor.getText() + "','"
                    + edtTelefonoVendedor.getText() + "','"
                    + edtEmailVendedor.getText() + "','"
                    + edtComision.getText() + "');");
            showMessage("Exito!", "Proveedor agregado");
            clearText();
        }else{
            showMessage("Error!", "Porfavor introduce todos los valores");
            return;
        }
    }

    public void modificarVendedor(){
        flag=validarID();

        if(flag==true) {
            boolean flag2=validarCampos();
            if (flag2 == true) {
                String noV, nomV, calleV, colV, telV, emailV, comV;
                noV = edtNoVendedor.getText().toString();
                nomV = edtNombreVendedor.getText().toString();
                calleV = edtCalleVendedor.getText().toString();
                colV = edtColoniaVendedor.getText().toString();
                telV = edtTelefonoVendedor.getText().toString();
                emailV = edtEmailVendedor.getText().toString();
                comV = edtComision.getText().toString();
                try {
                    ContentValues cv = new ContentValues();
                    db.execSQL("UPDATE vendedores SET nombre='" + nomV + "',calle='" +calleV + "',colonia ='" +colV + "',telefono='" +telV + "',email='" +emailV + "'," +
                            "comision='" + comV + "' WHERE noVendedor='" + noV + "'");
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

    public void bajaVendedor(){
        if(edtNoVendedor.getText().toString().trim().length()==0)
        {
            showMessage("Error!", "Introduce el Numero");
            return;
        }
        db.execSQL("DELETE FROM vendedores where noVendedor='"+edtNoVendedor.getText()+"'");
        showMessage("Exito!", "Proveedor eliminado");
        clearText();
    }

    public void buscarVendedor(){
        if(edtNoVendedor.getText().toString().trim().length()==0)
        {
            showMessage("Error!", "Introduce el No. del vendedor");
            return;
        }
        Cursor c=db.rawQuery("SELECT * FROM vendedores WHERE noVendedor='"+edtNoVendedor.getText()+"'", null);
        if(c.moveToFirst())
        {
            edtNoVendedor.setText(c.getString(0));
            edtNombreVendedor.setText(c.getString(1));
            edtCalleVendedor.setText(c.getString(2));
            edtColoniaVendedor.setText(c.getString(3));
            edtTelefonoVendedor.setText(c.getString(4));
            edtEmailVendedor.setText(c.getString(5));
            edtComision.setText(c.getString(6));
        }
        else
        {
            showMessage("Error!", "No.   no valida");
            clearText();
        }
    }
    public void consultaVendedores(){
        Cursor c=db.rawQuery("SELECT * FROM vendedores", null);
        if(c.getCount()==0)
        {
            showMessage("Error!", "No se encontraron Vendedores");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            buffer.append("No       : " + c.getString(0)+"\n");
            buffer.append("Nombre       : " + c.getString(1)+"\n");
            buffer.append("Calle        : " + c.getString(2)+"\n");
            buffer.append("Colonia      :" + c.getString(3)+"\n");
            buffer.append("Telefono     : " + c.getString(4)+"\n");
            buffer.append("Email        : " + c.getString(5)+"\n");
            buffer.append("Comision     : " + c.getString(6)+"\n\n");
        }
        showMessage("Vendedores", buffer.toString());
    }

    public boolean validarCampos(){
        if(edtNoVendedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if(edtNoVendedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if (edtCalleVendedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if (edtColoniaVendedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if(edtTelefonoVendedor.getText().toString().trim().length()== 0){
            return false;
        }
        else if(edtEmailVendedor.getText().toString().trim().length()== 0){
            return  false;
        }
        else if(edtComision.getText().toString().trim().length()== 0){
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
        edtNoVendedor.setText("");
        edtNombreVendedor.setText("");
        edtCalleVendedor.setText("");
        edtColoniaVendedor.setText("");
        edtTelefonoVendedor.setText("");
        edtEmailVendedor.setText("");
        edtComision.setText("");
        edtNoVendedor.requestFocus();
    }

    public boolean validarID(){
        if(edtNoVendedor.getText().toString().trim().length()==0)
        {
            showMessage("Error!", "Introduce el No");
            return false;
        }
        else{
            return true;
        }
    }
}