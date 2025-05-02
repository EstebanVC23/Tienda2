package com.store.view.components.dialogs.admin;

import com.store.models.Usuario;
import com.store.services.UsuarioServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.utils.PasswordUtils;
import com.store.view.components.shared.FormInputComponents;
import com.store.view.components.dialogs.constants.UserFormDialogConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo para el formulario de creación y edición de usuarios del sistema.
 * Proporciona campos para todos los datos necesarios de un usuario y maneja
 * tanto la creación de nuevos usuarios como la edición de existentes.
 */
public class UserFormDialog extends BaseEntityFormDialog {
    private final UsuarioServicioImpl usuarioServicio;
    private final Usuario usuario;
    private final UserFormDialogConstants constants;
    private boolean isNewUser;
    
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JTextField telefonoField;
    private JComboBox<String> rolCombo;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JPanel docPanel;
    private JCheckBox estadoCheck;

    /**
     * Construye un nuevo diálogo de formulario de usuario.
     * @param parent Ventana padre para el posicionamiento
     * @param usuario Usuario a editar o null para crear uno nuevo
     * @param usuarioServicio Servicio para operaciones de usuario
     */
    public UserFormDialog(Window parent, Usuario usuario, UsuarioServicioImpl usuarioServicio) {
        super(parent, usuario == null ? "Nuevo Usuario" : "Editar Usuario");
        this.usuarioServicio = usuarioServicio;
        this.usuario = usuario == null ? new Usuario() : usuario;
        this.constants = new UserFormDialogConstants();
        this.isNewUser = usuario == null;
        
        setSize(constants.WIDTH, isNewUser ? constants.HEIGHT_CREATE : constants.HEIGHT_EDIT);
        setupLayout();
        centerOnParent();
    }

    /**
     * Configura el diseño y componentes del formulario.
     */
    @Override
    protected void setupLayout() {
        setupCommonLayout();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        nombreField = addTextField("Nombre:", usuario.getNombre());
        apellidoField = addTextField("Apellido:", usuario.getApellido());
        emailField = addTextField("Email:", usuario.getEmail());
        
        docPanel = FormInputComponents.createDocumentInputPanel(
            usuario.getTipoDocumento(), usuario.getNumeroDocumento());
        addCustomField("Documento:", docPanel);
        
        telefonoField = addTextField("Teléfono:", usuario.getTelefono());
        rolCombo = addComboBox("Rol:", 
            Arrays.asList("Administrador", "Vendedor", "Almacenero"), 
            usuario.getRol());
        
        if (isNewUser) {
            passwordField = FormInputComponents.createPasswordField();
            addCustomField("Contraseña:", passwordField);
            
            confirmPasswordField = FormInputComponents.createPasswordField();
            addCustomField("Confirmar Contraseña:", confirmPasswordField);
        }
        
        estadoCheck = createStatusCheckbox();
        addCustomField("", estadoCheck);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(this::saveForm), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    /**
     * Crea el checkbox para el estado activo/inactivo del usuario.
     * @return JCheckBox configurado
     */
    private JCheckBox createStatusCheckbox() {
        JCheckBox check = new JCheckBox(constants.ACTIVE_USER_TEXT);
        check.setFont(Fonts.BODY);
        check.setBackground(Color.WHITE);
        check.setSelected(usuario.getEstado());
        return check;
    }

    /**
     * Maneja la lógica de guardado del formulario.
     */
    @Override
    protected void saveForm() {
        try {
            if (isNewUser) {
                validatePasswords();
                usuario.setPassword(PasswordUtils.encrypt(new String(passwordField.getPassword())));
            }
            
            updateUserData();
            
            boolean success = isNewUser ? 
                usuarioServicio.crearUsuario(usuario) : 
                usuarioServicio.actualizarUsuario(usuario);
            
            handleSaveResult(success);
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Valida que las contraseñas coincidan y cumplan con los requisitos.
     * @throws IllegalArgumentException Si las contraseñas no son válidas
     */
    private void validatePasswords() {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
    }

    /**
     * Actualiza los datos del usuario con los valores del formulario.
     */
    @SuppressWarnings("unchecked")
    private void updateUserData() {
        JComboBox<String> tipoDocCombo = (JComboBox<String>) docPanel.getComponent(0);
        JTextField numDocField = (JTextField) docPanel.getComponent(1);
        
        usuario.setNombre(nombreField.getText().trim());
        usuario.setApellido(apellidoField.getText().trim());
        usuario.setEmail(emailField.getText().trim());
        usuario.setTipoDocumento(tipoDocCombo.getSelectedItem().toString());
        usuario.setNumeroDocumento(numDocField.getText().trim());
        usuario.setTelefono(telefonoField.getText().trim());
        usuario.setRol(rolCombo.getSelectedItem().toString());
        usuario.setEstadoActivo(estadoCheck.isSelected());
    }

    /**
     * Maneja el resultado de la operación de guardado.
     * @param success Indica si la operación fue exitosa
     */
    private void handleSaveResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, 
                constants.SUCCESS_MESSAGE, 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError(constants.ERROR_MESSAGE);
        }
    }
}