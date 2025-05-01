package com.store.view.Auth;

import com.store.models.Usuario;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.forauth.CampoEntrada;
import com.store.view.Auth.constants.AuthConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Registro extends AuthBaseFrame {
    private CampoEntrada campoNombre;
    private CampoEntrada campoApellido;
    private CampoEntrada campoEmail;
    private JComboBox<String> comboTipoDocumento;
    private CampoEntrada campoNumeroDocumento;
    private CampoEntrada campoDireccion;
    private CampoEntrada campoTelefono;
    private CampoEntrada campoPassword;
    private CampoEntrada campoConfirmarPassword;
    private CustomButton botonRegistrar;
    private CustomButton botonVolver;

    public Registro(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        super(usuarioServicio, productoServicio, "Registro de Usuario");
        initializeComponents();
    }

    private void initializeComponents() {
        addLeftPanel(AuthConstants.REGISTER_TITLE, AuthConstants.REGISTER_SUBTITLE);
        createRightPanelContent();
        configureEvents();
    }

    private void createRightPanelContent() {
        JPanel panelDerecho = createRightPanel();
        
        JLabel labelTitulo = createTitleLabel("Registrar Usuario");
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        
        JPanel panelFormulario = createFormPanel();
        int anchoCampo = 300;
        int altoCampo = 40;

        campoNombre = new CampoEntrada("texto", "Nombre", anchoCampo, altoCampo);
        campoApellido = new CampoEntrada("texto", "Apellido", anchoCampo, altoCampo);
        campoEmail = new CampoEntrada("texto", "Correo electrónico", anchoCampo, altoCampo);
        
        comboTipoDocumento = createDocumentTypeComboBox(anchoCampo, altoCampo);
        campoNumeroDocumento = new CampoEntrada("entero", "Número Documento", anchoCampo, altoCampo);
        campoDireccion = new CampoEntrada("texto", "Dirección", anchoCampo, altoCampo);
        campoTelefono = new CampoEntrada("texto", "Teléfono", anchoCampo, altoCampo);
        campoPassword = new CampoEntrada("password", "Contraseña", anchoCampo, altoCampo);
        campoConfirmarPassword = new CampoEntrada("password", "Confirmar Contraseña", anchoCampo, altoCampo);

        addFieldsToForm(panelFormulario);
        
        JScrollPane scrollPane = createScrollPane(panelFormulario);
        JPanel panelBotones = createButtonPanel();
        
        botonRegistrar = createPrimaryButton("REGISTRAR");
        botonVolver = createSecondaryButton("Volver al Login");
        
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonVolver);
        
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        add(panelDerecho);
    }

    private JComboBox<String> createDocumentTypeComboBox(int width, int height) {
        JComboBox<String> combo = new JComboBox<>(new String[]{"DNI", "Pasaporte", "Cédula"});
        combo.setMaximumSize(new Dimension(width, height));
        combo.setFont(Fonts.BODY);
        combo.setBackground(Color.WHITE);
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return combo;
    }

    private void addFieldsToForm(JPanel formPanel) {
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoNombre);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoApellido);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoEmail);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createDocumentTypePanel());
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoNumeroDocumento);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoDireccion);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoTelefono);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoPassword);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(campoConfirmarPassword);
        formPanel.add(Box.createVerticalGlue());
    }

    private JPanel createDocumentTypePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel label = new JLabel("Tipo Documento");
        label.setForeground(Colors.SECONDARY_TEXT);
        label.setFont(Fonts.BODY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(comboTipoDocumento);
        return panel;
    }

    private JScrollPane createScrollPane(JPanel content) {
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }

    private CustomButton createPrimaryButton(String text) {
        CustomButton button = new CustomButton(text, Colors.PRIMARY, Color.WHITE);
        button.setPreferredSize(AuthConstants.BUTTON_SIZE);
        button.setFont(Fonts.BUTTON.deriveFont(AuthConstants.BUTTON_FONT_SIZE));
        return button;
    }

    private CustomButton createSecondaryButton(String text) {
        CustomButton button = new CustomButton(text, Colors.SECONDARY_GRAY, Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(
            AuthConstants.BUTTON_PADDING.top,
            AuthConstants.BUTTON_PADDING.left,
            AuthConstants.BUTTON_PADDING.bottom,
            AuthConstants.BUTTON_PADDING.right
        ));
        return button;
    }

    private void configureEvents() {
        botonRegistrar.addActionListener(this::processRegistration);
        botonVolver.addActionListener(_ -> openLogin());
    }

    private void processRegistration(ActionEvent e) {
        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String email = campoEmail.getText().trim();
        String tipoDocumento = (String) comboTipoDocumento.getSelectedItem();
        String numeroDocumento = campoNumeroDocumento.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String password = campoPassword.getText();
        String confirmarPassword = campoConfirmarPassword.getText();

        if (!validateFields(nombre, apellido, email, numeroDocumento, 
                          direccion, telefono, password, confirmarPassword)) {
            return;
        }

        registerNewUser(nombre, apellido, email, tipoDocumento, 
                      numeroDocumento, direccion, telefono, password);
    }

    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                showErrorMessage(AuthConstants.EMPTY_FIELDS);
                return false;
            }
        }

        if (!fields[6].equals(fields[7])) {
            showErrorMessage(AuthConstants.PASSWORD_MISMATCH);
            return false;
        }

        if (usuarioServicio.obtenerUsuarioPorEmail(fields[2]) != null) {
            showErrorMessage(AuthConstants.EMAIL_EXISTS);
            return false;
        }

        return true;
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void registerNewUser(String nombre, String apellido, String email,
                               String tipoDocumento, String numeroDocumento,
                               String direccion, String telefono, String password) {
        Usuario nuevoUsuario = createUser(nombre, apellido, email, tipoDocumento, 
                                        numeroDocumento, direccion, telefono, password);

        if (usuarioServicio.crearUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.SUCCESS_REGISTER, 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            openLogin();
        } else {
            showErrorMessage(AuthConstants.ERROR_REGISTER);
        }
    }

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

    private void openLogin() {
        new Login(usuarioServicio, productoServicio).setVisible(true);
        dispose();
    }
}