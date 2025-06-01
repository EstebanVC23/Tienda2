package com.store.view.panels.admin;

import com.store.models.Sale;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.tables.CustomTable;
import com.store.view.components.dialogs.admin.SaleFormDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Panel de administración para la gestión de ventas.
 * Proporciona funcionalidades CRUD para ventas, incluyendo
 * filtrado, edición y creación a través de formularios dedicados.
 */
public class SalesPanel extends CrudPanel<Sale> {
    private final SaleServiceImpl saleService;

    private static final String[] COLUMN_NAMES = {
        "ID", "Fecha", "Cliente", "Total", "Estado"
    };

    /**
     * Constructor para el panel de gestión de ventas.
     * @param saleService Servicio para operaciones con ventas.
     */
    public SalesPanel(SaleServiceImpl saleService) {
        super("Gestión de Ventas");
        this.saleService = saleService;
        refreshTable();
    }

    /**
     * Configura el panel de filtro para buscar ventas.
     */
    @Override
    protected void setupFilterPanel() {
        filterPanel = new AdminFilterPanel("Buscar ventas:", null, this::applyFilter);
    }

    /**
     * Crea el panel con los botones de acción (nuevo, editar, eliminar).
     * @return Panel con botones.
     */
    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Colors.PANEL_BACKGROUND);

        buttonPanel.add(createActionButton("Nueva Venta", Colors.SUCCESS_GREEN, () -> showSaleFormDialog(null)));
        buttonPanel.add(createActionButton("Editar Venta", Colors.PRIMARY_BLUE, this::editSelectedSale));
        buttonPanel.add(createActionButton("Eliminar Venta", Colors.ERROR_RED, this::deleteSelectedSale));

        return buttonPanel;
    }

    /**
     * Crea el panel con la tabla para mostrar las ventas.
     * @return Panel con la tabla.
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
     * Aplica filtro de búsqueda a la tabla de ventas.
     * @param filterText Texto para filtrar.
     */
    private void applyFilter(String filterText) {
        if (filterText.trim().isEmpty()) {
            table.getSorter().setRowFilter(null);
        } else {
            table.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
        }
    }

    /**
     * Edita la venta seleccionada en la tabla.
     */
    private void editSelectedSale() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorMessage("Seleccione una venta");
            return;
        }

        int id = (int) table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0);
        boolean success = saleService.eliminarVenta(id);
        if (success) {
            showSaleFormDialog(saleService.obtenerVentaPorId(id));
        } else {
            showErrorMessage("No se encontró la venta seleccionada");
        }
    }

    /**
     * Elimina la venta seleccionada previa confirmación.
     */
    private void deleteSelectedSale() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow < 0) {
        showErrorMessage("Seleccione una venta");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "¿Eliminar esta venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // Obtener id entero en lugar de String
        int id = (int) table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0);
        boolean success = saleService.eliminarVenta(id);
        if (success) {
            refreshTable();
        } else {
            showErrorMessage("Error al eliminar la venta");
        }
    }
}


    /**
     * Muestra el diálogo para crear o editar una venta.
     * @param sale Venta a editar, null para crear nueva.
     */
    private void showSaleFormDialog(Sale sale) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        SaleFormDialog dialog = new SaleFormDialog(parentWindow, sale, saleService);
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
     * Refresca la tabla con las ventas actuales.
     */
    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Sale> sales = saleService.listarVentas();
        for (Sale sale : sales) {
            model.addRow(new Object[]{
                sale.getId(),
                sale.getDate(),
                sale.getCustomerId(),
                String.format("%.2f", sale.getTotal()),
                sale.getStatus()
            });
        }
    }

    /**
     * Muestra un mensaje de error.
     * @param message Mensaje a mostrar.
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
