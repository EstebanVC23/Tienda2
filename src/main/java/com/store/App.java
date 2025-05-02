package com.store;

import com.store.services.ProductoServicioImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.view.auth.Login;

/**
 * Clase principal de la aplicación.
 * Inicializa la aplicación y carga los datos desde archivos JSON.
 * 
 * NOTA: 
 * dialog admin y users, falta documentar
 * modularizar el código en paquetes y clases
 * estudiar POO
 * identificar los métodos que no se usan y eliminarlos
 * crear un readme.md con la información de la aplicación
 * 
 */
public class App {
    public static void main(String[] args) {
        UsuarioServicioImpl usuarioServicio = new UsuarioServicioImpl();
        ProductoServicioImpl productoServicio = new ProductoServicioImpl();

        Login login = new Login(usuarioServicio, productoServicio);

        login.setVisible(true);
    }
}