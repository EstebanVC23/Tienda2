package com.store.view.components.dialogs.user;

import com.store.models.Producto;
import com.store.models.ProductoCarrito;
import com.store.models.Sale;
import com.store.models.SaleItem;
import com.store.models.SaleStatus;
import com.store.models.Usuario;
import com.store.services.ProductoServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.tables.CustomTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 * Diálogo para mostrar el contenido actual del carrito de compras.
 * Permite gestionar los productos, eliminar items y realizar la compra.
 */
public class CarritoDialog extends JDialog {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    
    private final List<ProductoCarrito> carrito;
    private CustomTable table;
    private DefaultTableModel tableModel;
    
    private final SaleServiceImpl saleService;
    private final ProductoServicioImpl productService;
    private final Usuario usuario;

    /**
     * Crea un nuevo diálogo de carrito de compras.
     *
     * @param parent la ventana padre
     * @param carrito la lista de productos en el carrito
     * @param saleService el servicio de ventas
     * @param productService el servicio de productos
     * @param usuario el usuario que realiza la compra
     */
    public CarritoDialog(Window parent, List<ProductoCarrito> carrito, 
                        SaleServiceImpl saleService, 
                        ProductoServicioImpl productService,
                        Usuario usuario) {
        super(parent, "Carrito de Compras", ModalityType.APPLICATION_MODAL);
        this.carrito = carrito;
        this.saleService = saleService;
        this.productService = productService;
        this.usuario = usuario;
        
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
        
        JLabel titleLabel = new JLabel("Productos en el Carrito");
        titleLabel.setFont(Fonts.SECTION_TITLE);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void setupTable() {
        String[] columnNames = {"Producto", "Precio Unitario", "Cantidad", "Subtotal"};
        table = new CustomTable(columnNames);
        tableModel = table.getTableModel();
        
        updateTableModel();
    }
    
    private void updateTableModel() {
        tableModel.setRowCount(0);
        
        for (ProductoCarrito item : carrito) {
            tableModel.addRow(new Object[]{
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
        
        updateTotalLabel(panel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Colors.BACKGROUND);
        
        CustomButton deleteButton = new CustomButton("Eliminar Producto", Colors.ERROR_RED);
        deleteButton.setRound(true);
        deleteButton.setCornerRadius(8);
        deleteButton.addActionListener(_ -> deleteSelectedProduct());
        buttonPanel.add(deleteButton);
        
        CustomButton buyButton = new CustomButton("Comprar", Colors.PRIMARY);
        buyButton.setRound(true);
        buyButton.setCornerRadius(8);
        buyButton.addActionListener(_ -> processPurchase());
        buttonPanel.add(buyButton);
        
        CustomButton closeButton = new CustomButton("Cerrar", Colors.SECONDARY_GRAY);
        closeButton.setRound(true);
        closeButton.setCornerRadius(8);
        closeButton.addActionListener(_ -> dispose());
        buttonPanel.add(closeButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void updateTotalLabel(JPanel panel) {
        double total = carrito.stream().mapToDouble(ProductoCarrito::getSubtotal).sum();
        JLabel totalLabel = new JLabel(String.format("Total: $%.2f", total));
        totalLabel.setFont(Fonts.SECTION_TITLE);
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        Component[] components = panel.getComponents();
        for (Component c : components) {
            if (c instanceof JLabel) {
                panel.remove(c);
            }
        }
        
        panel.add(totalLabel, BorderLayout.CENTER);
    }
    
    /**
     * Elimina el producto seleccionado del carrito.
     * Muestra mensajes de confirmación y actualiza la interfaz.
     */
    private void deleteSelectedProduct() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un producto para eliminar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea eliminar este producto del carrito?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            carrito.remove(selectedRow);
            updateTableModel();
            updateTotalLabel((JPanel) getContentPane().getComponent(2));
            
            JOptionPane.showMessageDialog(this, 
                "Producto eliminado del carrito", 
                "Eliminado", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Procesa la compra de los productos en el carrito.
     * Realiza validaciones de stock, registra la venta y actualiza inventarios.
     */
    private void processPurchase() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea proceder con la compra?",
            "Confirmar compra",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Sale nuevaVenta = new Sale();
            nuevaVenta.setCustomerId(usuario.getId());
            nuevaVenta.setDate(new Date());
            nuevaVenta.setStatus(SaleStatus.COMPLETED);

            List<SaleItem> itemsVenta = carrito.stream()
                .map(pc -> new SaleItem(pc.getProducto(), pc.getCantidadSeleccionada()))
                .toList();
            
            nuevaVenta.setItems(itemsVenta);
            nuevaVenta.recalculateTotal();

            for (ProductoCarrito item : carrito) {
                Producto producto = item.getProducto();
                if (producto.getStock() < item.getCantidadSeleccionada()) {
                    JOptionPane.showMessageDialog(this,
                        "No hay suficiente stock para: " + producto.getNombre() + 
                        "\nStock disponible: " + producto.getStock() +
                        "\nCantidad solicitada: " + item.getCantidadSeleccionada(),
                        "Error de stock",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            boolean ventaExitosa = saleService.crearVenta(nuevaVenta);
            
            if (!ventaExitosa) {
                throw new Exception("No se pudo registrar la venta en el sistema");
            }

            for (ProductoCarrito item : carrito) {
                Producto producto = item.getProducto();
                int cantidadVendida = item.getCantidadSeleccionada();
                producto.setStock(producto.getStock() - cantidadVendida);
                productService.actualizarProducto(producto);
            }

            String mensaje = String.format(
                "¡Compra realizada con éxito!\n\n" +
                "Número de venta: %d\n" +
                "Fecha: %s\n" +
                "Total: $%.2f\n" +
                "Productos: %d",
                nuevaVenta.getId(),
                nuevaVenta.getDate(),
                nuevaVenta.getTotal(),
                nuevaVenta.getItems().size());
            
            JOptionPane.showMessageDialog(this,
                mensaje,
                "Compra exitosa",
                JOptionPane.INFORMATION_MESSAGE);

            carrito.clear();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al procesar la compra: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}