package com.store.view.components.dialogs.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicio;
import com.store.utils.Fonts;
import com.store.view.components.dialogs.constants.ProductFormDialogConstants;

import javax.swing.*;

public class ProductFormDialog extends FormDialog {
    private final ProductoServicio productoServicio;
    private final Producto productToEdit;

    public ProductFormDialog(JPanel parent, Producto producto, ProductoServicio productoServicio) {
        super(parent, producto == null ? "Nuevo Producto" : "Editar Producto", new ProductFormDialogConstants());
        this.productoServicio = productoServicio;
        this.productToEdit = producto == null ? new Producto() : producto;
        initComponents();
    }

    private void initComponents() {
        if (productToEdit.getCodigo() != null) {
            addReadOnlyField("Código:", productToEdit.getCodigo());
        }
        
        JTextField nombreField = addFormField("Nombre:", productToEdit.getNombre());
        JTextArea descripcionArea = createTextArea(productToEdit.getDescripcion());
        addFormField("Descripción:", new JScrollPane(descripcionArea));
        
        // Spinner para precio (double)
        JSpinner precioSpinner = createSpinner(productToEdit.getPrecio(), 0.01, 0.0, 99999.99);
        addFormField("Precio:", precioSpinner);
        
        // Spinner para stock (int)
        JSpinner stockSpinner = createIntegerSpinner(productToEdit.getStock(), 1, 0, 9999);
        addFormField("Stock:", stockSpinner);
        
        JComboBox<String> categoriaCombo = addComboField("Categoría:", 
            productoServicio.obtenerCategorias(), 
            productToEdit.getCategoria());
        
        JComboBox<String> proveedorCombo = addComboField("Proveedor:", 
            productoServicio.obtenerProveedores(), 
            productToEdit.getProveedor());
        
        setupSaveAction(nombreField, descripcionArea, precioSpinner, stockSpinner, categoriaCombo, proveedorCombo);
    }

    private JTextArea createTextArea(String text) {
        JTextArea area = new JTextArea(text, 3, 20);
        area.setFont(Fonts.BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private JSpinner createSpinner(double value, double step, double min, double max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        
        // Configurar editor para decimales
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0.00");
        spinner.setEditor(editor);
        
        return spinner;
    }

    private JSpinner createIntegerSpinner(int value, int step, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        
        // Configurar editor para enteros
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
        
        return spinner;
    }

    private void setupSaveAction(JTextField nombreField, JTextArea descripcionArea,
                               JSpinner precioSpinner, JSpinner stockSpinner,
                               JComboBox<String> categoriaCombo, JComboBox<String> proveedorCombo) {
        setSaveAction(_ -> {
            try {
                updateProductData(nombreField, descripcionArea, precioSpinner, 
                                stockSpinner, categoriaCombo, proveedorCombo);
                
                boolean success = productToEdit.getCodigo() == null ? 
                    productoServicio.crearProducto(productToEdit) : 
                    productoServicio.actualizarProducto(productToEdit);
                
                handleSaveResult(success);
            } catch (Exception ex) {
                showError("Error al guardar el producto: " + ex.getMessage());
                ex.printStackTrace(); // Para depuración
            }
        });
    }

    private void updateProductData(JTextField nombreField, JTextArea descripcionArea,
                                 JSpinner precioSpinner, JSpinner stockSpinner,
                                 JComboBox<String> categoriaCombo, JComboBox<String> proveedorCombo) {
        productToEdit.setNombre(nombreField.getText());
        productToEdit.setDescripcion(descripcionArea.getText());
        
        // Conversión segura de valores numéricos
        productToEdit.setPrecio(((Number)precioSpinner.getValue()).doubleValue());
        productToEdit.setStock(((Number)stockSpinner.getValue()).intValue());
        
        productToEdit.setCategoria((String) categoriaCombo.getSelectedItem());
        productToEdit.setProveedor((String) proveedorCombo.getSelectedItem());
    }

    private void handleSaveResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, 
                productToEdit.getCodigo() == null ? 
                    "Producto creado correctamente" : 
                    "Producto actualizado correctamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError("Error al guardar el producto. Verifique los datos.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}