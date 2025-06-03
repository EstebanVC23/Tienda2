package com.store.view.panels.users;

import com.store.models.Producto;
import com.store.models.ProductoCarrito;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.view.components.TitlePanel;
import com.store.view.components.cards.spaces.ProductGridPanel;
import com.store.view.components.cards.spaces.ProductSearchHeader;
import com.store.view.panels.BasePanel;
import com.store.view.components.cards.constants.GridConstants;
import com.store.view.components.cards.QuantityInputDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class ProductosClientePanel extends BasePanel {
    private final ProductoServicioImpl productoServicio;
    private final ProductSearchHeader filterPanel;
    private final ProductGridPanel gridPanel;
    private final JScrollPane scrollPane;
    private final TitlePanel titlePanel;
    private final List<ProductoCarrito> carritoCompras;

    public ProductosClientePanel(ProductoServicioImpl productoServicio, 
                               List<ProductoCarrito> carritoCompras) {
        this.productoServicio = productoServicio;
        this.carritoCompras = carritoCompras;
        setBackground(Colors.BACKGROUND);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        this.titlePanel = new TitlePanel("Productos Disponibles");
        this.filterPanel = new ProductSearchHeader(productoServicio.obtenerCategorias());
        
        GridConstants gridConstants = new GridConstants();
        this.gridPanel = new ProductGridPanel(gridConstants, productoServicio, this);
        
        this.scrollPane = createScrollPane();
        
        setupUI();
        setupListeners();
        loadProducts();
    }
    
    private JScrollPane createScrollPane() {
        JScrollPane pane = new JScrollPane(gridPanel.getContentPanel());
        pane.setBorder(null);
        pane.getVerticalScrollBar().setUnitIncrement(25);
        pane.getHorizontalScrollBar().setUnitIncrement(25);
        pane.setViewportBorder(null);
        return pane;
    }
    
    private void setupUI() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Colors.BACKGROUND);
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        filterPanel.getSearchField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterProducts();
            }
        });
        
        filterPanel.getCategoriaCombo().addActionListener(_ -> filterProducts());
    }
    
    private void loadProducts() {
        List<Producto> productos = productoServicio.listarProductos();
        gridPanel.displayProducts(productos);
    }
    
    private void filterProducts() {
        String searchText = filterPanel.getSearchField().getText().toLowerCase();
        String selectedCategory = (String) filterPanel.getCategoriaCombo().getSelectedItem();
        
        List<Producto> filtered = productoServicio.listarProductos().stream()
            .filter(p -> p.getNombre().toLowerCase().contains(searchText) ||
                         p.getDescripcion().toLowerCase().contains(searchText))
            .filter(p -> selectedCategory.equals("Todas las categorías") ||
                         p.getCategoria().equals(selectedCategory))
            .toList();
        
        gridPanel.displayProducts(filtered);
        resetScrollPosition();
    }
    
    private void resetScrollPosition() {
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));
    }

    public void addToCart(Producto producto) {
        System.out.println("DEBUG: addToCart llamado para: " + producto.getNombre());
        
        QuantityInputDialog dialog = new QuantityInputDialog(
            SwingUtilities.getWindowAncestor(this),
            producto.getStock()
        );
        
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            int quantity = dialog.getSelectedQuantity();
            System.out.println("DEBUG: Cantidad confirmada: " + quantity);
            
            Optional<ProductoCarrito> existingProduct = carritoCompras.stream()
                .filter(p -> p.getProducto().getCodigo().equals(producto.getCodigo()))
                .findFirst();
            
            if (existingProduct.isPresent()) {
                System.out.println("DEBUG: Producto ya existe, aumentando cantidad");
                existingProduct.get().aumentarCantidad(quantity);
            } else {
                System.out.println("DEBUG: Producto nuevo, agregando al carrito");
                carritoCompras.add(new ProductoCarrito(producto, quantity));
            }
            
            // Mostrar carrito actualizado en terminal
            System.out.println("=== CARRITO ACTUALIZADO ===");
            for (ProductoCarrito item : carritoCompras) {
                System.out.println("- " + item.getProducto().getNombre() + 
                                " | Cantidad: " + item.getCantidadSeleccionada() + 
                                " | Subtotal: $" + String.format("%.2f", item.getSubtotal()));
            }
            System.out.println("============================");
            
            JOptionPane.showMessageDialog(this,
                quantity + " unidad(es) de " + producto.getNombre() + " añadidas al carrito",
                "Producto añadido", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @Override
    public void refreshTable() {
        loadProducts();
    }
}