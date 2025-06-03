package com.store.services;

import com.store.models.Producto;
import com.store.repository.ProductoRepositorioJson;
import com.store.services.interfaces.IProductoServicio;

import java.util.List;
import java.util.UUID;

/**
 * Implementación concreta de {@link IProductoServicio} que gestiona productos
 * utilizando un repositorio JSON como almacenamiento.
 * Proporciona operaciones CRUD para productos y métodos adicionales para búsquedas
 * y gestión de inventario.
 */
public class ProductoServicioImpl implements IProductoServicio {

    private final ProductoRepositorioJson productoRepositorio;

    /**
     * Constructor que inicializa el servicio con un repositorio JSON.
     */
    public ProductoServicioImpl() {
        this.productoRepositorio = new ProductoRepositorioJson();
    }

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param producto El producto a crear. Si no tiene código, se genera uno automáticamente.
     * @return true si el producto fue creado exitosamente, false si ya existe un producto con el mismo código.
     */
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

    /**
     * Actualiza los datos de un producto existente.
     * 
     * @param producto El producto con los datos actualizados.
     * @return true si el producto fue actualizado exitosamente, false si no se encontró el producto.
     */
    @Override
    public boolean actualizarProducto(Producto producto) {
        return productoRepositorio.actualizarProducto(producto);
    }

    /**
     * Elimina un producto del sistema.
     * 
     * @param codigo El código del producto a eliminar.
     * @return true si el producto fue eliminado exitosamente, false si no se encontró el producto.
     */
    @Override
    public boolean eliminarProducto(String codigo) {
        return productoRepositorio.eliminarProducto(codigo);
    }

    /**
     * Obtiene un producto por su código único.
     * 
     * @param codigo El código del producto a buscar.
     * @return El producto encontrado o null si no existe.
     */
    @Override
    public Producto obtenerProductoPorCodigo(String codigo) {
        return productoRepositorio.obtenerProductoPorCodigo(codigo);
    }

    /**
     * Obtiene una lista de todos los productos en el sistema.
     * 
     * @return Lista de todos los productos registrados.
     */
    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.obtenerProductos();
    }

    /**
     * Busca productos cuyo nombre coincida total o parcialmente con el parámetro.
     * 
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Lista de productos que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepositorio.buscarProductosPorNombre(nombre);
    }

    /**
     * Obtiene productos pertenecientes a una categoría específica.
     * 
     * @param categoria La categoría de productos a buscar.
     * @return Lista de productos que pertenecen a la categoría especificada.
     */
    @Override
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoRepositorio.obtenerProductosPorCategoria(categoria);
    }

    /**
     * Obtiene productos asociados a un proveedor específico.
     * 
     * @param proveedor El nombre del proveedor a buscar.
     * @return Lista de productos asociados al proveedor especificado.
     */
    @Override
    public List<Producto> obtenerProductosPorProveedor(String proveedor) {
        return productoRepositorio.obtenerProductosPorProveedor(proveedor);
    }

    /**
     * Obtiene una lista de todas las categorías de productos existentes.
     * 
     * @return Lista de categorías únicas.
     */
    @Override
    public List<String> obtenerCategorias() {
        return productoRepositorio.obtenerCategorias();
    }

    /**
     * Obtiene una lista de todos los proveedores existentes.
     * 
     * @return Lista de proveedores únicos.
     */
    @Override
    public List<String> obtenerProveedores() {
        return productoRepositorio.obtenerProveedores();
    }

    /**
     * Actualiza el stock de un producto sumando la cantidad especificada.
     * 
     * @param codigo El código del producto a actualizar.
     * @param cantidad La cantidad a sumar (puede ser negativa para disminuir el stock).
     * @return true si la actualización fue exitosa, false si el producto no existe o no hay stock suficiente.
     */
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
     * Genera un código único para productos nuevos con formato "PROD-XXXXXX".
     * 
     * @return Código único generado con 6 caracteres aleatorios en mayúsculas.
     */
    private String generarCodigoProducto() {
        return "PROD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}