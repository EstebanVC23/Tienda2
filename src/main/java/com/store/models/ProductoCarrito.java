package com.store.models;

/**
 * Clase que representa un producto en el carrito de compras
 * con la cantidad seleccionada por el usuario.
 */
public class ProductoCarrito {
    private final Producto producto;
    private int cantidadSeleccionada;

    public ProductoCarrito(Producto producto, int cantidadSeleccionada) {
        this.producto = producto;
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    public void aumentarCantidad(int cantidad) {
        this.cantidadSeleccionada += cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidadSeleccionada;
    }
}