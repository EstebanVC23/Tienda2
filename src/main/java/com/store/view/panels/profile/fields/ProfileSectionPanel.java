package com.store.view.panels.profile.fields;

import com.store.models.Usuario;
import com.store.view.panels.profile.ProfileFieldFactory;
import com.store.view.panels.profile.UserProfileConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Panel que muestra y permite la edición de la información del perfil de usuario.
 * Contiene campos para los datos personales, información de contacto y documentos.
 * El diseño utiliza GridBagLayout para una disposición organizada de los campos.
 */
public class ProfileSectionPanel extends JPanel {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JTextField telefonoField;
    private JComboBox<String> tipoDocumentoField;
    private JTextField numeroDocumentoField;
    private JTextField direccionField;

    /**
     * Construye un nuevo panel de sección de perfil con los datos del usuario.
     * @param usuario El objeto Usuario que contiene los datos a mostrar en el perfil
     */
    public ProfileSectionPanel(Usuario usuario) {
        setLayout(new GridBagLayout());
        setBackground(UserProfileConstants.PANEL_BACKGROUND);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UserProfileConstants.BORDER_COLOR),
            BorderFactory.createEmptyBorder(UserProfileConstants.BORDER_PADDING, 
                UserProfileConstants.BORDER_PADDING, 
                UserProfileConstants.BORDER_PADDING, 
                UserProfileConstants.BORDER_PADDING)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = UserProfileConstants.FIELD_INSETS;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        addSectionTitle(gbc, UserProfileConstants.PERSONAL_INFO_TITLE);
        
        nombreField = addEditableField(gbc, "Nombre:", usuario.getNombre());
        apellidoField = addEditableField(gbc, "Apellido:", usuario.getApellido());
        emailField = addEditableField(gbc, "Email:", usuario.getEmail());
        telefonoField = addEditableField(gbc, "Teléfono:", usuario.getTelefono());
        
        tipoDocumentoField = addComboBoxField(gbc, "Tipo Documento:", 
                new String[]{"DNI", "Pasaporte", "Carnet de Extranjería", "RUC"}, 
                usuario.getTipoDocumento());
        numeroDocumentoField = addEditableField(gbc, "Número Documento:", usuario.getNumeroDocumento());
        
        direccionField = addEditableField(gbc, "Dirección:", usuario.getDireccion());
    }

    /**
     * Añade un título de sección al panel.
     * @param gbc Restricciones de diseño para posicionar el título
     * @param title El texto del título a mostrar
     */
    private void addSectionTitle(GridBagConstraints gbc, String title) {
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(UserProfileConstants.SECTION_FONT);
        sectionLabel.setForeground(UserProfileConstants.SECTION_COLOR);
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        gbc.gridwidth = 2;
        add(sectionLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
    }

    /**
     * Añade un campo editable al panel.
     * @param gbc Restricciones de diseño para posicionar el campo
     * @param label Etiqueta descriptiva del campo
     * @param value Valor inicial del campo
     * @return El campo de texto creado
     */
    private JTextField addEditableField(GridBagConstraints gbc, String label, String value) {
        add(ProfileFieldFactory.createLabel(label), gbc);
        gbc.gridx++;
        JTextField field = ProfileFieldFactory.createEditableField(value);
        add(field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        return field;
    }
    
    /**
     * Añade un campo de tipo combo box al panel.
     * @param gbc Restricciones de diseño para posicionar el campo
     * @param label Etiqueta descriptiva del campo
     * @param options Opciones disponibles en el combo box
     * @param selectedValue Valor seleccionado inicialmente
     * @return El combo box creado
     */
    private JComboBox<String> addComboBoxField(GridBagConstraints gbc, String label, String[] options, String selectedValue) {
        add(ProfileFieldFactory.createLabel(label), gbc);
        gbc.gridx++;
        
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(UserProfileConstants.VALUE_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UserProfileConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UserProfileConstants.FIELD_PADDING - 2, 
                UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING - 2, 
                UserProfileConstants.FIELD_PADDING)
        ));
        
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(selectedValue)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
        
        add(comboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        return comboBox;
    }

    /**
     * Obtiene el valor actual del campo Nombre.
     * @return El texto ingresado en el campo Nombre
     */
    public String getNombre() {
        return nombreField.getText();
    }

    /**
     * Obtiene el valor actual del campo Apellido.
     * @return El texto ingresado en el campo Apellido
     */
    public String getApellido() {
        return apellidoField.getText();
    }

    /**
     * Obtiene el valor actual del campo Email.
     * @return El texto ingresado en el campo Email
     */
    public String getEmail() {
        return emailField.getText();
    }

    /**
     * Obtiene el valor actual del campo Teléfono.
     * @return El texto ingresado en el campo Teléfono
     */
    public String getTelefono() {
        return telefonoField.getText();
    }
    
    /**
     * Obtiene el valor seleccionado actual del campo Tipo Documento.
     * @return El ítem seleccionado en el combo box de Tipo Documento
     */
    public String getTipoDocumento() {
        return (String) tipoDocumentoField.getSelectedItem();
    }
    
    /**
     * Obtiene el valor actual del campo Número Documento.
     * @return El texto ingresado en el campo Número Documento
     */
    public String getNumeroDocumento() {
        return numeroDocumentoField.getText();
    }

    /**
     * Obtiene el valor actual del campo Dirección.
     * @return El texto ingresado en el campo Dirección
     */
    public String getDireccion() {
        return direccionField.getText();
    }
}