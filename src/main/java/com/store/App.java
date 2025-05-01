package com.store;

import com.store.Auth.Login;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;

/**
 * Clase principal de la aplicación.
 * Inicializa la aplicación y carga los datos desde archivos JSON.
 * 
 * NOTA: 
 * Borrar los comentarios de las clases y usar javadoc para generar la documentación.
 * los titulos de las tablas no aparecen
 * modularizar el código en paquetes y clases
 * recrear el diseño de los dialogs de productos iguales al de los de usuarios
 * estudiar POO
 * usar interfaces para los servicios
 * identificar los métodos que no se usan y eliminarlos
 * crear un readme.md con la información de la aplicación
 * 
 */
public class App {
    public static void main(String[] args) {
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        ProductoServicio productoServicio = new ProductoServicio();

        Login login = new Login(usuarioServicio, productoServicio);

        login.setVisible(true);
    }
}