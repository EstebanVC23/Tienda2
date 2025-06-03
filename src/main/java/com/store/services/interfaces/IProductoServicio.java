package com.store.services.interfaces;

import com.store.models.Producto;
import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para la gestión de productos.
 * Proporciona métodos para CRUD, búsquedas y gestión de stock.
 */
public interface IProductoServicio {

    /**
     * Crea un nuevo producto en el sistema.
     * @param producto Producto a crear (si no tiene código, se genera automáticamente)
     * @return true si se creó exitosamente, false si ya existe un producto con el mismo código
     */
    boolean crearProducto(Producto producto);

    /**
     * Actualiza un producto existente.
     * @param producto Producto con datos actualizados
     * @return true si se actualizó correctamente, false si el producto no existe
     */
    boolean actualizarProducto(Producto producto);

    /**
     * Elimina un producto por su código.
     * @param codigo Código del producto a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el producto
     */
    boolean eliminarProducto(String codigo);

    /**
     * Obtiene un producto por su código único.
     * @param codigo Código del producto a buscar
     * @return Producto encontrado o null si no existe
     */
    Producto obtenerProductoPorCodigo(String codigo);

    /**
     * Obtiene todos los productos registrados.
     * @return Lista de productos (vacía si no hay registros)
     */
    List<Producto> listarProductos();

    /**
     * Busca productos por coincidencia parcial en el nombre (case-insensitive).
     * @param nombre Texto a buscar
     * @return Lista de productos que coinciden con el criterio
     */
    List<Producto> buscarProductosPorNombre(String nombre);

    /**
     * Filtra productos por categoría.
     * @param categoria Categoría a filtrar
     * @return Lista de productos que pertenecen a la categoría
     */
    List<Producto> obtenerProductosPorCategoria(String categoria);

    /**
     * Filtra productos por proveedor.
     * @param proveedor Proveedor a filtrar
     * @return Lista de productos asociados al proveedor
     */
    List<Producto> obtenerProductosPorProveedor(String proveedor);

    /**
     * Obtiene todas las categorías únicas de productos existentes.
     * @return Lista de categorías (sin duplicados)
     */
    List<String> obtenerCategorias();

    /**
     * Obtiene todos los proveedores únicos registrados.
     * @return Lista de proveedores (sin duplicados)
     */
    List<String> obtenerProveedores();

    /**
     * Actualiza el stock de un producto (incrementa o decrementa).
     * @param codigo Código del producto
     * @param cantidad Valor a ajustar (positivo para aumentar, negativo para disminuir)
     * @return true si la operación fue exitosa, false si el producto no existe o el stock resultante es inválido
     */
    boolean actualizarStock(String codigo, int cantidad);
}