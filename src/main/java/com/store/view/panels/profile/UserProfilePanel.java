package com.store.view.panels.profile;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.view.panels.profile.fields.*;
import com.store.view.components.dialogs.ChangePasswordDialog;

import javax.swing.*;
import java.awt.*;

public class UserProfilePanel extends JPanel {
    private final UserProfileController controller;
    private ProfileSectionPanel profileSection;
    private ProfileActionsPanel actionsPanel;

    public UserProfilePanel(UsuarioServicio usuarioServicio, Usuario usuario) {
        this.controller = new UserProfileController(usuarioServicio, usuario);
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        profileSection = new ProfileSectionPanel(controller.getUsuario());
        actionsPanel = new ProfileActionsPanel();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(0, 15));
        setBackground(UserProfileConstants.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(
            UserProfileConstants.BORDER_PADDING, 
            UserProfileConstants.BORDER_PADDING, 
            UserProfileConstants.BORDER_PADDING, 
            UserProfileConstants.BORDER_PADDING));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(profileSection, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UserProfileConstants.BACKGROUND);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UserProfileConstants.BORDER_COLOR));

        JLabel titleLabel = new JLabel(UserProfileConstants.TITLE);
        titleLabel.setFont(UserProfileConstants.TITLE_FONT);
        titleLabel.setForeground(UserProfileConstants.TITLE_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        return headerPanel;
    }

    private void setupListeners() {
        actionsPanel.getSaveButton().addActionListener(_ -> handleSaveProfile());
        actionsPanel.getChangePasswordButton().addActionListener(_ -> handleChangePassword());
    }

    private void handleSaveProfile() {
        boolean resultado = controller.actualizarPerfil(
            profileSection.getNombre(),
            profileSection.getApellido(),
            profileSection.getEmail(),
            profileSection.getTelefono(),
            profileSection.getTipoDocumento(),
            profileSection.getNumeroDocumento(),
            profileSection.getDireccion() // Ahora obtenemos la dirección de profileSection
        );
        
        if(resultado) {
            JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el perfil", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleChangePassword() {
        ChangePasswordDialog dialog = new ChangePasswordDialog((JFrame)SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        
        if(dialog.isPasswordChanged()) {
            boolean cambioExitoso = controller.cambiarContraseña(
                dialog.getCurrentPassword(),
                dialog.getNewPassword()
            );
            
            if(cambioExitoso) {
                JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}