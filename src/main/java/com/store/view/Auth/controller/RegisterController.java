package com.store.view.auth.controller;

import com.store.models.Usuario;
import com.store.services.UsuarioServicioImpl;
import com.store.view.auth.constants.AuthConstants;
import com.store.view.auth.Register;

import java.awt.event.ActionEvent;

/**
 * Controlador para manejar la lógica de registro de nuevos usuarios.
 * Gestiona la validación de campos y la creación de cuentas de usuario.
 */
public class RegisterController {
    private final Register view;
    private final UsuarioServicioImpl usuarioServicio;

    /**
     * Constructor del controlador de registro.
     * @param view Vista de registro asociada
     * @param usuarioServicio Servicio para gestión de usuarios
     */
    public RegisterController(Register view, UsuarioServicioImpl usuarioServicio) {
        this.view = view;
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Configura los listeners para los eventos de la vista.
     */
    public void configureEvents() {
        view.addRegisterListener(this::processRegistration);
        view.addBackListener(this::openLogin);
    }

    /**
     * Procesa el intento de registro de un nuevo usuario.
     * @param e Evento de acción
     */
    private void processRegistration(ActionEvent e) {
        String nombre = view.getNombre();
        String apellido = view.getApellido();
        String email = view.getEmail();
        String tipoDocumento = view.getTipoDocumento();
        String numeroDocumento = view.getNumeroDocumento();
        String direccion = view.getDireccion();
        String telefono = view.getTelefono();
        String password = view.getPassword();
        String confirmarPassword = view.getConfirmarPassword();

        if (!validateFields(nombre, apellido, email, numeroDocumento, 
                          direccion, telefono, password, confirmarPassword)) {
            return;
        }

        registerNewUser(nombre, apellido, email, tipoDocumento, 
                      numeroDocumento, direccion, telefono, password);
    }

    /**
     * Valida los campos del formulario de registro.
     * @param fields Campos a validar
     * @return true si todos los campos son válidos, false en caso contrario
     */
    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                view.showError(AuthConstants.EMPTY_FIELDS);
                return false;
            }
        }

        if (!fields[6].equals(fields[7])) {
            view.showError(AuthConstants.PASSWORD_MISMATCH);
            return false;
        }

        if (usuarioServicio.obtenerUsuarioPorEmail(fields[2]) != null) {
            view.showError(AuthConstants.EMAIL_EXISTS);
            return false;
        }

        return true;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param tipoDocumento Tipo de documento
     * @param numeroDocumento Número de documento
     * @param direccion Dirección del usuario
     * @param telefono Teléfono del usuario
     * @param password Contraseña del usuario
     */
    private void registerNewUser(String nombre, String apellido, String email,
                               String tipoDocumento, String numeroDocumento,
                               String direccion, String telefono, String password) {
        Usuario nuevoUsuario = createUser(nombre, apellido, email, tipoDocumento, 
                                        numeroDocumento, direccion, telefono, password);

        if (usuarioServicio.crearUsuario(nuevoUsuario)) {
            view.showSuccess(AuthConstants.SUCCESS_REGISTER);
            view.openLogin();
        } else {
            view.showError(AuthConstants.ERROR_REGISTER);
        }
    }

    /**
     * Crea un objeto Usuario con los datos del formulario.
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param email Email del usuario
     * @param tipoDocumento Tipo de documento
     * @param numeroDocumento Número de documento
     * @param direccion Dirección del usuario
     * @param telefono Teléfono del usuario
     * @param password Contraseña del usuario
     * @return Objeto Usuario creado
     */
    private Usuario createUser(String nombre, String apellido, String email,
                             String tipoDocumento, String numeroDocumento,
                             String direccion, String telefono, String password) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNumeroDocumento(numeroDocumento);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setPassword(password);
        usuario.setRol("USER");
        usuario.setEstadoActivo(true);
        return usuario;
    }

    /**
     * Abre la vista de login.
     * @param e Evento de acción
     */
    private void openLogin(ActionEvent e) {
        view.openLogin();
    }
}