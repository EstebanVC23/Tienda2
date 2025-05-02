package com.store.view.panels.profile;

import com.store.models.Usuario;
import com.store.services.UsuarioServicioImpl;
import com.store.view.panels.BasePanel;
import com.store.view.panels.profile.fields.*;
import com.store.view.components.dialogs.ChangePasswordDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Panel principal que muestra y permite la edición del perfil de usuario.
 * Contiene secciones para información personal y acciones como guardar cambios o cambiar contraseña.
 * Extiende de BasePanel para mantener consistencia en la interfaz.
 */
public class UserProfilePanel extends BasePanel {
    private final UserProfileController controller;
    private ProfileSectionPanel profileSection;
    private ProfileActionsPanel actionsPanel;

    /**
     * Construye un nuevo panel de perfil de usuario.
     * @param usuarioServicio Servicio para operaciones con usuarios
     * @param usuario Usuario cuyo perfil se mostrará y editará
     */
    public UserProfilePanel(UsuarioServicioImpl usuarioServicio, Usuario usuario) {
        this.controller = new UserProfileController(usuarioServicio, usuario);
        initComponents();
        setupLayout();
        setupListeners();
    }

    /**
     * Inicializa los componentes principales del panel.
     */
    private void initComponents() {
        profileSection = new ProfileSectionPanel(controller.getUsuario());
        actionsPanel = new ProfileActionsPanel();
    }

    /**
     * Configura el diseño del panel y añade los componentes.
     */
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

    /**
     * Crea el panel del encabezado con el título principal.
     * @return JPanel configurado como encabezado
     */
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

    /**
     * Configura los listeners para los botones de acciones.
     */
    private void setupListeners() {
        actionsPanel.getSaveButton().addActionListener(_ -> handleSaveProfile());
        actionsPanel.getChangePasswordButton().addActionListener(_ -> handleChangePassword());
    }

    /**
     * Maneja el evento de guardar el perfil, recolectando los datos y actualizando mediante el controlador.
     */
    private void handleSaveProfile() {
        boolean resultado = controller.actualizarPerfil(
            profileSection.getNombre(),
            profileSection.getApellido(),
            profileSection.getEmail(),
            profileSection.getTelefono(),
            profileSection.getTipoDocumento(),
            profileSection.getNumeroDocumento(),
            profileSection.getDireccion()
        );
        
        if(resultado) {
            showSelectionMessage("Perfil actualizado correctamente", "Éxito");
        } else {
            showSelectionMessage("Error al actualizar el perfil", "Error");
        }
    }

    /**
     * Maneja el evento de cambiar contraseña, mostrando el diálogo correspondiente
     * y procesando el resultado mediante el controlador.
     */
    private void handleChangePassword() {
        ChangePasswordDialog dialog = new ChangePasswordDialog((JFrame)SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        
        if(dialog.isPasswordChanged()) {
            boolean cambioExitoso = controller.cambiarContraseña(
                dialog.getCurrentPassword(),
                dialog.getNewPassword()
            );
            
            if(cambioExitoso) {
                showSelectionMessage("Contraseña cambiada correctamente", "Éxito");
            } else {
                showSelectionMessage("Contraseña actual incorrecta", "Error");
            }
        }
    }

    /**
     * Método heredado de BasePanel. No implementa funcionalidad en este panel.
     */
    @Override
    public void refreshTable() {
        // No se requiere implementación para este panel
    }
}