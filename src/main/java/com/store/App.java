package com.store;

import com.store.Auth.Login;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;

/**
 * Clase principal de la aplicación.
 * Inicializa la aplicación y carga los datos desde archivos JSON.
 */
public class App {
    public static void main(String[] args) {
        // Inicializar servicios con repositorios basados en JSON
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        ProductoServicio productoServicio = new ProductoServicio();

        // Crear la interfaz de login y pasar los servicios
        Login login = new Login(usuarioServicio, productoServicio);

        login.setVisible(true);
    }
}