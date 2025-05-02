package com.store.view.components.dialogs.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.dialogs.FormStyler;
import com.store.view.components.dialogs.constants.ProductFormDialogConstants;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ProductFormDialog extends AbstractFormDialog {
    private final ProductoServicioImpl productoServicio;
    private final Producto productToEdit;
    private final ProductFormDialogConstants constants;
    
    public ProductFormDialog(JPanel parent, Producto producto, ProductoServicioImpl productoServicio) {
        super(SwingUtilities.getWindowAncestor(parent), 
              producto == null ? "Nuevo Producto" : "Editar Producto");
        
        this.productoServicio = productoServicio;
        this.productToEdit = producto == null ? new Producto() : producto;
        this.constants = new ProductFormDialogConstants();
        
        // Configurar tamaño antes de hacer visible
        setSize(constants.WIDTH, constants.HEIGHT);
        setupLayout();
        
        // Centrar después de configurar el tamaño y antes de hacer visible
        setLocationRelativeTo(getOwner());
    }

    @Override
    protected void setupLayout() {
        // Creamos un nuevo panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        // Configuramos el panel del formulario
        formPanel = FormStyler.createFormPanel();
        formPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER));
        
        if (productToEdit.getCodigo() != null) {
            addReadOnlyField("Código:", productToEdit.getCodigo());
        }
        
        JTextField nombreField = addTextField("Nombre:", productToEdit.getNombre());
        JTextArea descripcionArea = createTextArea(productToEdit.getDescripcion());
        addCustomField("Descripción:", new JScrollPane(descripcionArea));
        
        JSpinner precioSpinner = createSpinner(productToEdit.getPrecio(), 0.01, 0.0, 99999.99);
        addCustomField("Precio:", precioSpinner);
        
        JSpinner stockSpinner = createIntegerSpinner(productToEdit.getStock(), 1, 0, 9999);
        addCustomField("Stock:", stockSpinner);
        
        JComboBox<String> categoriaCombo = addComboBox("Categoría:", 
            productoServicio.obtenerCategorias(), 
            productToEdit.getCategoria());
        
        JComboBox<String> proveedorCombo = addComboBox("Proveedor:", 
            productoServicio.obtenerProveedores(), 
            productToEdit.getProveedor());
        
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Colors.ERROR_RED);
        errorLabel.setFont(Fonts.SMALL);
        formPanel.add(errorLabel);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(() -> saveProduct(
            nombreField, descripcionArea, precioSpinner, 
            stockSpinner, categoriaCombo, proveedorCombo
        )), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    private void addReadOnlyField(String label, String value) {
        formPanel.add(FormStyler.createFormLabel(label));
        JTextField field = FormStyler.createFormTextField();
        field.setText(value);
        field.setEditable(false);
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, constants.FIELD_SPACING)));
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
        
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0.00");
        spinner.setEditor(editor);
        
        return spinner;
    }

    private JSpinner createIntegerSpinner(int value, int step, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
        
        return spinner;
    }

    private void saveProduct(JTextField nombreField, JTextArea descripcionArea,
                           JSpinner precioSpinner, JSpinner stockSpinner,
                           JComboBox<String> categoriaCombo, JComboBox<String> proveedorCombo) {
        try {
            updateProductData(nombreField, descripcionArea, precioSpinner, 
                             stockSpinner, categoriaCombo, proveedorCombo);
            
            boolean success = productToEdit.getCodigo() == null ? 
                productoServicio.crearProducto(productToEdit) : 
                productoServicio.actualizarProducto(productToEdit);
            
            handleSaveResult(success);
        } catch (Exception ex) {
            showError("Error al guardar el producto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void updateProductData(JTextField nombreField, JTextArea descripcionArea,
                                 JSpinner precioSpinner, JSpinner stockSpinner,
                                 JComboBox<String> categoriaCombo, JComboBox<String> proveedorCombo) {
        productToEdit.setNombre(nombreField.getText().trim());
        productToEdit.setDescripcion(descripcionArea.getText().trim());
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

    @Override
    protected void saveForm() {
        // Implementación vacía ya que usamos saveProduct directamente
    }
}