package com.store.view.panels.admin;

import com.store.models.Sale;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.view.components.dialogs.admin.SaleDialog;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.tables.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Panel de administración para la gestión de ventas.
 * Permite visualizar y editar ventas mediante doble click.
 */
public class SalesPanel extends CrudPanel<Sale> {
    private final SaleServiceImpl saleService;

    private static final String[] COLUMN_NAMES = {
        "ID", "Fecha", "Cliente", "Total", "Estado"
    };

    /**
     * Constructor del panel de ventas.
     * 
     * @param saleService Servicio de ventas para operaciones CRUD
     */
    public SalesPanel(SaleServiceImpl saleService) {
        super("Gestión de Ventas");
        this.saleService = saleService;
        setupTableDoubleClick();
        refreshTable();
    }

    @Override
    protected void setupFilterPanel() {
        filterPanel = new AdminFilterPanel("Buscar ventas:", null, this::applyFilter);
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.PANEL_BACKGROUND);
        return buttonPanel;
    }

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
     * Configura el listener para editar ventas con doble click.
     */
    private void setupTableDoubleClick() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedSale();
                }
            }
        });
    }

    /**
     * Edita la venta seleccionada en la tabla.
     */
    private void editSelectedSale() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una venta haciendo clic en una fila", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0);
        Sale saleToEdit = saleService.obtenerVentaPorId(id);
        
        if (saleToEdit != null) {
            showEditDialog(saleToEdit);
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontró la venta seleccionada", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el diálogo de edición para una venta.
     * 
     * @param sale Venta a editar
     */
    private void showEditDialog(Sale sale) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        SaleDialog dialog = new SaleDialog(parentWindow, 
            sale,
            saleService,
            this::refreshTable);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTable();
            }
        });
        dialog.setVisible(true);
    }

    /**
     * Aplica un filtro de búsqueda a la tabla.
     * 
     * @param filterText Texto del filtro
     */
    private void applyFilter(String filterText) {
        if (filterText.trim().isEmpty()) {
            table.getSorter().setRowFilter(null);
        } else {
            table.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + filterText));
        }
    }

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
}