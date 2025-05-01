package com.store.view.panels.admin;

import com.store.models.Producto;
import com.store.services.ProductoServicio;
import com.store.view.components.CustomTable;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.dialogs.admin.ProductFormDialog;
import com.store.view.components.dialogs.admin.StockDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosPanel extends CrudPanel<Producto> {
    private final ProductoServicio productoServicio;
    
    public ProductosPanel(ProductoServicio productoServicio) {
        super("Gestión de Productos");
        this.productoServicio = productoServicio;
        refreshTable();
    }

    @Override
    protected void setupFilterPanel() {
        filterPanel = new AdminFilterPanel("Buscar:", null, this::applyFilter);
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(createActionButton("Nuevo Producto", new Color(76, 175, 80), () -> showProductFormDialog(null)));
        buttonPanel.add(createActionButton("Editar", new Color(33, 150, 243), this::editSelectedProduct));
        buttonPanel.add(createActionButton("Gestionar Stock", new Color(255, 152, 0), this::manageStock));
        buttonPanel.add(createActionButton("Eliminar", new Color(244, 67, 54), this::deleteSelectedProduct));
        return buttonPanel;
    }

    @Override
    protected JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        String[] columnNames = {"Código", "Nombre", "Descripción", "Precio", "Stock", "Categoría", "Proveedor"};
        this.table = new CustomTable(columnNames);
        
        panel.add(table, BorderLayout.CENTER);
        return panel;
    }

    private void applyFilter(String filterText) {
        if (filterText.trim().isEmpty()) {
            table.getSorter().setRowFilter(null);
        } else {
            table.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
        }
    }

    private void editSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigo = table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString();
        Producto producto = productoServicio.obtenerProductoPorCodigo(codigo);
        if (producto != null) showProductFormDialog(producto);
    }

    private void deleteSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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

    private void manageStock() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigo = table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString();
        Producto producto = productoServicio.obtenerProductoPorCodigo(codigo);
        
        if (producto != null) {
            // Obtener el JFrame principal para usarlo como parent del diálogo
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
}