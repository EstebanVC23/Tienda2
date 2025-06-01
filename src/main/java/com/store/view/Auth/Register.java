package com.store.view.auth;

import com.store.services.ProductoServicioImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.auth.constants.AuthConstants;
import com.store.view.auth.controller.RegisterController;
import com.store.view.components.dialogs.auth.RegisterForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista de registro de usuarios que extiende de AuthBaseFrame.
 * Proporciona la interfaz gráfica para el registro de nuevos usuarios.
 */
public class Register extends AuthBaseFrame {
    private RegisterForm registerForm;
    private RegisterActionsPanel actionsPanel;
    private RegisterController controller;

    /**
     * Constructor de la vista de registro.
     * @param usuarioServicio Servicio para gestión de usuarios
     * @param productoServicio Servicio para gestión de productos
     */
    public Register(UsuarioServicioImpl usuarioServicio, ProductoServicioImpl productoServicio, SaleServiceImpl saleServicio) {
        super(usuarioServicio, productoServicio, saleServicio,"Registro de Usuario");
        this.controller = new RegisterController(this, usuarioServicio);
        initializeComponents();
    }

    /**
     * Inicializa los componentes de la vista.
     */
    private void initializeComponents() {
        addLeftPanel(AuthConstants.REGISTER_TITLE, AuthConstants.REGISTER_SUBTITLE);
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
        
        registerForm = new RegisterForm();
        actionsPanel = new RegisterActionsPanel();
        registerForm.add(actionsPanel, createFormButtonsConstraints());
        
        JScrollPane scrollPane = createScrollPane(registerForm);
        
        gbc = createScrollPaneConstraints();
        panelDerecho.add(scrollPane, gbc);
        
        add(panelDerecho);
    }

    /**
     * Crea las restricciones para el título del formulario.
     * @return GridBagConstraints configurado
     */
    private GridBagConstraints createTitleConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
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
        JLabel label = new JLabel("Registrar Usuario", SwingConstants.CENTER);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setFont(Fonts.TITLE);
        return label;
    }

    /**
     * Crea las restricciones para los botones del formulario.
     * @return GridBagConstraints configurado
     */
    private GridBagConstraints createFormButtonsConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    /**
     * Crea un JScrollPane para el formulario.
     * @param content Panel a incluir en el scroll
     * @return JScrollPane configurado
     */
    private JScrollPane createScrollPane(JPanel content) {
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }

    /**
     * Crea las restricciones para el scroll pane.
     * @return GridBagConstraints configurado
     */
    private GridBagConstraints createScrollPaneConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Obtiene el nombre ingresado en el formulario.
     * @return String con el nombre
     */
    public String getNombre() {
        return registerForm.getNombre();
    }

    /**
     * Obtiene el apellido ingresado en el formulario.
     * @return String con el apellido
     */
    public String getApellido() {
        return registerForm.getApellido();
    }

    /**
     * Obtiene el email ingresado en el formulario.
     * @return String con el email
     */
    public String getEmail() {
        return registerForm.getEmail();
    }

    /**
     * Obtiene el tipo de documento seleccionado.
     * @return String con el tipo de documento
     */
    public String getTipoDocumento() {
        return registerForm.getTipoDocumento();
    }

    /**
     * Obtiene el número de documento ingresado.
     * @return String con el número de documento
     */
    public String getNumeroDocumento() {
        return registerForm.getNumeroDocumento();
    }

    /**
     * Obtiene la dirección ingresada.
     * @return String con la dirección
     */
    public String getDireccion() {
        return registerForm.getDireccion();
    }

    /**
     * Obtiene el teléfono ingresado.
     * @return String con el teléfono
     */
    public String getTelefono() {
        return registerForm.getTelefono();
    }

    /**
     * Obtiene la contraseña ingresada.
     * @return String con la contraseña
     */
    public String getPassword() {
        return registerForm.getPassword();
    }

    /**
     * Obtiene la confirmación de contraseña ingresada.
     * @return String con la confirmación de contraseña
     */
    public String getConfirmarPassword() {
        return registerForm.getConfirmarPassword();
    }

    /**
     * Añade un listener para el botón de registro.
     * @param listener ActionListener para el evento
     */
    public void addRegisterListener(ActionListener listener) {
        actionsPanel.addRegisterListener(listener);
    }

    /**
     * Añade un listener para el botón de volver.
     * @param listener ActionListener para el evento
     */
    public void addBackListener(ActionListener listener) {
        actionsPanel.addBackListener(listener);
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

    /**
     * Abre la vista de login y cierra la actual.
     */
    public void openLogin() {
        new Login(usuarioServicio, productoServicio, saleServicio).setVisible(true);
        dispose();
    }
}