package com.store.view.components.dialogs.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.dialogs.FormStyler;
import com.store.view.components.dialogs.constants.StockDialogConstants;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class StockDialog extends AbstractFormDialog {
    private final Producto producto;
    private final ProductoServicioImpl productoServicio;
    private final StockDialogConstants constants;
    
    private JSpinner cantidadSpinner;
    private JRadioButton addButton;
    private JRadioButton removeButton;

    public StockDialog(JFrame parent, Producto producto, ProductoServicioImpl productoServicio) {
        super(parent, "Gestionar Stock");
        this.producto = producto;
        this.productoServicio = productoServicio;
        this.constants = new StockDialogConstants();
        
        setSize(constants.WIDTH, constants.HEIGHT);
        setupLayout();
        centerOnParent();;
    }

    @Override
    protected void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        // Panel de información
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Panel de formulario
        formPanel = FormStyler.createFormPanel();
        formPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.BORDER));
        
        // Campos del formulario
        cantidadSpinner = addSpinner("Cantidad:", 0, 1, 0, 9999);
        addRadioButtons();
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(this::updateStock), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.BACKGROUND);
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel nameLabel = new JLabel(producto.getNombre());
        nameLabel.setFont(Fonts.SUBTITLE);
        nameLabel.setForeground(Colors.PRIMARY_TEXT);
        
        JLabel detailsLabel = new JLabel(String.format(
            "<html>Código: <b>%s</b> • Stock actual: <b style='color:%s'>%d</b></html>",
            producto.getCodigo(),
            producto.getStock() < constants.LOW_STOCK_THRESHOLD ? "#e53935" : "#43a047",
            producto.getStock()
        ));
        detailsLabel.setFont(Fonts.BODY);
        detailsLabel.setForeground(Colors.SECONDARY_TEXT);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(detailsLabel, BorderLayout.SOUTH);
        return panel;
    }

    private JSpinner addSpinner(String label, int value, int step, int min, int max) {
        formPanel.add(FormStyler.createFormLabel(label));
        JSpinner spinner = FormStyler.createFormSpinner();
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner.setModel(model);
        formPanel.add(spinner);
        formPanel.add(Box.createRigidArea(new Dimension(0, constants.FIELD_SPACING)));
        return spinner;
    }

    private void addRadioButtons() {
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        radioPanel.setBackground(Colors.PANEL_BACKGROUND);
        
        addButton = new JRadioButton("Agregar stock");
        removeButton = new JRadioButton("Quitar stock");
        
        ButtonGroup group = new ButtonGroup();
        group.add(addButton);
        group.add(removeButton);
        addButton.setSelected(true);
        
        styleRadioButton(addButton);
        styleRadioButton(removeButton);
        
        radioPanel.add(addButton);
        radioPanel.add(removeButton);
        
        formPanel.add(radioPanel);
    }

    private void styleRadioButton(JRadioButton radio) {
        radio.setFont(Fonts.BODY);
        radio.setBackground(Colors.PANEL_BACKGROUND);
        radio.setFocusPainted(false);
        radio.setIcon(UIManager.getIcon("RadioButton.icon"));
        radio.setSelectedIcon(UIManager.getIcon("RadioButton.selectedIcon"));
    }

    private void updateStock() {
        try {
            int cantidad = (Integer) cantidadSpinner.getValue();
            if (removeButton.isSelected()) cantidad = -cantidad;
            
            boolean success = productoServicio.actualizarStock(producto.getCodigo(), cantidad);
            
            if (success) {
                JOptionPane.showMessageDialog(this, constants.SUCCESS_MESSAGE, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showError(constants.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            showError(constants.INVALID_NUMBER_MESSAGE);
        }
    }

    @Override
    protected void saveForm() {
        updateStock();
    }
}