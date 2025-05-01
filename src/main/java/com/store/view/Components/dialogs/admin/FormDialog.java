package com.store.view.components.dialogs.admin;

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

public abstract class FormDialog extends JDialog {
    protected JPanel formPanel;
    protected GridBagConstraints gbc;
    protected Map<String, JComponent> formFields;
    protected JButton saveButton;
    protected final FormDialogConstants constants;

    public FormDialog(JPanel parent, String title, FormDialogConstants constants) {
        super((JFrame) SwingUtilities.getWindowAncestor(parent), title, true);
        this.constants = constants;
        initializeDialog();
        initializeFormPanel();
        initializeButtonPanel();
    }

    private void initializeDialog() {
        setSize(constants.WIDTH, constants.HEIGHT);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());
    }

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

    protected void addFormField(String label, JComponent component) {
        addFormField(label, component, false);
    }

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

    protected JTextField addFormField(String label, String value) {
        JTextField field = new JTextField(value);
        field.setFont(Fonts.BODY);
        addFormField(label, field);
        return field;
    }

    protected void addReadOnlyField(String label, String value) {
        JTextField field = new JTextField(value);
        field.setEditable(false);
        field.setFont(Fonts.BODY);
        addFormField(label, field);
    }

    protected <T> JComboBox<T> addComboField(String label, List<T> items, T selectedItem) {
        JComboBox<T> combo = new JComboBox<>();
        items.forEach(combo::addItem);
        if (selectedItem != null) combo.setSelectedItem(selectedItem);
        combo.setFont(Fonts.BODY);
        addFormField(label, combo);
        return combo;
    }

    protected void setSaveAction(ActionListener action) {
        saveButton.addActionListener(action);
    }

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