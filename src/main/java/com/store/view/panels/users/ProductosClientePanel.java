package com.store.view.panels.users;

import com.store.models.Producto;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.view.components.TitlePanel;
import com.store.view.components.cards.spaces.ProductGridPanel;
import com.store.view.components.cards.spaces.ProductSearchHeader;
import com.store.view.panels.BasePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra los productos disponibles para los clientes.
 * Incluye funcionalidades de búsqueda, filtrado por categoría y visualización en formato de cuadrícula.
 * Extiende de BasePanel para mantener consistencia en la interfaz.
 */
public class ProductosClientePanel extends BasePanel {
    private final ProductoServicioImpl productoServicio;
    private final ProductSearchHeader filterPanel;
    private final ProductGridPanel gridPanel;
    private final JScrollPane scrollPane;
    private final TitlePanel titlePanel;

    /**
     * Construye un nuevo panel de productos para clientes.
     * @param productoServicio Servicio para operaciones con productos
     */
    public ProductosClientePanel(ProductoServicioImpl productoServicio) {
        this.productoServicio = productoServicio;
        setBackground(Colors.BACKGROUND);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        this.titlePanel = new TitlePanel("Productos Disponibles");
        this.filterPanel = new ProductSearchHeader(productoServicio.obtenerCategorias());
        this.gridPanel = new ProductGridPanel();
        this.scrollPane = createScrollPane();
        
        setupUI();
        setupListeners();
        loadProducts();
    }
    
    /**
     * Crea y configura el panel de desplazamiento para la cuadrícula de productos.
     * @return JScrollPane configurado
     */
    private JScrollPane createScrollPane() {
        JScrollPane pane = new JScrollPane(gridPanel.getContentPanel());
        pane.setBorder(null);
        pane.getVerticalScrollBar().setUnitIncrement(25);
        pane.getHorizontalScrollBar().setUnitIncrement(25);
        pane.setViewportBorder(null);
        return pane;
    }
    
    /**
     * Configura la interfaz de usuario principal del panel.
     */
    private void setupUI() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Colors.BACKGROUND);
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Configura los listeners para los eventos de búsqueda y filtrado.
     */
    private void setupListeners() {
        filterPanel.getSearchField().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterProducts();
            }
        });
        
        filterPanel.getCategoriaCombo().addActionListener(_ -> filterProducts());
    }
    
    /**
     * Carga todos los productos disponibles desde el servicio.
     */
    private void loadProducts() {
        List<Producto> productos = productoServicio.listarProductos();
        gridPanel.displayProducts(productos);
    }
    
    /**
     * Filtra los productos según el texto de búsqueda y la categoría seleccionada.
     */
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
    
    /**
     * Reinicia la posición de desplazamiento del scroll pane al inicio.
     */
    private void resetScrollPosition() {
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));
    }
    
    /**
     * Actualiza la lista de productos mostrados (implementación de BasePanel).
     */
    @Override
    public void refreshTable() {
        loadProducts();
    }
}