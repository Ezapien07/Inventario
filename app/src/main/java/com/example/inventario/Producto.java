package com.example.inventario;

public class Producto {
    private String id;
    private String nombre;
    private String unidad;
    private String linea;
    private int existencias;
    private int costo;
    private double promedio;
    private double venta1;
    private double venta2;

    public Producto(String id, String nombre, String unidad, String linea, int existencias, int costo, double promedio, double venta1, double venta2) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.linea = linea;
        this.existencias = existencias;
        this.costo = costo;
        this.promedio = promedio;
        this.venta1 = venta1;
        this.venta2 = venta2;
    }

    public Producto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public double getVenta1() {
        return venta1;
    }

    public void setVenta1(double venta1) {
        this.venta1 = venta1;
    }

    public double getVenta2() {
        return venta2;
    }

    public void setVenta2(double venta2) {
        this.venta2 = venta2;
    }
}
