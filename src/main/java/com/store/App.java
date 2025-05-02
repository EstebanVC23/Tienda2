package com.store;

import com.store.services.ProductoServicioImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.view.auth.Login;

/**
 * Clase principal que inicia la aplicación de tienda.
 * <p>
 * Esta clase se encarga de:
 * <ul>
 *   <li>Inicializar los servicios necesarios</li>
 *   <li>Crear y mostrar la ventana de login</li>
 *   <li>Servir como punto de entrada de la aplicación</li>
 * </ul>
 */
public class App {
    /**
     * Punto de entrada principal de la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        UsuarioServicioImpl usuarioServicio = new UsuarioServicioImpl();
        ProductoServicioImpl productoServicio = new ProductoServicioImpl();

        Login login = new Login(usuarioServicio, productoServicio);
        login.setVisible(true);
    }
}