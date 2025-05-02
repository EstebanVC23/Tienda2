package com.store.view.components.dialogs;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.dialogs.constants.FormDialogConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo base para formularios de administración.
 * Proporciona una estructura común para formularios con campos de entrada,
 * validación y botones de acción.
 */
public abstract class FormDialog extends JDialog {
    protected JPanel formPanel;
    protected GridBagConstraints gbc;
    protected Map<String, JComponent> formFields;
    protected JButton saveButton;
    protected final FormDialogConstants constants;

    /**
     * Crea un nuevo diálogo de formulario.
     * @param parent Panel padre para centrar el diálogo
     * @param title Título del diálogo
     * @param constants Constantes de configuración del diálogo
     */
    public FormDialog(JPanel parent, String title, FormDialogConstants constants) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), title, true);
        this.constants = constants;
        initializeDialog();
        initializeFormPanel();
        initializeButtonPanel();
    }

    /**
     * Configura las propiedades básicas del diálogo.
     */
    private void initializeDialog() {
        setSize(constants.WIDTH, constants.HEIGHT);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa el panel principal del formulario con scroll.
     */
    private void initializeFormPanel() {
        formFields = new HashMap<>();
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(
            constants.FORM_PADDING, constants.FORM_PADDING, 
            constants.FORM_PADDING, constants.FORM_PADDING));
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(
            constants.FIELD_VERTICAL_INSET, 
            constants.FIELD_HORIZONTAL_INSET,
            constants.FIELD_VERTICAL_INSET, 
            constants.FIELD_HORIZONTAL_INSET);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Configura el panel de botones con acciones de guardar y cancelar.
     */
    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(
            FlowLayout.RIGHT, 
            constants.BUTTON_HGAP, 
            constants.BUTTON_VGAP));
        
        buttonPanel.setBorder(new EmptyBorder(
            constants.BUTTON_PADDING, constants.BUTTON_PADDING,
            constants.BUTTON_PADDING, constants.BUTTON_PADDING));
        
        CustomButton cancelButton = new CustomButton(constants.CANCEL_TEXT, Colors.SECONDARY_GRAY);
        cancelButton.addActionListener(_ -> dispose());
        
        saveButton = new CustomButton(constants.SAVE_TEXT, Colors.PRIMARY_BLUE);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Añade un campo al formulario.
     * @param label Etiqueta del campo
     * @param component Componente de entrada
     */
    protected void addFormField(String label, JComponent component) {
        addFormField(label, component, false);
    }

    /**
     * Añade un campo al formulario con opción de solo lectura.
     * @param label Etiqueta del campo
     * @param component Componente de entrada
     * @param isReadOnly Indica si el campo es de solo lectura
     */
    protected void addFormField(String label, JComponent component, boolean isReadOnly) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(Fonts.SECTION_TITLE);
        
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(jLabel, gbc);
        
        component.setFont(Fonts.BODY);
        if (component instanceof JTextField) {
            ((JTextField) component).setEditable(!isReadOnly);
        }
        
        gbc.gridx = 1;
        formPanel.add(component, gbc);
        
        formFields.put(label.replace(":", "").trim(), component);
    }

    /**
     * Añade un campo de texto al formulario.
     * @param label Etiqueta del campo
     * @param value Valor inicial del campo
     * @return Campo de texto creado
     */
    protected JTextField addFormField(String label, String value) {
        JTextField field = new JTextField(value);
        field.setFont(Fonts.BODY);
        addFormField(label, field);
        return field;
    }

    /**
     * Añade un campo de solo lectura al formulario.
     * @param label Etiqueta del campo
     * @param value Valor a mostrar
     */
    protected void addReadOnlyField(String label, String value) {
        JTextField field = new JTextField(value);
        field.setEditable(false);
        field.setFont(Fonts.BODY);
        addFormField(label, field);
    }

    /**
     * Añade un campo de selección (combobox) al formulario.
     * @param label Etiqueta del campo
     * @param items Lista de elementos seleccionables
     * @param selectedItem Elemento preseleccionado
     * @return Combobox creado
     */
    protected <T> JComboBox<T> addComboField(String label, List<T> items, T selectedItem) {
        JComboBox<T> combo = new JComboBox<>();
        items.forEach(combo::addItem);
        if (selectedItem != null) combo.setSelectedItem(selectedItem);
        combo.setFont(Fonts.BODY);
        addFormField(label, combo);
        return combo;
    }

    /**
     * Establece la acción del botón guardar.
     * @param action Acción a ejecutar al guardar
     */
    protected void setSaveAction(ActionListener action) {
        saveButton.addActionListener(action);
    }

    /**
     * Obtiene el valor de un campo del formulario.
     * @param fieldName Nombre del campo (sin ":")
     * @return Valor del campo como String
     */
    protected String getFieldValue(String fieldName) {
        JComponent component = formFields.get(fieldName);
        if (component instanceof JTextField) {
            return ((JTextField) component).getText();
        } else if (component instanceof JComboBox) {
            return ((JComboBox<?>) component).getSelectedItem().toString();
        } else if (component instanceof JTextArea) {
            return ((JTextArea) component).getText();
        } else if (component instanceof JPasswordField) {
            return new String(((JPasswordField) component).getPassword());
        }
        return "";
    }
}