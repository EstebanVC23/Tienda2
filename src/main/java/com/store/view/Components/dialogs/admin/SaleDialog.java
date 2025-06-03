package com.store.view.components.dialogs.admin;

import com.store.models.Sale;
import com.store.models.SaleItem;
import com.store.models.SaleStatus;
import com.store.services.ProductoServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.tables.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Date;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo para visualizar y editar los detalles de una venta.
 * Permite cambiar el estado de la venta y gestionar el stock de productos asociados.
 */
public class SaleDialog extends BaseEntityFormDialog {
    private final SaleServiceImpl saleService;
    private final Sale sale;
    
    private JLabel dateLabel;
    private JLabel customerIdLabel;
    private JLabel totalLabel;
    private JComboBox<SaleStatus> statusCombo;
    private CustomTable productsTable;
    private final Runnable refreshCallback;

    /**
     * Crea un nuevo diálogo de venta.
     *
     * @param parent la ventana padre
     * @param sale la venta a mostrar/editar
     * @param saleService el servicio de ventas
     * @param refreshCallback callback para actualizar la vista principal
     * @throws IllegalArgumentException si la venta o el servicio son nulos
     */
    public SaleDialog(Window parent, Sale sale, SaleServiceImpl saleService, Runnable refreshCallback) {
        super(parent, "Editar Venta");
        
        if (sale == null) {
            throw new IllegalArgumentException("No se ha seleccionado ninguna venta para editar. Por favor seleccione una venta válida.");
        }
        if (saleService == null) {
            throw new IllegalArgumentException("El servicio de ventas no está disponible.");
        }

        this.refreshCallback = refreshCallback;
        this.saleService = saleService;
        this.sale = sale;
        
        if (sale.getId() != 0) {
            setTitle("Editar Venta #" + sale.getId());
        }
        
        setSize(750, 620);
        setupLayout();
        centerOnParent();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    @Override
    protected void setupLayout() {
        setupCommonLayout();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        customerIdLabel = new JLabel(String.valueOf(sale.getCustomerId()));
        customerIdLabel.setFont(Fonts.BODY);
        addCustomField("ID Cliente:", customerIdLabel);

        dateLabel = new JLabel(formatDate(sale.getDate()));
        dateLabel.setFont(Fonts.BODY);
        addCustomField("Fecha:", dateLabel);

        productsTable = createProductsTable();
        JScrollPane tableScroll = new JScrollPane(productsTable);
        tableScroll.setPreferredSize(new Dimension(650, 200));
        addCustomField("Productos:", tableScroll);

        totalLabel = new JLabel(formatCurrency(sale.getTotal()));
        totalLabel.setFont(Fonts.BODY);
        addCustomField("Total:", totalLabel);

        statusCombo = new JComboBox<>(SaleStatus.values());
        statusCombo.setSelectedItem(sale.getStatus() != null ? sale.getStatus() : SaleStatus.COMPLETED);
        addCustomField("Estado:", statusCombo);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private String formatDate(Date date) {
        return date != null ? date.toString() : "No especificada";
    }

    private String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance().format(amount);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Colors.BACKGROUND);
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(_ -> dispose());
        
        JButton saveButton = new JButton("Guardar Cambios");
        saveButton.addActionListener(_ -> saveForm());
        
        panel.add(cancelButton);
        panel.add(saveButton);
        
        return panel;
    }

    private CustomTable createProductsTable() {
        String[] columnNames = {"Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        CustomTable table = new CustomTable(columnNames);
        DefaultTableModel model = table.getTableModel();

        if (sale.getItems() != null) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            for (SaleItem item : sale.getItems()) {
                if (item != null && item.getProduct() != null) {
                    model.addRow(new Object[]{
                        item.getProduct().getNombre(),
                        item.getQuantity(),
                        currencyFormat.format(item.getProduct().getPrecio()),
                        currencyFormat.format(item.getProduct().getPrecio() * item.getQuantity())
                    });
                }
            }
        }
        
        return table;
    }

    /**
     * Guarda los cambios realizados en el diálogo.
     * Maneja la actualización del estado de la venta y el stock de productos.
     */
    @Override
    protected void saveForm() {
        try {
            SaleStatus newStatus = (SaleStatus) statusCombo.getSelectedItem();
            SaleStatus oldStatus = sale.getStatus();
            
            if (newStatus != oldStatus) {
                ProductoServicioImpl productoServicio = new ProductoServicioImpl();
                boolean stockUpdated = true;
                
                if (newStatus == SaleStatus.CANCELLED && oldStatus != SaleStatus.CANCELLED) {
                    for (SaleItem item : sale.getItems()) {
                        if (item != null && item.getProduct() != null) {
                            int quantityToReturn = item.getQuantity();
                            if (!productoServicio.actualizarStock(item.getProduct().getCodigo(), quantityToReturn)) {
                                stockUpdated = false;
                                break;
                            }
                        }
                    }
                }
                else if (oldStatus == SaleStatus.CANCELLED && newStatus != SaleStatus.CANCELLED) {
                    for (SaleItem item : sale.getItems()) {
                        if (item != null && item.getProduct() != null) {
                            int quantityToRemove = item.getQuantity();
                            if (!productoServicio.actualizarStock(item.getProduct().getCodigo(), -quantityToRemove)) {
                                stockUpdated = false;
                                break;
                            }
                        }
                    }
                }
                
                if (!stockUpdated) {
                    showError("No se pudo actualizar el stock de uno o más productos");
                    return;
                }
                
                sale.setStatus(newStatus);
                boolean success = saleService.actualizarVenta(sale);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Estado actualizado correctamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    if (refreshCallback != null) {
                        refreshCallback.run();
                    }
                
                dispose();
                } else {
                    if (newStatus == SaleStatus.CANCELLED && oldStatus != SaleStatus.CANCELLED) {
                        for (SaleItem item : sale.getItems()) {
                            if (item != null && item.getProduct() != null) {
                                productoServicio.actualizarStock(item.getProduct().getCodigo(), -item.getQuantity());
                            }
                        }
                    } else if (oldStatus == SaleStatus.CANCELLED && newStatus != SaleStatus.CANCELLED) {
                        for (SaleItem item : sale.getItems()) {
                            if (item != null && item.getProduct() != null) {
                                productoServicio.actualizarStock(item.getProduct().getCodigo(), item.getQuantity());
                            }
                        }
                    }
                    showError("Error al actualizar el estado de la venta");
                }
            } else {
                dispose();
            }
        } catch (Exception ex) {
            showError("Error al guardar cambios: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}