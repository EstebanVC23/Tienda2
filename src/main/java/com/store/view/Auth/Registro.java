package com.store.view.Auth;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.services.ProductoServicio;
import com.store.view.components.forauth.CampoEntrada;
import com.store.view.components.forauth.PanelIzquierdoInicio;
import com.store.view.components.buttons.CustomButton;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.Auth.constants.AuthConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Registro extends JFrame {
    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;

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
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro de Usuario");
        setSize(AuthConstants.WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
    }

    private void inicializarComponentes() {
        crearPanelIzquierdo();
        crearPanelDerecho();
        configurarEventos();
    }

    private void crearPanelIzquierdo() {
        PanelIzquierdoInicio panelIzquierdo = new PanelIzquierdoInicio(
            AuthConstants.REGISTER_TITLE, 
            AuthConstants.REGISTER_SUBTITLE
        );
        add(panelIzquierdo);
    }

    private void configurarEventos() {
        botonRegistrar.addActionListener(this::procesarRegistro);
        botonVolver.addActionListener(_ -> abrirLogin());
    }

    private void crearPanelDerecho() {
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Colors.BACKGROUND);
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(
            AuthConstants.PANEL_PADDING.top,
            AuthConstants.PANEL_PADDING.left,
            AuthConstants.PANEL_PADDING.bottom,
            AuthConstants.PANEL_PADDING.right
        ));
    
        JLabel labelTitulo = new JLabel("Registrar Usuario");
        labelTitulo.setForeground(Colors.PRIMARY_TEXT);
        labelTitulo.setFont(Fonts.TITLE.deriveFont(AuthConstants.TITLE_FONT_SIZE));
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
    
        JPanel panelFormulario = new JPanel();
        panelFormulario.setOpaque(false);
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
    
        int anchoCampo = 300;
        int altoCampo = 40;
    
        campoNombre = new CampoEntrada("texto", "Nombre", anchoCampo, altoCampo);
        campoApellido = new CampoEntrada("texto", "Apellido", anchoCampo, altoCampo);
        campoEmail = new CampoEntrada("texto", "Correo electrónico", anchoCampo, altoCampo);
    
        // Crear panel contenedor para el tipo de documento
        JPanel panelTipoDocumento = new JPanel();
        panelTipoDocumento.setLayout(new BoxLayout(panelTipoDocumento, BoxLayout.Y_AXIS));
        panelTipoDocumento.setOpaque(false);
        panelTipoDocumento.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label para el tipo de documento
        JLabel labelDocumento = new JLabel("Tipo Documento");
        labelDocumento.setForeground(Colors.SECONDARY_TEXT);
        labelDocumento.setFont(Fonts.BODY);
        labelDocumento.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTipoDocumento.add(labelDocumento);
        
        // ComboBox para seleccionar tipo de documento
        comboTipoDocumento = new JComboBox<>(new String[]{"DNI", "Pasaporte", "Cédula"});
        comboTipoDocumento.setMaximumSize(new Dimension(anchoCampo, altoCampo));
        comboTipoDocumento.setFont(Fonts.BODY);
        comboTipoDocumento.setBackground(Color.WHITE);
        comboTipoDocumento.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTipoDocumento.add(comboTipoDocumento);
    
        campoNumeroDocumento = new CampoEntrada("entero", "Número Documento", anchoCampo, altoCampo);
        campoDireccion = new CampoEntrada("texto", "Dirección", anchoCampo, altoCampo);
        campoTelefono = new CampoEntrada("texto", "Teléfono", anchoCampo, altoCampo);
        campoPassword = new CampoEntrada("password", "Contraseña", anchoCampo, altoCampo);
        campoConfirmarPassword = new CampoEntrada("password", "Confirmar Contraseña", anchoCampo, altoCampo);
    
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoNombre);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoApellido);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoEmail);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(panelTipoDocumento);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoNumeroDocumento);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoDireccion);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoTelefono);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoPassword);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(campoConfirmarPassword);
        panelFormulario.add(Box.createVerticalGlue());
    
        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
    
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);
        
        botonRegistrar = new CustomButton("REGISTRAR", Colors.PRIMARY, Color.WHITE);
        botonRegistrar.setPreferredSize(AuthConstants.BUTTON_SIZE);
        botonRegistrar.setFont(Fonts.BUTTON.deriveFont(AuthConstants.BUTTON_FONT_SIZE));
        
        botonVolver = new CustomButton("Volver al Login", Colors.SECONDARY_GRAY, Color.WHITE);
        botonVolver.setBorder(BorderFactory.createEmptyBorder(
            AuthConstants.BUTTON_PADDING.top,
            AuthConstants.BUTTON_PADDING.left,
            AuthConstants.BUTTON_PADDING.bottom,
            AuthConstants.BUTTON_PADDING.right
        ));
        
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonVolver);
    
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        add(panelDerecho);
    }
    
    private void procesarRegistro(ActionEvent e) {
        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String email = campoEmail.getText().trim();
        String tipoDocumento = (String)comboTipoDocumento.getSelectedItem();
        String numeroDocumento = campoNumeroDocumento.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String password = campoPassword.getText();
        String confirmarPassword = campoConfirmarPassword.getText();
    
        if (validarCampos(nombre, apellido, email, numeroDocumento, 
                         direccion, telefono, password, confirmarPassword)) {
            registrarNuevoUsuario(nombre, apellido, email, tipoDocumento, 
                                numeroDocumento, direccion, telefono, password);
        }
    }

    private boolean validarCampos(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    AuthConstants.EMPTY_FIELDS, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        }

        if (!campos[6].equals(campos[7])) {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.PASSWORD_MISMATCH, 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        if (usuarioServicio.obtenerUsuarioPorEmail(campos[2]) != null) {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.EMAIL_EXISTS, 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        return true;
    }

    private void registrarNuevoUsuario(String nombre, String apellido, String email,
                                     String tipoDocumento, String numeroDocumento,
                                     String direccion, String telefono, String password) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setTipoDocumento(tipoDocumento);
        nuevoUsuario.setNumeroDocumento(numeroDocumento);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setRol("USER");
        nuevoUsuario.setEstadoActivo(true);

        if (usuarioServicio.crearUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.SUCCESS_REGISTER, 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE
            );
            abrirLogin();
        } else {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.ERROR_REGISTER, 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void abrirLogin() {
        new Login(usuarioServicio, productoServicio).setVisible(true);
        dispose();
    }
}