package com.store.view.panels.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.tables.CustomTable;
import com.store.view.components.dialogs.admin.ProductFormDialog;
import com.store.view.components.dialogs.admin.StockDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de administración para la gestión de productos.
 * Proporciona funcionalidades CRUD completas (Crear, Leer, Actualizar, Eliminar)
 * para productos, incluyendo gestión de stock e interfaz de usuario consistente.
 */
public class ProductosPanel extends CrudPanel<Producto> {
    private final ProductoServicioImpl productoServicio;
    
    private static final String[] COLUMN_NAMES = {
        "Código", "Nombre", "Descripción", "Precio", "Stock", "Categoría", "Proveedor"
    };

    /**
     * Constructor del panel de gestión de productos.
     * @param productoServicio Servicio para operaciones con productos
     */
    public ProductosPanel(ProductoServicioImpl productoServicio) {
        super("Gestión de Productos");
        this.productoServicio = productoServicio;
        refreshTable();
    }

    /**
     * Configura el panel de filtros para la búsqueda de productos.
     * Se utiliza un panel de filtro personalizado que permite buscar productos
     * por nombre, código o categoría.
     */
    @Override
    protected void setupFilterPanel() {
        filterPanel = new AdminFilterPanel("Buscar productos:", null, this::applyFilter);
    }

    /**
     * Crea el panel de botones de acción para el CRUD de productos.
     * Incluye botones para crear, editar, eliminar y gestionar stock de productos.
     * @return Panel con los botones de acción
     */
    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Colors.PANEL_BACKGROUND);
        
        buttonPanel.add(createActionButton("Nuevo Producto", Colors.SUCCESS_GREEN, 
            () -> showProductFormDialog(null)));
        buttonPanel.add(createActionButton("Editar", Colors.PRIMARY_BLUE, 
            this::editSelectedProduct));
        buttonPanel.add(createActionButton("Gestionar Stock", Colors.WARNING_ORANGE, 
            this::manageStock));
        buttonPanel.add(createActionButton("Eliminar", Colors.ERROR_RED, 
            this::deleteSelectedProduct));
        
        return buttonPanel;
    }

    /**
     * Crea el panel de la tabla de productos.
     * Configura la tabla con los nombres de las columnas y el modelo de datos.
     * @return Panel con la tabla de productos
     */
    @Override
    protected JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.PANEL_BACKGROUND);
        
        this.table = new CustomTable(COLUMN_NAMES);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Aplica filtro de búsqueda a la tabla de productos.
     * @param filterText Texto para filtrar los productos
     */
    private void applyFilter(String filterText) {
        if (filterText.trim().isEmpty()) {
            table.getSorter().setRowFilter(null);
        } else {
            table.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
        }
    }

    /**
     * Edita el producto seleccionado en la tabla.
     */
    private void editSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorMessage("Seleccione un producto");
            return;
        }
        
        String codigo = table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString();
        Producto producto = productoServicio.obtenerProductoPorCodigo(codigo);
        if (producto != null) showProductFormDialog(producto);
    }

    /**
     * Elimina el producto seleccionado previa confirmación.
     */
    private void deleteSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorMessage("Seleccione un producto");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String codigo = table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString();
            boolean success = productoServicio.eliminarProducto(codigo);
            if (success) {
                refreshTable();
            } else {
                showErrorMessage("Error al eliminar el producto");
            }
        }
    }

    /**
     * Muestra el diálogo de formulario para crear/editar productos.
     * @param producto Producto a editar (null para crear nuevo)
     */
    private void showProductFormDialog(Producto producto) {
        ProductFormDialog dialog = new ProductFormDialog(this, producto, productoServicio);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTable();
                notifyRefresh();
            }
        });
        dialog.setVisible(true);
    }

    /**
     * Muestra el diálogo para gestionar el stock del producto seleccionado.
     */
    private void manageStock() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorMessage("Seleccione un producto");
            return;
        }
        
        String codigo = table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString();
        Producto producto = productoServicio.obtenerProductoPorCodigo(codigo);
        
        if (producto != null) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            StockDialog dialog = new StockDialog(parentFrame, producto, productoServicio);
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                    notifyRefresh();
                }
            });
            dialog.setVisible(true);
        }
    }

    /**
     * Refresca la tabla de productos con los datos actuales del servicio.
     */
    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<Producto> productos = productoServicio.listarProductos();
        for (Producto p : productos) {
            model.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getDescripcion(),
                String.format("%.2f", p.getPrecio()),
                p.getStock(),
                p.getCategoria(),
                p.getProveedor()
            });
        }
    }

    /**
     * Muestra un mensaje de error al usuario.
     * @param message Mensaje de error a mostrar
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}