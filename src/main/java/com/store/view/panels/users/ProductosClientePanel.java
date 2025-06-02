package com.store.view.panels.users;

import com.store.models.Producto;
import com.store.services.IShoppingCartService;
import com.store.services.IProductoServicio;
import com.store.utils.Colors;
import com.store.view.components.TitlePanel;
import com.store.view.components.cards.spaces.ProductGridPanel;
import com.store.view.components.cards.spaces.ProductSearchHeader;
import com.store.view.panels.BasePanel;
import com.store.view.components.cards.constants.GridConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ProductosClientePanel extends BasePanel {
    private final IProductoServicio productoServicio;
    private final ProductSearchHeader filterPanel;
    private final ProductGridPanel gridPanel;
    private final JScrollPane scrollPane;
    private final TitlePanel titlePanel;

    public ProductosClientePanel(IProductoServicio productoServicio, 
                               IShoppingCartService cartService, 
                               int userId) {
        this.productoServicio = productoServicio;
        setBackground(Colors.BACKGROUND);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        this.titlePanel = new TitlePanel("Productos Disponibles");
        this.filterPanel = new ProductSearchHeader(productoServicio.obtenerCategorias());
        
        // Inicialización del gridPanel con las constantes
        GridConstants gridConstants = new GridConstants();
        this.gridPanel = new ProductGridPanel(gridConstants);
        if (cartService != null && userId != -1) {
            gridPanel.setCartService(cartService, userId);
        }
        
        this.scrollPane = createScrollPane();
        
        setupUI();
        setupListeners();
        loadProducts();
    }
    
    public ProductosClientePanel(IProductoServicio productoServicio) {
        this(productoServicio, null, -1);
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
    
    @Override
    public void refreshTable() {
        loadProducts();
    }
}