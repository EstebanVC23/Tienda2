package com.store.view.components.dialogs.user;

import com.store.models.ProductoCarrito;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.tables.CustomTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Diálogo para mostrar el contenido actual del carrito de compras.
 * Muestra los productos en una tabla con sus cantidades y subtotales.
 */
public class CarritoDialog extends JDialog {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    
    private final List<ProductoCarrito> carrito;
    private CustomTable table;
    
    public CarritoDialog(Window parent, List<ProductoCarrito> carrito) {
        super(parent, "Carrito de Compras", ModalityType.APPLICATION_MODAL);
        this.carrito = carrito;
        
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setupLayout();
        centerOnParent();
    }
    
    private void centerOnParent() {
        setLocationRelativeTo(getOwner());
    }
    
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        // Título
        JLabel titleLabel = new JLabel("Productos en el Carrito");
        titleLabel.setFont(Fonts.SECTION_TITLE);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Tabla
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con total y botones
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void setupTable() {
        String[] columnNames = {"Producto", "Precio Unitario", "Cantidad", "Subtotal"};
        table = new CustomTable(columnNames);
        
        DefaultTableModel model = table.getTableModel();
        
        for (ProductoCarrito item : carrito) {
            model.addRow(new Object[]{
                item.getProducto().getNombre(),
                String.format("$%.2f", item.getProducto().getPrecio()),
                item.getCantidadSeleccionada(),
                String.format("$%.2f", item.getSubtotal())
            });
        }
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 0, 0, 0));
        panel.setBackground(Colors.BACKGROUND);
        
        // Total
        double total = carrito.stream().mapToDouble(ProductoCarrito::getSubtotal).sum();
        JLabel totalLabel = new JLabel(String.format("Total: $%.2f", total));
        totalLabel.setFont(Fonts.SECTION_TITLE);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(totalLabel, BorderLayout.CENTER);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Colors.BACKGROUND);
        
        // Botón de Comprar
        CustomButton buyButton = new CustomButton("Comprar", Colors.PRIMARY);
        buyButton.setRound(true);
        buyButton.setCornerRadius(8);
        buttonPanel.add(buyButton);
        
        // Botón de Cerrar
        CustomButton closeButton = new CustomButton("Cerrar", Colors.SECONDARY_GRAY);
        closeButton.setRound(true);
        closeButton.setCornerRadius(8);
        closeButton.addActionListener(_ -> dispose());
        buttonPanel.add(closeButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
}