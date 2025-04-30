package com.store.services;

import com.store.models.Producto;
import com.store.repository.ProductoRepositorioJson;
import java.util.List;
import java.util.UUID;

/**
 * Clase ProductoServicio.
 * 
 * Gestiona productos en el archivo productos.json, incluyendo creación,
 * actualización, eliminación y recuperación de productos.
 */
public class ProductoServicio {
    private ProductoRepositorioJson productoRepositorio;

    /** 
     * Constructor de ProductoServicio. 
     * Inicializa el repositorio de productos basado en JSON. 
     */
    public ProductoServicio() {
        this.productoRepositorio = new ProductoRepositorioJson();
    }

    public boolean crearProducto(Producto producto) {
        if (producto.getCodigo() == null || producto.getCodigo().isEmpty()) {
            producto.setCodigo(generarCodigoProducto());
        } else if (productoRepositorio.existeCodigoProducto(producto.getCodigo())) {
            System.out.println("Ya existe un producto con el código: " + producto.getCodigo());
            return false;
        }
        productoRepositorio.agregarProducto(producto);
        return true;
    }

    public boolean actualizarProducto(Producto producto) {
        return productoRepositorio.actualizarProducto(producto);
    }

    public boolean eliminarProducto(String codigo) {
        return productoRepositorio.eliminarProducto(codigo);
    }

    public Producto obtenerProductoPorCodigo(String codigo) {
        return productoRepositorio.obtenerProductoPorCodigo(codigo);
    }

    public List<Producto> listarProductos() {
        return productoRepositorio.obtenerProductos();
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepositorio.buscarProductosPorNombre(nombre);
    }

    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepositorio.obtenerProductosPorCategoria(categoria);
    }

    public List<Producto> obtenerProductosPorProveedor(String proveedor) {
        return productoRepositorio.obtenerProductosPorProveedor(proveedor);
    }

    public List<String> obtenerCategorias() {
        return productoRepositorio.obtenerCategorias();
    }

    public List<String> obtenerProveedores() {
        return productoRepositorio.obtenerProveedores();
    }

    public boolean actualizarStock(String codigo, int cantidad) {
        Producto producto = productoRepositorio.obtenerProductoPorCodigo(codigo);
        if (producto == null) {
            return false;
        }

        int nuevoStock = producto.getStock() + cantidad;
        if (nuevoStock < 0) {
            System.out.println("Stock insuficiente para el producto: " + producto.getNombre());
            return false;
        }

        producto.setStock(nuevoStock);
        return productoRepositorio.actualizarProducto(producto);
    }

    private String generarCodigoProducto() {
        return "PROD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}