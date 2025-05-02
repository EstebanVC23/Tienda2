package com.store.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.store.models.Producto;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Repositorio para gestionar productos almacenados en formato JSON.
 * Proporciona métodos para cargar, guardar, agregar, actualizar, eliminar y buscar productos.
 */
public class ProductoRepositorioJson {
    private static final String FILE_PATH = "src/main/resources/json/productos.json";
    private List<Producto> productos;

    /**
     * Constructor que inicializa el repositorio cargando los productos desde el archivo JSON.
     */
    public ProductoRepositorioJson() {
        this.productos = cargarProductos();
    }

    /**
     * Carga los productos desde el archivo JSON.
     * @return Lista de productos cargados, o lista vacía si ocurre un error.
     */
    private List<Producto> cargarProductos() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new Gson().fromJson(jsonString, new TypeToken<List<Producto>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda los productos actuales en el archivo JSON.
     */
    private void guardarProductos() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(productos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo producto al repositorio y guarda los cambios.
     * @param newProducto Producto a agregar.
     */
    public void agregarProducto(Producto newProducto) {
        productos.add(newProducto);
        guardarProductos();
    }

    /**
     * Actualiza un producto existente en el repositorio.
     * @param productoModificado Producto con los datos actualizados.
     * @return true si se actualizó correctamente, false si no se encontró el producto.
     */
    public boolean actualizarProducto(Producto productoModificado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(productoModificado.getCodigo())) {
                productos.set(i, productoModificado);
                guardarProductos();
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un producto del repositorio.
     * @param codigo Código del producto a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró el producto.
     */
    public boolean eliminarProducto(String codigo) {
        if (productos.removeIf(p -> p.getCodigo().equals(codigo))) {
            guardarProductos();
            return true;
        }
        return false;
    }

    /**
     * Obtiene un producto por su código.
     * @param codigo Código del producto a buscar.
     * @return El producto encontrado o null si no existe.
     */
    public Producto obtenerProductoPorCodigo(String codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los productos de una categoría específica.
     * @param categoria Categoría a filtrar.
     * @return Lista de productos que pertenecen a la categoría.
     */
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        List<Producto> productosCategoria = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                productosCategoria.add(producto);
            }
        }
        return productosCategoria;
    }

    /**
     * Obtiene todos los productos de un proveedor específico.
     * @param proveedor Proveedor a filtrar.
     * @return Lista de productos que pertenecen al proveedor.
     */
    public List<Producto> obtenerProductosPorProveedor(String proveedor) {
        List<Producto> productosProveedor = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getProveedor().equalsIgnoreCase(proveedor)) {
                productosProveedor.add(producto);
            }
        }
        return productosProveedor;
    }

    /**
     * Obtiene todos los productos del repositorio.
     * @return Lista completa de productos.
     */
    public List<Producto> obtenerProductos() {
        return productos;
    }

    /**
     * Busca productos cuyo nombre contenga el texto especificado (case insensitive).
     * @param nombre Texto a buscar en los nombres de productos.
     * @return Lista de productos que coinciden con la búsqueda.
     */
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        String nombreLower = nombre.toLowerCase();
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombreLower)) {
                resultados.add(producto);
            }
        }
        return resultados;
    }

    /**
     * Obtiene todas las categorías únicas de productos.
     * @return Lista de categorías sin duplicados.
     */
    public List<String> obtenerCategorias() {
        Set<String> categorias = new HashSet<>();
        for (Producto producto : productos) {
            if (!producto.getCategoria().isEmpty()) {
                categorias.add(producto.getCategoria());
            }
        }
        return new ArrayList<>(categorias);
    }

    /**
     * Obtiene todos los proveedores únicos de productos.
     * @return Lista de proveedores sin duplicados.
     */
    public List<String> obtenerProveedores() {
        Set<String> proveedores = new HashSet<>();
        for (Producto producto : productos) {
            if (!producto.getProveedor().isEmpty()) {
                proveedores.add(producto.getProveedor());
            }
        }
        return new ArrayList<>(proveedores);
    }

    /**
     * Verifica si existe un producto con el código especificado.
     * @param codigo Código a verificar.
     * @return true si existe un producto con ese código, false en caso contrario.
     */
    public boolean existeCodigoProducto(String codigo) {
        return productos.stream().anyMatch(p -> p.getCodigo().equals(codigo));
    }
}