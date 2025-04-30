package com.store.models;

/**
 * Clase que representa un producto en el sistema.
 * Contiene información sobre el producto, como su código, nombre,
 */
public class Producto {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String categoria;
    private String proveedor;
    
    public Producto() {
        this.codigo = "";
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0.0;
        this.stock = 0;
        this.categoria = "";
        this.proveedor = "";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    
}
