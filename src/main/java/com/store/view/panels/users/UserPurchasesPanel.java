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

    /**
     * Constructor del panel de compras de usuario.
     * 
     * @param userId ID del usuario cuyas compras se mostrarán
     * @param saleService Servicio de ventas para operaciones CRUD
     */
    public UserPurchasesPanel(int userId, SaleServiceImpl saleService) {
        this.userId = userId;
        this.saleService = saleService;
        
        setLayout(new BorderLayout());
        setBackground(Colors.PANEL_BACKGROUND);
        
        initComponents();
        refreshTable();
    }

    /**
     * Inicializa los componentes del panel.
     */
    private void initComponents() {
        JLabel titleLabel = new JLabel("Mis Compras");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        setupTableDoubleClick();
    }

    /**
     * Crea el panel que contiene la tabla de compras.
     * 
     * @return Panel con la tabla configurada
     */
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

    /**
     * Actualiza los datos mostrados en la tabla.
     */
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Sale> userPurchases = saleService.buscarVentasPorCliente(userId);
        for (Sale purchase : userPurchases) {
            model.addRow(new Object[]{
                purchase.getId(),
                purchase.getDate(),
                String.format("%.2f", purchase.getTotal()),
                purchase.getStatus()
            });
        }
    }

    /**
     * Configura el listener para editar compras con doble click.
     */
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

    /**
     * Edita la compra seleccionada en la tabla.
     */
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

    /**
     * Muestra el diálogo de edición para una compra.
     * 
     * @param sale Compra a editar
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
}