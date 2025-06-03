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

public class SaleDialog extends BaseEntityFormDialog {
    private final SaleServiceImpl saleService;
    private final Sale sale;
    
    private JLabel dateLabel;
    private JLabel customerIdLabel;
    private JLabel totalLabel;
    private JComboBox<SaleStatus> statusCombo;
    private CustomTable productsTable;
    private final Runnable refreshCallback;

    public SaleDialog(Window parent, Sale sale, SaleServiceImpl saleService, Runnable refreshCallback) {
        super(parent, "Editar Venta");
        
        // Validación mejorada con mensaje más descriptivo
        if (sale == null) {
            throw new IllegalArgumentException("No se ha seleccionado ninguna venta para editar. Por favor seleccione una venta válida.");
        }
        if (saleService == null) {
            throw new IllegalArgumentException("El servicio de ventas no está disponible.");
        }

        this.refreshCallback = refreshCallback;
        this.saleService = saleService;
        this.sale = sale;
        
        // Configurar título con ID si existe
        if (sale.getId() != 0) {
            setTitle("Editar Venta #" + sale.getId());
        }
        
        setSize(750, 620);
        setupLayout();
        centerOnParent();
        
        // Configuración para evitar cierre accidental
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

        // Tabla de productos
        productsTable = createProductsTable();
        JScrollPane tableScroll = new JScrollPane(productsTable);
        tableScroll.setPreferredSize(new Dimension(650, 200));
        addCustomField("Productos:", tableScroll);

        // Total
        totalLabel = new JLabel(formatCurrency(sale.getTotal()));
        totalLabel.setFont(Fonts.BODY);
        addCustomField("Total:", totalLabel);

        // Combo de estado (único campo editable)
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

        // Validar items de la venta
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

    @Override
    protected void saveForm() {
        try {
            SaleStatus newStatus = (SaleStatus) statusCombo.getSelectedItem();
            SaleStatus oldStatus = sale.getStatus();
            
            if (newStatus != oldStatus) {
                // Necesitamos el servicio de productos para actualizar el stock
                ProductoServicioImpl productoServicio = new ProductoServicioImpl();
                boolean stockUpdated = true;
                
                // Si el nuevo estado es CANCELLED y el anterior no lo era
                if (newStatus == SaleStatus.CANCELLED && oldStatus != SaleStatus.CANCELLED) {
                    // Devolver el stock al inventario
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
                // Si el estado anterior era CANCELLED y el nuevo no lo es
                else if (oldStatus == SaleStatus.CANCELLED && newStatus != SaleStatus.CANCELLED) {
                    // Quitar el stock del inventario nuevamente
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
                
                // Actualizar el estado de la venta
                sale.setStatus(newStatus);
                boolean success = saleService.actualizarVenta(sale);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Estado actualizado correctamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Notificar para actualizar la vista
                    if (refreshCallback != null) {
                        refreshCallback.run();
                    }
                
                dispose();
                } else {
                    // Si falla la actualización, revertir los cambios en el stock
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
                dispose(); // No hay cambios
            }
        } catch (Exception ex) {
            showError("Error al guardar cambios: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}