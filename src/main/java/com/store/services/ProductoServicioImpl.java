package com.store.services;

import com.store.models.Producto;
import com.store.repository.ProductoRepositorioJson;
import java.util.List;
import java.util.UUID;

/**
 * Implementación concreta de {@link IProductoServicio} que gestiona productos
 * utilizando un repositorio JSON como almacenamiento.
 */
public class ProductoServicioImpl implements IProductoServicio {

    private final ProductoRepositorioJson productoRepositorio;

    /**
     * Constructor que inicializa el repositorio JSON.
     */
    public ProductoServicioImpl() {
        this.productoRepositorio = new ProductoRepositorioJson();
    }

    @Override
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

    @Override
    public boolean actualizarProducto(Producto producto) {
        return productoRepositorio.actualizarProducto(producto);
    }

    @Override
    public boolean eliminarProducto(String codigo) {
        return productoRepositorio.eliminarProducto(codigo);
    }

    @Override
    public Producto obtenerProductoPorCodigo(String codigo) {
        return productoRepositorio.obtenerProductoPorCodigo(codigo);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.obtenerProductos();
    }

    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepositorio.buscarProductosPorNombre(nombre);
    }

    @Override
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepositorio.obtenerProductosPorCategoria(categoria);
    }

    @Override
    public List<Producto> obtenerProductosPorProveedor(String proveedor) {
        return productoRepositorio.obtenerProductosPorProveedor(proveedor);
    }

    @Override
    public List<String> obtenerCategorias() {
        return productoRepositorio.obtenerCategorias();
    }

    @Override
    public List<String> obtenerProveedores() {
        return productoRepositorio.obtenerProveedores();
    }

    @Override
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

    /**
     * Genera un código único para productos nuevos.
     * @return Código con formato "PROD-XXXXXX" (6 caracteres aleatorios)
     */
    private String generarCodigoProducto() {
        return "PROD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}