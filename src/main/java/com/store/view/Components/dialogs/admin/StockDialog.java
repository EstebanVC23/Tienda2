package com.store.view.components.dialogs.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.shared.FormInputComponents;
import com.store.view.components.shared.RadioButtonGroup;
import com.store.view.components.dialogs.constants.StockDialogConstants;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo para gestionar el stock de productos.
 * Permite agregar o quitar unidades del inventario de un producto existente.
 * 
 * <p>Características principales:
 * <ul>
 *   <li>Muestra información detallada del producto</li>
 *   <li>Permite especificar cantidad a agregar/quitar</li>
 *   <li>Validación de datos antes de aplicar cambios</li>
 *   <li>Integración con el servicio de productos</li>
 * </ul>
 */
public class StockDialog extends BaseEntityFormDialog {
    /** Producto cuyo stock se está gestionando */
    private final Producto producto;
    /** Servicio para operaciones de productos */
    private final ProductoServicioImpl productoServicio;
    /** Constantes de configuración del diálogo */
    private final StockDialogConstants constants;
    
    /** Spinner para seleccionar la cantidad */
    private JSpinner cantidadSpinner;
    /** Grupo de radio buttons para seleccionar acción */
    private RadioButtonGroup stockActionGroup;

    /**
     * Constructor del diálogo de gestión de stock.
     * @param parent Ventana padre para posicionamiento relativo
     * @param producto Producto a gestionar
     * @param productoServicio Servicio para operaciones de stock
     */
    public StockDialog(JFrame parent, Producto producto, ProductoServicioImpl productoServicio) {
        super(parent, "Gestionar Stock");
        this.producto = producto;
        this.productoServicio = productoServicio;
        this.constants = new StockDialogConstants();
        
        setSize(constants.WIDTH, constants.HEIGHT);
        setupLayout();
        centerOnParent();
    }

    /**
     * Configura el diseño del diálogo.
     * Implementación del método abstracto de la clase base.
     */
    @Override
    protected void setupLayout() {
        setupCommonLayout();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        // Panel de información
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Campos del formulario
        cantidadSpinner = FormInputComponents.createIntegerSpinner(0, 1, 0, 9999);
        addCustomField("Cantidad:", cantidadSpinner);
        
        stockActionGroup = new RadioButtonGroup("Agregar stock", "Quitar stock");
        addCustomField("Acción:", stockActionGroup.getContainer());
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(this::saveForm), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    /**
     * Crea el panel superior con información del producto.
     * @return JPanel configurado con los datos del producto
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.BACKGROUND);
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel nameLabel = new JLabel(producto.getNombre());
        nameLabel.setFont(Fonts.SUBTITLE);
        nameLabel.setForeground(Colors.PRIMARY_TEXT);
        
        String stockColor = producto.getStock() < constants.LOW_STOCK_THRESHOLD ? "#e53935" : "#43a047";
        JLabel detailsLabel = new JLabel(String.format(
            "<html>Código: <b>%s</b> • Stock actual: <b style='color:%s'>%d</b></html>",
            producto.getCodigo(), stockColor, producto.getStock()
        ));
        detailsLabel.setFont(Fonts.BODY);
        detailsLabel.setForeground(Colors.SECONDARY_TEXT);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(detailsLabel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Maneja la lógica de guardado del stock.
     * Implementación del método abstracto de la clase base.
     */
    @Override
    protected void saveForm() {
        try {
            int cantidad = (Integer) cantidadSpinner.getValue();
            if (stockActionGroup.isSelected("Quitar stock")) {
                cantidad = -cantidad;
            }
            
            boolean success = productoServicio.actualizarStock(
                producto.getCodigo(), cantidad);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    constants.SUCCESS_MESSAGE, 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showError(constants.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            showError(constants.INVALID_NUMBER_MESSAGE);
        }
    }
}