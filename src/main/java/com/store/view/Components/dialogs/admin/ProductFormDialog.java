package com.store.view.components.dialogs.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.view.components.shared.FormInputComponents;
import com.store.view.components.dialogs.constants.ProductFormDialogConstants;
import com.store.view.components.dialogs.FormStyler;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo para el formulario de creación/edición de productos.
 * Extiende de BaseEntityFormDialog para heredar funcionalidades comunes.
 * 
 * <p>Permite:
 * <ul>
 *   <li>Crear nuevos productos</li>
 *   <li>Editar productos existentes</li>
 *   <li>Validar datos antes de guardar</li>
 * </ul>
 */
public class ProductFormDialog extends BaseEntityFormDialog {
    private final ProductoServicioImpl productoServicio;
    private final Producto producto;
    private final ProductFormDialogConstants constants;
    
    private JTextField nombreField;
    private JTextArea descripcionArea;
    private JSpinner precioSpinner;
    private JSpinner stockSpinner;
    private JComboBox<String> categoriaCombo;
    private JComboBox<String> proveedorCombo;

    /**
     * Constructor del diálogo de producto.
     * @param parent Panel padre para posicionamiento relativo
     * @param producto Producto a editar (null para creación)
     * @param productoServicio Servicio para operaciones con productos
     */
    public ProductFormDialog(JPanel parent, Producto producto, ProductoServicioImpl productoServicio) {
        super(SwingUtilities.getWindowAncestor(parent), 
              producto == null ? "Nuevo Producto" : "Editar Producto");
        
        this.productoServicio = productoServicio;
        this.producto = producto == null ? new Producto() : producto;
        this.constants = new ProductFormDialogConstants();
        
        setSize(constants.WIDTH, constants.HEIGHT);
        setupLayout();
        centerOnParent();
    }

    /**
     * Configura el diseño específico del formulario de producto.
     * Implementación del método abstracto de la clase base.
     */
    @Override
    protected void setupLayout() {
        setupCommonLayout();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        if (producto.getCodigo() != null) {
            addReadOnlyField("Código:", producto.getCodigo());
        }
        
        nombreField = addTextField("Nombre:", producto.getNombre());
        descripcionArea = FormInputComponents.createTextArea(producto.getDescripcion(), 3, 20);
        addCustomField("Descripción:", new JScrollPane(descripcionArea));
        
        precioSpinner = FormInputComponents.createDecimalSpinner(
            producto.getPrecio(), 0.01, 0.0, 99999.99);
        addCustomField("Precio:", precioSpinner);
        
        stockSpinner = FormInputComponents.createIntegerSpinner(
            producto.getStock(), 1, 0, 9999);
        addCustomField("Stock:", stockSpinner);
        
        categoriaCombo = addComboBox("Categoría:", 
            productoServicio.obtenerCategorias(), 
            producto.getCategoria());
        
        proveedorCombo = addComboBox("Proveedor:", 
            productoServicio.obtenerProveedores(), 
            producto.getProveedor());
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(this::saveForm), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    /**
     * Maneja la lógica de guardado del producto.
     * Implementación del método abstracto de la clase base.
     */
    @Override
    protected void saveForm() {
        try {
            updateProductData();
            
            boolean success = producto.getCodigo() == null ? 
                productoServicio.crearProducto(producto) : 
                productoServicio.actualizarProducto(producto);
            
            handleSaveResult(success);
        } catch (Exception ex) {
            showError("Error al guardar el producto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Añade un campo de solo lectura al formulario.
     * @param label Etiqueta del campo
     * @param value Valor a mostrar
     */
    private void addReadOnlyField(String label, String value) {
        formPanel.add(FormStyler.createFormLabel(label));
        JTextField field = FormStyler.createFormTextField();
        field.setText(value);
        field.setEditable(false);
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    /**
     * Actualiza los datos del producto con los valores del formulario.
     */
    private void updateProductData() {
        producto.setNombre(nombreField.getText().trim());
        producto.setDescripcion(descripcionArea.getText().trim());
        producto.setPrecio(((Number)precioSpinner.getValue()).doubleValue());
        producto.setStock(((Number)stockSpinner.getValue()).intValue());
        producto.setCategoria((String)categoriaCombo.getSelectedItem());
        producto.setProveedor((String)proveedorCombo.getSelectedItem());
    }

    /**
     * Maneja el resultado de la operación de guardado.
     * @param success Indica si la operación fue exitosa
     */
    private void handleSaveResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, 
                producto.getCodigo() == null ? 
                    "Producto creado correctamente" : 
                    "Producto actualizado correctamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError("Error al guardar el producto. Verifique los datos.");
        }
    }
}