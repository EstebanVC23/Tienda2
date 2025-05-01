package com.store.view.components.dialogs;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;

public class ChangePasswordDialog extends JDialog {
    private final JPasswordField currentPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;
    private boolean passwordChanged = false;

    public ChangePasswordDialog(JFrame parent) {
        super(parent, "Cambiar Contraseña", true);
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(createLabel("Contraseña Actual:"), gbc);
        gbc.gridx++;
        currentPasswordField.setFont(Fonts.BODY);
        add(currentPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel("Nueva Contraseña:"), gbc);
        gbc.gridx++;
        newPasswordField.setFont(Fonts.BODY);
        add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(createLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx++;
        confirmPasswordField.setFont(Fonts.BODY);
        add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        add(createConfirmButton(), gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BOLD_BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    private JButton createConfirmButton() {
        JButton button = new JButton("Confirmar");
        button.setFont(Fonts.BUTTON);
        button.setForeground(Color.WHITE);
        button.setBackground(Colors.PRIMARY_BLUE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.PRIMARY_BLUE.darker()),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addActionListener(_ -> validatePasswordChange());
        return button;
    }

    private void validatePasswordChange() {
        String newPass = new String(newPasswordField.getPassword());
        String confirmPass = new String(confirmPasswordField.getPassword());
        
        if(!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        passwordChanged = true;
        dispose();
    }

    public String getCurrentPassword() {
        return new String(currentPasswordField.getPassword());
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }
}