package com.store.view.auth;

import com.store.services.ProductoServicioImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.auth.components.LoginForm;
import com.store.view.auth.constants.AuthConstants;
import com.store.view.auth.controller.LoginController;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

/**
 * Vista de inicio de sesión que extiende de AuthBaseFrame.
 * Proporciona la interfaz gráfica para el login de usuarios.
 */
public class Login extends AuthBaseFrame {
    private LoginForm loginForm;
    private LoginController controller;

    /**
     * Constructor de la vista de login.
     * @param usuarioServicio Servicio para gestión de usuarios
     * @param productoServicio Servicio para gestión de productos
     */
    public Login(UsuarioServicioImpl usuarioServicio, ProductoServicioImpl productoServicio) {
        super(usuarioServicio, productoServicio, "Login");
        this.controller = new LoginController(this, usuarioServicio, productoServicio);
        initializeComponents();
    }

    /**
     * Inicializa los componentes de la vista.
     */
    private void initializeComponents() {
        addLeftPanel(AuthConstants.LOGIN_TITLE, AuthConstants.LOGIN_SUBTITLE);
        createRightPanelContent();
        controller.configureEvents();
    }

    /**
     * Crea y configura el contenido del panel derecho.
     */
    private void createRightPanelContent() {
        JPanel panelDerecho = createRightPanel();
        panelDerecho.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = createTitleConstraints();
        JLabel labelTitulo = createTitleLabel();
        panelDerecho.add(labelTitulo, gbc);
        
        loginForm = new LoginForm();
        
        gbc = createFormConstraints();
        panelDerecho.add(loginForm, gbc);
        
        add(panelDerecho);
    }

    /**
     * Crea las restricciones para el título del formulario.
     * @return GridBagConstraints configurado
     */
    private GridBagConstraints createTitleConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        return gbc;
    }

    /**
     * Crea el label del título del formulario.
     * @return JLabel configurado
     */
    private JLabel createTitleLabel() {
        JLabel label = new JLabel(AuthConstants.LOGIN_FORM_TITLE, SwingConstants.CENTER);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setFont(Fonts.TITLE);
        return label;
    }

    /**
     * Crea las restricciones para el formulario.
     * @return GridBagConstraints configurado
     */
    private GridBagConstraints createFormConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Obtiene el correo ingresado en el formulario.
     * @return String con el correo electrónico
     */
    public String getCorreo() {
        return loginForm.getCorreo();
    }

    /**
     * Obtiene la contraseña ingresada en el formulario.
     * @return String con la contraseña
     */
    public String getPassword() {
        return loginForm.getPassword();
    }

    /**
     * Añade un listener para el botón de login.
     * @param listener ActionListener para el evento
     */
    public void addLoginListener(ActionListener listener) {
        loginForm.addLoginListener(listener);
    }

    /**
     * Añade un listener para el botón de registro.
     * @param listener ActionListener para el evento
     */
    public void addRegisterListener(ActionListener listener) {
        loginForm.addRegisterListener(listener);
    }

    /**
     * Muestra un mensaje de error.
     * @param message Mensaje a mostrar
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito.
     * @param message Mensaje a mostrar
     */
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}