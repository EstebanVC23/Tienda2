package com.store.view.components.dialogs.admin;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.dialogs.FormStyler;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

/**
 * Diálogo base abstracto para formularios de entidades en la aplicación.
 * Proporciona funcionalidades comunes para formularios CRUD como:
 * - Campos de texto
 * - Combo boxes
 * - Manejo de errores
 * - Botones de acción
 * 
 * <p>Las clases concretas deben implementar:
 * <ul>
 *   <li>{@code setupLayout()} - Para definir la estructura del formulario</li>
 *   <li>{@code saveForm()} - Para manejar la lógica de guardado</li>
 * </ul>
 */
public abstract class BaseEntityFormDialog extends JDialog {
    /** Panel principal que contiene los campos del formulario */
    protected JPanel formPanel;
    /** Etiqueta para mostrar mensajes de error */
    protected JLabel errorLabel;
    
    /**
     * Crea un nuevo diálogo de formulario.
     * @param parent Ventana padre del diálogo
     * @param title Título del diálogo
     */
    public BaseEntityFormDialog(Window parent, String title) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        initCommonUI();
    }
    
    /**
     * Inicializa la configuración común de la UI.
     */
    private void initCommonUI() {
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }
    
    /**
     * Configura el diseño base común para todos los formularios.
     */
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
    
    /**
     * Añade un campo de texto al formulario.
     * @param label Etiqueta del campo
     * @param value Valor inicial del campo
     * @return JTextField creado
     */
    protected JTextField addTextField(String label, String value) {
        formPanel.add(FormStyler.createFormLabel(label));
        JTextField field = FormStyler.createFormTextField();
        field.setText(value);
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        return field;
    }
    
    /**
     * Añade un componente personalizado al formulario.
     * @param label Etiqueta del campo (puede ser vacía)
     * @param component Componente a añadir
     */
    protected void addCustomField(String label, JComponent component) {
        if (!label.isEmpty()) {
            formPanel.add(FormStyler.createFormLabel(label));
        }
        formPanel.add(component);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }
    
    /**
     * Añade un combo box al formulario.
     * @param label Etiqueta del campo
     * @param items Lista de opciones
     * @param selected Opción seleccionada inicialmente
     * @return JComboBox creado
     */
    protected JComboBox<String> addComboBox(String label, List<String> items, String selected) {
        formPanel.add(FormStyler.createFormLabel(label));
        JComboBox<String> combo = FormStyler.createFormComboBox();
        items.forEach(combo::addItem);
        if (selected != null) combo.setSelectedItem(selected);
        formPanel.add(combo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        return combo;
    }

    /**
     * Centra el diálogo respecto a su ventana padre.
     */
    protected void centerOnParent() {
        if (getOwner() != null) {
            Rectangle ownerBounds = getOwner().getBounds();
            int x = ownerBounds.x + (ownerBounds.width - getWidth()) / 2;
            int y = ownerBounds.y + (ownerBounds.height - getHeight()) / 2;
            setLocation(x, y);
        } else {
            setLocationRelativeTo(null);
        }
    }
    
    /**
     * Crea el panel de botones con acciones estándar.
     * @param saveAction Acción a ejecutar al guardar
     * @return JPanel con los botones configurados
     */
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
    
    /**
     * Muestra un mensaje de error en el formulario.
     * @param message Mensaje de error a mostrar
     */
    protected void showError(String message) {
        errorLabel.setText(message);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Método abstracto para configurar el diseño específico del formulario.
     * Debe ser implementado por las clases hijas.
     */
    protected abstract void setupLayout();
    
    /**
     * Método abstracto para manejar la lógica de guardado del formulario.
     * Debe ser implementado por las clases hijas.
     */
    protected abstract void saveForm();
}