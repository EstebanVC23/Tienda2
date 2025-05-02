package com.store.view.components.dialogs.auth;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.forauth.CampoEntrada;
import com.store.view.components.buttons.CustomButton;
import com.store.view.auth.constants.AuthConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Formulario de inicio de sesión personalizado.
 * Proporciona campos para correo electrónico y contraseña, junto con botones de acción.
 */
public class LoginForm extends JPanel {
    private CampoEntrada campoCorreo;
    private CampoEntrada campoPassword;
    private CustomButton botonIngresar;
    private CustomButton botonRegistrarse;

    /**
     * Constructor que inicializa el formulario de login.
     */
    public LoginForm() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initializeFields();
        setupFormLayout();
    }

    /**
     * Inicializa los campos y botones del formulario.
     */
    private void initializeFields() {
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
        
        botonIngresar = new CustomButton(
            AuthConstants.LOGIN_BUTTON_TEXT, 
            Colors.PRIMARY,
            Colors.ACTIVE_TEXT
        );
        botonIngresar.setPreferredSize(AuthConstants.LOGIN_BUTTON_SIZE);
        botonIngresar.setFont(Fonts.BUTTON.deriveFont(AuthConstants.LOGIN_BUTTON_FONT_SIZE));
        botonIngresar.setRound(true);
        
        botonRegistrarse = new CustomButton(
            AuthConstants.REGISTER_LINK_TEXT,
            Colors.BACKGROUND,
            Colors.PRIMARY_BLUE
        );
        botonRegistrarse.setBorder(BorderFactory.createEmptyBorder());
        botonRegistrarse.setContentAreaFilled(false);
        botonRegistrarse.setFont(Fonts.BODY.deriveFont(AuthConstants.SECONDARY_BUTTON_FONT_SIZE));
    }

    /**
     * Configura el diseño del formulario usando GridBagLayout.
     */
    private void setupFormLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = AuthConstants.LOGIN_FORM_PADDING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(campoCorreo, gbc);
        
        gbc.gridy = 1;
        add(campoPassword, gbc);
        
        gbc.gridy = 2;
        add(botonIngresar, gbc);
        
        gbc.gridy = 3;
        add(botonRegistrarse, gbc);
    }

    /**
     * Obtiene el texto ingresado en el campo de correo.
     * @return String con el correo electrónico ingresado
     */
    public String getCorreo() {
        return campoCorreo.getText().trim();
    }

    /**
     * Obtiene el texto ingresado en el campo de contraseña.
     * @return String con la contraseña ingresada
     */
    public String getPassword() {
        return campoPassword.getText().trim();
    }

    /**
     * Añade un ActionListener al botón de ingreso.
     * @param listener ActionListener para el botón de ingreso
     */
    public void addLoginListener(ActionListener listener) {
        botonIngresar.addActionListener(listener);
    }

    /**
     * Añade un ActionListener al botón de registro.
     * @param listener ActionListener para el botón de registro
     */
    public void addRegisterListener(ActionListener listener) {
        botonRegistrarse.addActionListener(listener);
    }
}