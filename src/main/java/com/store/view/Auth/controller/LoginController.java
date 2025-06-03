package com.store.view.auth.controller;

import com.store.models.Usuario;
import com.store.services.ProductoServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.view.AdminView.AdminView;
import com.store.view.UserView.UserView;
import com.store.view.auth.Login;
import com.store.view.auth.constants.AuthConstants;
import com.store.view.auth.Register;

import java.awt.event.ActionEvent;

/**
 * Controlador para manejar la lógica de inicio de sesión y navegación relacionada.
 * Gestiona las interacciones entre la vista de login y los servicios de usuario.
 */
public class LoginController {
    private final Login view;
    private final UsuarioServicioImpl usuarioServicio;
    private final ProductoServicioImpl productoServicio;
    private final SaleServiceImpl saleServicio;

    /**
     * Constructor del controlador de login.
     * @param view Vista de login asociada
     * @param usuarioServicio Servicio para gestión de usuarios
     * @param productoServicio Servicio para gestión de productos
     */
    public LoginController(Login view, UsuarioServicioImpl usuarioServicio, ProductoServicioImpl productoServicio, SaleServiceImpl saleServicio) {
        this.view = view;
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        this.saleServicio = saleServicio;
    }

    /**
     * Configura los listeners para los eventos de la vista.
     */
    public void configureEvents() {
        view.addLoginListener(this::handleLogin);
        view.addRegisterListener(this::openRegister);
    }

    /**
     * Maneja el evento de inicio de sesión.
     * @param e Evento de acción
     */
    private void handleLogin(ActionEvent e) {
        String correo = view.getCorreo();
        String password = view.getPassword();

        if (correo.isEmpty() || password.isEmpty()) {
            view.showError(AuthConstants.EMPTY_FIELDS_LOGIN);
            return;
        }

        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setEmail(correo);
        usuarioLogin.setPassword(password);

        if (!usuarioServicio.validarUsuario(usuarioLogin)) {
            view.showError(AuthConstants.LOGIN_ERROR);
            return;
        }

        Usuario dataUser = usuarioServicio.obtenerUsuarioPorEmail(correo);
        view.showSuccess(String.format(AuthConstants.LOGIN_SUCCESS, dataUser.getNombre()));
        openUserView(dataUser);
    }

    /**
     * Abre la vista correspondiente según el rol del usuario.
     * @param usuario Usuario autenticado
     */
    private void openUserView(Usuario usuario) {
        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            new AdminView(usuario, usuarioServicio, productoServicio, saleServicio).setVisible(true);
        } else {
            new UserView(usuario, productoServicio, usuarioServicio, saleServicio).setVisible(true);
        }
        view.dispose();
    }

    /**
     * Abre la vista de registro de nuevos usuarios.
     * @param e Evento de acción
     */
    private void openRegister(ActionEvent e) {
        new Register(usuarioServicio, productoServicio, saleServicio).setVisible(true);
        view.dispose();
    }
}