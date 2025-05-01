package com.store.view.panels.profile.fields;

import com.store.models.Usuario;
import com.store.view.panels.profile.ProfileFieldFactory;
import com.store.view.panels.profile.UserProfileConstants;

import javax.swing.*;
import java.awt.*;

public class ProfileSectionPanel extends JPanel {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JTextField telefonoField;
    private JComboBox<String> tipoDocumentoField;
    private JTextField numeroDocumentoField;
    private JTextField direccionField; // Nuevo campo para la dirección

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
        
        // Nuevo campo para la dirección
        direccionField = addEditableField(gbc, "Dirección:", usuario.getDireccion());
    }

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

    private JTextField addEditableField(GridBagConstraints gbc, String label, String value) {
        add(ProfileFieldFactory.createLabel(label), gbc);
        gbc.gridx++;
        JTextField field = ProfileFieldFactory.createEditableField(value);
        add(field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        return field;
    }
    
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

    public String getNombre() {
        return nombreField.getText();
    }

    public String getApellido() {
        return apellidoField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getTelefono() {
        return telefonoField.getText();
    }
    
    public String getTipoDocumento() {
        return (String) tipoDocumentoField.getSelectedItem();
    }
    
    public String getNumeroDocumento() {
        return numeroDocumentoField.getText();
    }

    public String getDireccion() {
        return direccionField.getText();
    }
}