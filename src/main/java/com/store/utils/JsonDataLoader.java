package com.store.utils;

import com.store.models.Producto;
import com.store.models.Usuario;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Clase utilitaria para cargar datos JSON y convertirlos en objetos de dominio.
 * Utiliza org.json.simple que viene incluido en muchas distribuciones de Java.
 */
public class JsonDataLoader {

    /**
     * Carga usuarios desde un archivo JSON ubicado en `resources`.
     * 
     * @return Lista de usuarios
     */
    public static List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (InputStreamReader reader = new InputStreamReader(
                JsonDataLoader.class.getClassLoader().getResourceAsStream("json/usuarios.json"))) {

            Object obj = parser.parse(reader);
            JSONArray usuariosArray = (JSONArray) obj;

            for (Object item : usuariosArray) {
                JSONObject jsonUsuario = (JSONObject) item;
                Usuario usuario = new Usuario();
                usuario.setId(((Long) jsonUsuario.get("id")).intValue());
                usuario.setNombre((String) jsonUsuario.get("nombre"));
                usuario.setApellido((String) jsonUsuario.get("apellido"));
                usuario.setEmail((String) jsonUsuario.get("email"));
                usuario.setTipoDocumento((String) jsonUsuario.get("tipoDocumento"));
                usuario.setNumeroDocumento((String) jsonUsuario.get("numeroDocumento"));
                usuario.setDireccion((String) jsonUsuario.get("direccion"));
                usuario.setTelefono((String) jsonUsuario.get("telefono"));
                usuario.setPassword((String) jsonUsuario.get("password"));
                usuario.setRol((String) jsonUsuario.get("rol"));
                usuario.setEstadoActivo((Boolean) jsonUsuario.get("estadoActivo"));
                usuarios.add(usuario);
            }

            return usuarios;
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios desde JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Carga productos desde un archivo JSON ubicado en `resources`.
     * 
     * @return Lista de productos
     */
    public static List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (InputStreamReader reader = new InputStreamReader(
                JsonDataLoader.class.getClassLoader().getResourceAsStream("json/productos.json"))) {

            Object obj = parser.parse(reader);
            JSONArray productosArray = (JSONArray) obj;

            for (Object item : productosArray) {
                JSONObject jsonProducto = (JSONObject) item;
                Producto producto = new Producto();
                producto.setCodigo((String) jsonProducto.get("codigo"));
                producto.setNombre((String) jsonProducto.get("nombre"));
                producto.setDescripcion((String) jsonProducto.get("descripcion"));

                // Manejo de números
                Object precioObj = jsonProducto.get("precio");
                if (precioObj instanceof Double) {
                    producto.setPrecio((Double) precioObj);
                } else if (precioObj instanceof Long) {
                    producto.setPrecio(((Long) precioObj).doubleValue());
                }

                Object stockObj = jsonProducto.get("stock");
                if (stockObj instanceof Long) {
                    producto.setStock(((Long) stockObj).intValue());
                } else if (stockObj instanceof Integer) {
                    producto.setStock((Integer) stockObj);
                }

                producto.setCategoria((String) jsonProducto.get("categoria"));
                producto.setProveedor((String) jsonProducto.get("proveedor"));
                productos.add(producto);
            }

            return productos;
        } catch (Exception e) {
            System.err.println("Error al cargar productos desde JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}