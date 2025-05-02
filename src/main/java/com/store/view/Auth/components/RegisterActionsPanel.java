package com.store.view.auth.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.auth.constants.AuthConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel que contiene los botones de acción para el formulario de registro.
 * Incluye botones para registrar un nuevo usuario y volver al login.
 */
public class RegisterActionsPanel extends JPanel {
    private CustomButton botonRegistrar;
    private CustomButton botonVolver;

    /**
     * Constructor que inicializa el panel con los botones de acción.
     */
    public RegisterActionsPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initializeButtons();
        setupButtonsLayout();
    }

    /**
     * Inicializa los botones del panel con sus estilos y configuraciones.
     */
    private void initializeButtons() {
        botonRegistrar = new CustomButton(
            AuthConstants.REGISTER_BUTTON_TEXT, 
            Colors.PRIMARY, 
            Colors.ACTIVE_TEXT
        );
        botonRegistrar.setPreferredSize(AuthConstants.BUTTON_SIZE);
        botonRegistrar.setFont(Fonts.BUTTON.deriveFont(AuthConstants.BUTTON_FONT_SIZE));
        
        botonVolver = new CustomButton(
            AuthConstants.BACK_TO_LOGIN_TEXT, 
            Colors.SECONDARY_GRAY, 
            Colors.ACTIVE_TEXT
        );
        botonVolver.setBorder(BorderFactory.createEmptyBorder(
            AuthConstants.BUTTON_PADDING.top, 
            AuthConstants.BUTTON_PADDING.left, 
            AuthConstants.BUTTON_PADDING.bottom, 
            AuthConstants.BUTTON_PADDING.right
        ));
    }

    /**
     * Configura el diseño de los botones dentro del panel.
     */
    private void setupButtonsLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(botonRegistrar, gbc);
        
        gbc.gridx = 1;
        add(botonVolver, gbc);
    }

    /**
     * Añade un ActionListener al botón de registro.
     * @param listener ActionListener para manejar el evento de registro
     */
    public void addRegisterListener(ActionListener listener) {
        botonRegistrar.addActionListener(listener);
    }

    /**
     * Añade un ActionListener al botón de volver.
     * @param listener ActionListener para manejar el evento de volver al login
     */
    public void addBackListener(ActionListener listener) {
        botonVolver.addActionListener(listener);
    }
}