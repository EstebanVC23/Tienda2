package com.store.view.AdminView;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel que contiene el botón de cierre de sesión para la interfaz de administración.
 * Proporciona un diseño consistente y estilizado para el logout.
 */
public class LogoutPanel extends JPanel {

    /**
     * Crea un nuevo panel de logout.
     * @param logoutAction ActionListener para manejar el evento de cierre de sesión
     */
    public LogoutPanel(ActionListener logoutAction) {
        super(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        initPanel();
        add(createLogoutButton(logoutAction));
    }

    /**
     * Inicializa las propiedades del panel.
     */
    private void initPanel() {
        setBackground(Colors.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 20));
    }

    /**
     * Crea y configura el botón de cierre de sesión.
     * @param logoutAction ActionListener para el botón
     * @return Botón de logout configurado
     */
    private CustomButton createLogoutButton(ActionListener logoutAction) {
        CustomButton button = new CustomButton(
            "Cerrar Sesión", 
            Colors.LOGOUT_RED, 
            Colors.ACTIVE_TEXT,
            Fonts.BUTTON
        );
        button.setHoverColor(Colors.LOGOUT_RED_DARK);
        button.addActionListener(logoutAction);
        return button;
    }
}