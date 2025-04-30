package com.store.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.store.models.Producto;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProductoRepositorioJson {
    private static final String FILE_PATH = "src/main/resources/json/productos.json";
    private List<Producto> productos;

    public ProductoRepositorioJson() {
        this.productos = cargarProductos();
    }

    private List<Producto> cargarProductos() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new Gson().fromJson(jsonString, new TypeToken<List<Producto>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void guardarProductos() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(productos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public void agregarProducto(Producto newProducto) {
        productos.add(newProducto);
        guardarProductos();
    }

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

    public boolean eliminarProducto(String codigo) {
        if (productos.removeIf(p -> p.getCodigo().equals(codigo))) {
            guardarProductos();
            return true;
        }
        return false;
    }

    public Producto obtenerProductoPorCodigo(String codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        List<Producto> productosCategoria = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                productosCategoria.add(producto);
            }
        }
        return productosCategoria;
    }

    public List<Producto> obtenerProductosPorProveedor(String proveedor) {
        List<Producto> productosProveedor = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getProveedor().equalsIgnoreCase(proveedor)) {
                productosProveedor.add(producto);
            }
        }
        return productosProveedor;
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }

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

    public List<String> obtenerCategorias() {
        Set<String> categorias = new HashSet<>();
        for (Producto producto : productos) {
            if (!producto.getCategoria().isEmpty()) {
                categorias.add(producto.getCategoria());
            }
        }
        return new ArrayList<>(categorias);
    }

    public List<String> obtenerProveedores() {
        Set<String> proveedores = new HashSet<>();
        for (Producto producto : productos) {
            if (!producto.getProveedor().isEmpty()) {
                proveedores.add(producto.getProveedor());
            }
        }
        return new ArrayList<>(proveedores);
    }

    public boolean existeCodigoProducto(String codigo) {
        return productos.stream().anyMatch(p -> p.getCodigo().equals(codigo));
    }
}