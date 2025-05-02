package com.store.view.panels.profile.fields;

import com.store.utils.Colors;
import com.store.view.components.buttons.CustomButton;
import com.store.view.panels.profile.UserProfileConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene las acciones disponibles para el perfil de usuario,
 * incluyendo botones para guardar cambios y cambiar contraseña.
 * Diseñado con un layout alineado a la derecha y estilos visuales específicos.
 */
public class ProfileActionsPanel extends JPanel {
    private JButton saveButton;
    private JButton changePasswordButton;

    /**
     * Constructor que inicializa el panel de acciones del perfil.
     * Configura el layout, fondo, bordes y añade los botones con sus estilos correspondientes.
     */
    public ProfileActionsPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        setBackground(UserProfileConstants.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        changePasswordButton = new CustomButton("Cambiar Contraseña", Colors.PRIMARY_BLUE);
        saveButton = new CustomButton("Guardar Cambios", Colors.SECONDARY_BLUE);

        ((CustomButton) changePasswordButton).setHoverColor(Colors.DARK_BLUE);
        ((CustomButton) saveButton).setHoverColor(new Color(13, 71, 161));

        add(changePasswordButton);
        add(saveButton);
    }

    /**
     * Obtiene el botón de guardar cambios.
     * @return Instancia del botón para guardar cambios.
     */
    public JButton getSaveButton() {
        return saveButton;
    }

    /**
     * Obtiene el botón para cambiar contraseña.
     * @return Instancia del botón para cambiar contraseña.
     */
    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
}