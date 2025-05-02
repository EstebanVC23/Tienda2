package com.store.view.components.dialogs.admin;

import com.store.models.Usuario;
import com.store.services.UsuarioServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.utils.PasswordUtils;
import com.store.view.components.dialogs.FormStyler;
import com.store.view.components.dialogs.constants.UserFormDialogConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.border.EmptyBorder;

public class UserFormDialog extends AbstractFormDialog {
    private final UsuarioServicioImpl usuarioServicio;
    private final Usuario userToEdit;
    private final UserFormDialogConstants constants;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private boolean isNewUser;

    public UserFormDialog(Window parent, Usuario usuario, UsuarioServicioImpl usuarioServicio) {
        super(parent, usuario == null ? "Nuevo Usuario" : "Editar Usuario");
        this.usuarioServicio = usuarioServicio;
        this.userToEdit = usuario == null ? new Usuario() : usuario;
        this.constants = new UserFormDialogConstants();
        this.isNewUser = usuario == null;
        
        setSize(constants.WIDTH, isNewUser ? constants.HEIGHT_CREATE : constants.HEIGHT_EDIT);
        initComponents();
        setupLayout();
        centerOnParent();
    }

    private void initComponents() {
        // Inicializar el panel principal primero
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        // Inicializar el panel del formulario
        formPanel = FormStyler.createFormPanel();
        formPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER));
        
        // Inicializar la etiqueta de error
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Colors.ERROR_RED);
        errorLabel.setFont(Fonts.SMALL);
        
        setContentPane(mainPanel);
    }

    @Override
    protected void setupLayout() {
        JPanel mainPanel = (JPanel) getContentPane();
        
        JTextField nombreField = addTextField("Nombre:", userToEdit.getNombre());
        JTextField apellidoField = addTextField("Apellido:", userToEdit.getApellido());
        JTextField emailField = addTextField("Email:", userToEdit.getEmail());
        
        JPanel docPanel = createDocumentPanel();
        addCustomField("Documento:", docPanel);
        
        JTextField telefonoField = addTextField("Teléfono:", userToEdit.getTelefono());
        JComboBox<String> rolCombo = addComboBox("Rol:", 
            Arrays.asList("Administrador", "Vendedor", "Almacenero"), 
            userToEdit.getRol());
        
        if (isNewUser) {
            passwordField = addPasswordField("Contraseña:");
            confirmPasswordField = addPasswordField("Confirmar Contraseña:");
        }
        
        JCheckBox estadoCheck = createStatusCheckbox();
        addCustomField("", estadoCheck);
        
        formPanel.add(errorLabel);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(() -> saveUser(
            nombreField, apellidoField, emailField, 
            telefonoField, rolCombo, docPanel, estadoCheck
        )), BorderLayout.SOUTH);
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

    private void saveUser(JTextField nombreField, JTextField apellidoField, 
                    JTextField emailField, JTextField telefonoField,
                    JComboBox<String> rolCombo, JPanel docPanel, 
                    JCheckBox estadoCheck) {
        try {
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
                
                String encryptedPassword = PasswordUtils.encrypt(password);
                userToEdit.setPassword(encryptedPassword);
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

    @Override
    protected void saveForm() {
        // Implementación vacía ya que usamos saveUser directamente
    }
}