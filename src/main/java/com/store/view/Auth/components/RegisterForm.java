package com.store.view.auth.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.forauth.CampoEntrada;
import com.store.view.auth.constants.AuthConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario de registro personalizado que contiene todos los campos necesarios
 * para registrar un nuevo usuario en el sistema.
 */
public class RegisterForm extends JPanel {
    private CampoEntrada campoNombre;
    private CampoEntrada campoApellido;
    private CampoEntrada campoEmail;
    private JComboBox<String> comboTipoDocumento;
    private CampoEntrada campoNumeroDocumento;
    private CampoEntrada campoDireccion;
    private CampoEntrada campoTelefono;
    private CampoEntrada campoPassword;
    private CampoEntrada campoConfirmarPassword;

    /**
     * Constructor que inicializa el formulario de registro.
     */
    public RegisterForm() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initializeFields();
        setupFormLayout();
    }

    /**
     * Inicializa todos los campos del formulario.
     */
    private void initializeFields() {
        campoNombre = createTextField(AuthConstants.NAME_LABEL);
        campoApellido = createTextField(AuthConstants.LASTNAME_LABEL);
        campoEmail = createTextField(AuthConstants.EMAIL_LABEL);
        comboTipoDocumento = createDocumentTypeComboBox();
        campoNumeroDocumento = createNumberField(AuthConstants.DOCUMENT_NUMBER_LABEL);
        campoDireccion = createTextField(AuthConstants.ADDRESS_LABEL);
        campoTelefono = createTextField(AuthConstants.PHONE_LABEL);
        campoPassword = createPasswordField(AuthConstants.PASSWORD_LABEL);
        campoConfirmarPassword = createPasswordField(AuthConstants.CONFIRM_PASSWORD_LABEL);
    }

    /**
     * Configura el diseño del formulario usando GridBagLayout.
     */
    private void setupFormLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(campoNombre, gbc);

        gbc.gridy = 1;
        add(campoApellido, gbc);

        gbc.gridy = 2;
        add(campoEmail, gbc);

        gbc.gridy = 3;
        add(createDocumentTypePanel(), gbc);

        gbc.gridy = 4;
        add(campoNumeroDocumento, gbc);

        gbc.gridy = 5;
        add(campoDireccion, gbc);

        gbc.gridy = 6;
        add(campoTelefono, gbc);

        gbc.gridy = 7;
        add(campoPassword, gbc);

        gbc.gridy = 8;
        add(campoConfirmarPassword, gbc);
    }

    /**
     * Crea un campo de texto estándar.
     * @param label Etiqueta del campo
     * @return CampoEntrada configurado
     */
    private CampoEntrada createTextField(String label) {
        return new CampoEntrada(
            AuthConstants.TEXT_FIELD_TYPE, 
            label, 
            AuthConstants.REGISTER_INPUT_SIZE.width, 
            AuthConstants.REGISTER_INPUT_SIZE.height
        );
    }

    /**
     * Crea un campo numérico.
     * @param label Etiqueta del campo
     * @return CampoEntrada configurado para números
     */
    private CampoEntrada createNumberField(String label) {
        return new CampoEntrada(
            AuthConstants.NUMBER_FIELD_TYPE, 
            label, 
            AuthConstants.REGISTER_INPUT_SIZE.width, 
            AuthConstants.REGISTER_INPUT_SIZE.height
        );
    }

    /**
     * Crea un campo de contraseña.
     * @param label Etiqueta del campo
     * @return CampoEntrada configurado para contraseñas
     */
    private CampoEntrada createPasswordField(String label) {
        return new CampoEntrada(
            AuthConstants.PASSWORD_FIELD_TYPE, 
            label, 
            AuthConstants.REGISTER_INPUT_SIZE.width, 
            AuthConstants.REGISTER_INPUT_SIZE.height
        );
    }

    /**
     * Crea un JComboBox para seleccionar el tipo de documento.
     * @return JComboBox configurado con los tipos de documento
     */
    private JComboBox<String> createDocumentTypeComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(AuthConstants.DOCUMENT_TYPES);
        comboBox.setPreferredSize(AuthConstants.REGISTER_INPUT_SIZE);
        comboBox.setFont(Fonts.BODY);
        return comboBox;
    }

    /**
     * Crea un panel que contiene el combo box de tipo de documento con su etiqueta.
     * @return JPanel con el combo box y su etiqueta
     */
    private JPanel createDocumentTypePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel label = new JLabel(AuthConstants.DOCUMENT_TYPE_LABEL);
        label.setForeground(Colors.SECONDARY_TEXT);
        label.setFont(Fonts.BOLD_BODY);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(comboTipoDocumento, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Obtiene el nombre ingresado en el formulario.
     * @return String con el nombre
     */
    public String getNombre() {
        return campoNombre.getText().trim();
    }

    /**
     * Obtiene el apellido ingresado en el formulario.
     * @return String con el apellido
     */
    public String getApellido() {
        return campoApellido.getText().trim();
    }

    /**
     * Obtiene el email ingresado en el formulario.
     * @return String con el email
     */
    public String getEmail() {
        return campoEmail.getText().trim();
    }

    /**
     * Obtiene el tipo de documento seleccionado.
     * @return String con el tipo de documento
     */
    public String getTipoDocumento() {
        return (String) comboTipoDocumento.getSelectedItem();
    }

    /**
     * Obtiene el número de documento ingresado.
     * @return String con el número de documento
     */
    public String getNumeroDocumento() {
        return campoNumeroDocumento.getText().trim();
    }

    /**
     * Obtiene la dirección ingresada.
     * @return String con la dirección
     */
    public String getDireccion() {
        return campoDireccion.getText().trim();
    }

    /**
     * Obtiene el teléfono ingresado.
     * @return String con el teléfono
     */
    public String getTelefono() {
        return campoTelefono.getText().trim();
    }

    /**
     * Obtiene la contraseña ingresada.
     * @return String con la contraseña
     */
    public String getPassword() {
        return campoPassword.getText();
    }

    /**
     * Obtiene la confirmación de contraseña ingresada.
     * @return String con la confirmación de contraseña
     */
    public String getConfirmarPassword() {
        return campoConfirmarPassword.getText();
    }
}