package com.store.view.panels.profile.fields;

import com.store.utils.Colors;
import com.store.view.components.buttons.CustomButton;
import com.store.view.panels.profile.UserProfileConstants;

import javax.swing.*;
import java.awt.*;

public class ProfileActionsPanel extends JPanel {
    private JButton saveButton;
    private JButton changePasswordButton;

    public ProfileActionsPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        setBackground(UserProfileConstants.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        changePasswordButton = new CustomButton("Cambiar Contraseña", Colors.PRIMARY_BLUE);
        saveButton = new CustomButton("Guardar Cambios", Colors.SECONDARY_BLUE);

        // Añadir sombra y efecto hover a los botones
        ((CustomButton) changePasswordButton).setHoverColor(Colors.DARK_BLUE);
        ((CustomButton) saveButton).setHoverColor(new Color(13, 71, 161));

        add(changePasswordButton);
        add(saveButton);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }
}