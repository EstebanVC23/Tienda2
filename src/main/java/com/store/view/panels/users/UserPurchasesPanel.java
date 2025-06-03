package com.store.view.panels.users;

import com.store.models.Sale;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.view.components.dialogs.admin.SaleDialog;
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
 * Panel que muestra el historial de compras de un usuario específico.
 * Permite visualizar detalles y modificar el estado de las compras.
 */
public class UserPurchasesPanel extends JPanel {
    private final SaleServiceImpl saleService;
    private final int userId;
    private CustomTable table;

    private static final String[] COLUMN_NAMES = {
        "ID", "Fecha", "Total", "Estado"
    };

    public UserPurchasesPanel(int userId, SaleServiceImpl saleService) {
        this.userId = userId;
        this.saleService = saleService;
        
        setLayout(new BorderLayout());
        setBackground(Colors.PANEL_BACKGROUND);
        
        initComponents();
        refreshTable();
    }

    private void initComponents() {
        // Título del panel
        JLabel titleLabel = new JLabel("Mis Compras");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Panel de la tabla
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Configurar doble click para editar
        setupTableDoubleClick();
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.table = new CustomTable(COLUMN_NAMES);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Sale> userPurchases = saleService.buscarVentasPorCliente(userId);
        for (Sale purchase : userPurchases) {
            // Añadir todos los datos incluyendo ID (columna 0)
            model.addRow(new Object[]{
                purchase.getId(),    // Columna 0 (oculta)
                purchase.getDate(), // Columna 1
                String.format("%.2f", purchase.getTotal()), // Columna 2
                purchase.getStatus() // Columna 3
            });
        }
    }

    private void setupTableDoubleClick() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedPurchase();
                }
            }
        });
    }

    private void editSelectedPurchase() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una compra haciendo clic en una fila", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0);
        Sale saleToEdit = saleService.obtenerVentaPorId(id);
        
        if (saleToEdit != null) {
            showEditDialog(saleToEdit);
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontró la compra seleccionada", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showEditDialog(Sale sale) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        SaleDialog dialog = new SaleDialog(parentWindow, 
            sale, 
            saleService,
            this::refreshTable); // Añadido el callback de refresco
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTable();
            }
        });
        dialog.setVisible(true);
    }
}