package com.store.view.components.dialogs.admin;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.utils.PasswordUtils;
import com.store.view.components.FormStyler;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.dialogs.constants.UserFormDialogConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class UserFormDialog extends JDialog {
    private final UsuarioServicio usuarioServicio;
    private final Usuario userToEdit;
    private final UserFormDialogConstants constants;
    
    private JPanel formPanel;
    private JLabel errorLabel;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private boolean isNewUser;

    public UserFormDialog(Window parent, Usuario usuario, UsuarioServicio usuarioServicio) {
        super(parent, usuario == null ? "Nuevo Usuario" : "Editar Usuario", ModalityType.APPLICATION_MODAL);
        this.usuarioServicio = usuarioServicio;
        this.userToEdit = usuario == null ? new Usuario() : usuario;
        this.constants = new UserFormDialogConstants();
        this.isNewUser = usuario == null;
        
        initUI();
        setupLayout();
    }

    private void initUI() {
        // Ajustar altura según si es nuevo usuario (800px) o edición (650px)
        setSize(constants.WIDTH, isNewUser ? constants.HEIGHT_CREATE : constants.HEIGHT_EDIT);
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        // Panel de formulario
        formPanel = FormStyler.createFormPanel();
        formPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER));
        
        JTextField nombreField = addTextField("Nombre:", userToEdit.getNombre());
        JTextField apellidoField = addTextField("Apellido:", userToEdit.getApellido());
        JTextField emailField = addTextField("Email:", userToEdit.getEmail());
        
        JPanel docPanel = createDocumentPanel();
        addCustomField("Documento:", docPanel);
        
        JTextField telefonoField = addTextField("Teléfono:", userToEdit.getTelefono());
        JComboBox<String> rolCombo = addComboBox("Rol:", 
            Arrays.asList("Administrador", "Vendedor", "Almacenero"), 
            userToEdit.getRol());
        
        // Campos de contraseña solo para nuevo usuario
        if (isNewUser) {
            passwordField = addPasswordField("Contraseña:");
            confirmPasswordField = addPasswordField("Confirmar Contraseña:");
        }
        
        JCheckBox estadoCheck = createStatusCheckbox();
        addCustomField("", estadoCheck);
        
        // Panel de error
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Colors.ERROR_RED);
        errorLabel.setFont(Fonts.SMALL);
        formPanel.add(errorLabel);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(nombreField, apellidoField, emailField, 
                       telefonoField, rolCombo, docPanel, estadoCheck), 
                     BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPasswordField addPasswordField(String label) {
        formPanel.add(FormStyler.createFormLabel(label));
        JPasswordField field = new JPasswordField();
        field.setFont(Fonts.BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, constants.FIELD_SPACING)));
        return field;
    }

    private JTextField addTextField(String label, String value) {
        formPanel.add(FormStyler.createFormLabel(label));
        JTextField field = FormStyler.createFormTextField();
        field.setText(value);
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, constants.FIELD_SPACING)));
        return field;
    }

    private void addCustomField(String label, JComponent component) {
        if (!label.isEmpty()) {
            formPanel.add(FormStyler.createFormLabel(label));
        }
        formPanel.add(component);
        formPanel.add(Box.createRigidArea(new Dimension(0, constants.FIELD_SPACING)));
    }

    private JComboBox<String> addComboBox(String label, List<String> items, String selected) {
        formPanel.add(FormStyler.createFormLabel(label));
        JComboBox<String> combo = FormStyler.createFormComboBox();
        items.forEach(combo::addItem);
        if (selected != null) combo.setSelectedItem(selected);
        formPanel.add(combo);
        formPanel.add(Box.createRigidArea(new Dimension(0, constants.FIELD_SPACING)));
        return combo;
    }

    private JPanel createDocumentPanel() {
        JPanel panel = new JPanel(new BorderLayout(constants.DOCUMENT_FIELD_GAP, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, constants.DOCUMENT_FIELD_HEIGHT));
        
        JComboBox<String> tipoDocCombo = new JComboBox<>();
        tipoDocCombo.addItem("DNI");
        tipoDocCombo.addItem("Pasaporte");
        tipoDocCombo.addItem("Cédula");
        if (userToEdit.getTipoDocumento() != null) {
            tipoDocCombo.setSelectedItem(userToEdit.getTipoDocumento());
        }
        
        JTextField numDocField = FormStyler.createFormTextField();
        numDocField.setText(userToEdit.getNumeroDocumento());
        
        panel.add(tipoDocCombo, BorderLayout.WEST);
        panel.add(numDocField, BorderLayout.CENTER);
        return panel;
    }

    private JCheckBox createStatusCheckbox() {
        JCheckBox check = new JCheckBox(constants.ACTIVE_USER_TEXT);
        check.setFont(Fonts.BODY);
        check.setBackground(Color.WHITE);
        check.setSelected(userToEdit.getEstado());
        return check;
    }

    private JPanel createButtonPanel(JTextField nombreField, JTextField apellidoField, 
                                   JTextField emailField, JTextField telefonoField,
                                   JComboBox<String> rolCombo, JPanel docPanel, 
                                   JCheckBox estadoCheck) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Colors.BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        CustomButton cancelButton = new CustomButton("Cancelar", Colors.SECONDARY_GRAY);
        cancelButton.addActionListener(_ -> dispose());
        
        CustomButton saveButton = new CustomButton("Guardar", Colors.PRIMARY_BLUE);
        saveButton.addActionListener(_ -> saveUser(
            nombreField, apellidoField, emailField, 
            telefonoField, rolCombo, docPanel, estadoCheck
        ));
        
        panel.add(cancelButton);
        panel.add(saveButton);
        return panel;
    }

    private void saveUser(JTextField nombreField, JTextField apellidoField, 
                    JTextField emailField, JTextField telefonoField,
                    JComboBox<String> rolCombo, JPanel docPanel, 
                    JCheckBox estadoCheck) {
        try {
            // Validar contraseña si es nuevo usuario
            if (isNewUser) {
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                
                if (password.isEmpty()) {
                    showError("La contraseña no puede estar vacía");
                    return;
                }
                
                if (!password.equals(confirmPassword)) {
                    showError("Las contraseñas no coinciden");
                    return;
                }
                
                // Encriptar contraseña y asignarla al usuario
                String encryptedPassword = PasswordUtils.encrypt(password);
                userToEdit.setPassword(encryptedPassword); // Usar setPassword() en lugar de setContrasena()
            }
            
            updateUserData(nombreField, apellidoField, emailField, 
                        telefonoField, rolCombo, docPanel, estadoCheck);
            
            boolean success = isNewUser ? 
                usuarioServicio.crearUsuario(userToEdit) : 
                usuarioServicio.actualizarUsuario(userToEdit);
            
            handleSaveResult(success);
        } catch (Exception ex) {
            showError(constants.INVALID_DATA_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateUserData(JTextField nombreField, JTextField apellidoField, 
                              JTextField emailField, JTextField telefonoField,
                              JComboBox<String> rolCombo, JPanel docPanel, 
                              JCheckBox estadoCheck) {
        userToEdit.setNombre(nombreField.getText().trim());
        userToEdit.setApellido(apellidoField.getText().trim());
        userToEdit.setEmail(emailField.getText().trim());
        userToEdit.setTelefono(telefonoField.getText().trim());
        userToEdit.setRol(rolCombo.getSelectedItem().toString());
        
        @SuppressWarnings("unchecked")
        JComboBox<String> tipoDocCombo = (JComboBox<String>) docPanel.getComponent(0);
        JTextField numDocField = (JTextField) docPanel.getComponent(1);
        userToEdit.setTipoDocumento(tipoDocCombo.getSelectedItem().toString());
        userToEdit.setNumeroDocumento(numDocField.getText().trim());
        userToEdit.setEstadoActivo(estadoCheck.isSelected());
    }

    private void handleSaveResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, 
                constants.SUCCESS_MESSAGE, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError(constants.ERROR_MESSAGE);
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}