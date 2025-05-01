package com.store.view.Auth;

import com.store.models.Usuario;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.Auth.constants.AuthConstants;
import com.store.view.AdminView.AdminView;
import com.store.view.UserView.UserView;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.forauth.CampoEntrada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends AuthBaseFrame {
    private CampoEntrada campoCorreo;
    private CampoEntrada campoPassword;
    private CustomButton botonIngresar;
    private CustomButton botonRegistrarse;

    public Login(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        super(usuarioServicio, productoServicio, "Login");
        initializeComponents();
    }

    private void initializeComponents() {
        addLeftPanel(AuthConstants.LOGIN_TITLE, AuthConstants.LOGIN_SUBTITLE);
        createRightPanelContent();
    }

    private void createRightPanelContent() {
        JPanel panelDerecho = createRightPanel();
        
        // Panel título
        JLabel labelTitulo = createTitleLabel(AuthConstants.LOGIN_FORM_TITLE);
        panelDerecho.add(labelTitulo, BorderLayout.NORTH);
        
        // Panel formulario
        JPanel panelFormulario = createFormPanel();
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = AuthConstants.LOGIN_FORM_PADDING;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Campos de entrada
        campoCorreo = new CampoEntrada(
            AuthConstants.TEXT_FIELD_TYPE, 
            AuthConstants.EMAIL_LABEL, 
            AuthConstants.INPUT_FIELD_SIZE.width, 
            AuthConstants.INPUT_FIELD_SIZE.height
        );
        
        campoPassword = new CampoEntrada(
            AuthConstants.PASSWORD_FIELD_TYPE, 
            AuthConstants.PASSWORD_LABEL, 
            AuthConstants.INPUT_FIELD_SIZE.width, 
            AuthConstants.INPUT_FIELD_SIZE.height
        );
        
        // Botón principal
        botonIngresar = new CustomButton(
            AuthConstants.LOGIN_BUTTON_TEXT, 
            Colors.PRIMARY,
            Colors.ACTIVE_TEXT
        );
        botonIngresar.setPreferredSize(AuthConstants.LOGIN_BUTTON_SIZE);
        botonIngresar.setFont(Fonts.BUTTON.deriveFont(AuthConstants.LOGIN_BUTTON_FONT_SIZE));
        botonIngresar.setRound(true);
        
        // Botón secundario (enlace)
        botonRegistrarse = new CustomButton(
            AuthConstants.REGISTER_LINK_TEXT,
            Colors.BACKGROUND, // Fondo transparente
            Colors.PRIMARY_BLUE // Texto azul
        );
        botonRegistrarse.setBorder(BorderFactory.createEmptyBorder());
        botonRegistrarse.setContentAreaFilled(false);
        botonRegistrarse.setFont(Fonts.BODY.deriveFont(AuthConstants.SECONDARY_BUTTON_FONT_SIZE));
        botonRegistrarse.setHoverColor(Colors.BACKGROUND);
        botonRegistrarse.setPressedColor(Colors.BACKGROUND);

        // Agregar componentes al formulario
        panelFormulario.add(campoCorreo, gbc);
        panelFormulario.add(campoPassword, gbc);
        panelFormulario.add(botonIngresar, gbc);
        panelFormulario.add(botonRegistrarse, gbc);

        panelDerecho.add(panelFormulario, BorderLayout.CENTER);
        add(panelDerecho);
        
        configureEvents();
    }

    private void configureEvents() {
        botonIngresar.addActionListener(this::handleLogin);
        botonRegistrarse.addActionListener(_ -> openRegister());
    }

    private void handleLogin(ActionEvent e) {
        String correo = campoCorreo.getText().trim();
        String password = campoPassword.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.EMPTY_FIELDS_LOGIN, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setEmail(correo);
        usuarioLogin.setPassword(password);

        if (!usuarioServicio.validarUsuario(usuarioLogin)) {
            JOptionPane.showMessageDialog(this, 
                AuthConstants.LOGIN_ERROR, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario dataUser = usuarioServicio.obtenerUsuarioPorEmail(correo);
        JOptionPane.showMessageDialog(this, 
            String.format(AuthConstants.LOGIN_SUCCESS, dataUser.getNombre()), 
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

        openUserView(dataUser);
    }

    private void openUserView(Usuario usuario) {
        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            new AdminView(usuario, usuarioServicio, productoServicio).setVisible(true);
        } else {
            new UserView(usuario, productoServicio, usuarioServicio).setVisible(true);
        }
        dispose();
    }

    private void openRegister() {
        new Registro(usuarioServicio, productoServicio).setVisible(true);
        dispose();
    }
}