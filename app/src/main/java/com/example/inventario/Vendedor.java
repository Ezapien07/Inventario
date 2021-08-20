package com.example.inventario;

public class Vendedor {
    private int id;
    private String nombre;
    private String calle;
    private String colonia;
    private String telefono;
    private String email;
    private int comisiones;

    public Vendedor(int id, String nombre, String calle, String colonia, String telefono, String email, int comisiones) {
        this.id = id;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.telefono = telefono;
        this.email = email;
        this.comisiones = comisiones;
    }

    public Vendedor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getComisiones() {
        return comisiones;
    }

    public void setComisiones(int comisiones) {
        this.comisiones = comisiones;
    }
}
