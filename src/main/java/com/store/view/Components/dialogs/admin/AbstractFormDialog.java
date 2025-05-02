package com.store.view.components.dialogs.admin;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.dialogs.FormStyler;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

public abstract class AbstractFormDialog extends JDialog {
    protected JPanel formPanel;
    protected JLabel errorLabel;
    
    public AbstractFormDialog(Window parent, String title) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        initCommonUI();
    }
    
    private void initCommonUI() {
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }
    
    protected void setupCommonLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        formPanel = FormStyler.createFormPanel();
        formPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER));
        
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Colors.ERROR_RED);
        errorLabel.setFont(Fonts.SMALL);
    }
    
    protected JTextField addTextField(String label, String value) {
        formPanel.add(FormStyler.createFormLabel(label));
        JTextField field = FormStyler.createFormTextField();
        field.setText(value);
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        return field;
    }
    
    protected void addCustomField(String label, JComponent component) {
        if (!label.isEmpty()) {
            formPanel.add(FormStyler.createFormLabel(label));
        }
        formPanel.add(component);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }
    
    protected JComboBox<String> addComboBox(String label, List<String> items, String selected) {
        formPanel.add(FormStyler.createFormLabel(label));
        JComboBox<String> combo = FormStyler.createFormComboBox();
        items.forEach(combo::addItem);
        if (selected != null) combo.setSelectedItem(selected);
        formPanel.add(combo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        return combo;
    }

    protected void centerOnParent() {
        // Calcula la posición para centrar el diálogo respecto a su padre
        if (getOwner() != null) {
            Rectangle ownerBounds = getOwner().getBounds();
            int x = ownerBounds.x + (ownerBounds.width - getWidth()) / 2;
            int y = ownerBounds.y + (ownerBounds.height - getHeight()) / 2;
            setLocation(x, y);
        } else {
            // Si no hay padre, centra en la pantalla
            setLocationRelativeTo(null);
        }
    }
    
    protected JPanel createButtonPanel(Runnable saveAction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Colors.BACKGROUND);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        CustomButton cancelButton = new CustomButton("Cancelar", Colors.SECONDARY_GRAY);
        cancelButton.addActionListener(_ -> dispose());
        
        CustomButton saveButton = new CustomButton("Guardar", Colors.PRIMARY_BLUE);
        saveButton.addActionListener(_ -> saveAction.run());
        
        panel.add(cancelButton);
        panel.add(saveButton);
        return panel;
    }
    
    protected void showError(String message) {
        errorLabel.setText(message);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    protected abstract void setupLayout();
    protected abstract void saveForm();
}