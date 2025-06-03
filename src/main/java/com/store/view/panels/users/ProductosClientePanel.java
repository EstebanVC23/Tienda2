package com.store.view.panels.users;

import com.store.models.Producto;
import com.store.models.ProductoCarrito;
import com.store.services.ProductoServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.view.components.TitlePanel;
import com.store.view.components.cards.spaces.ProductGridPanel;
import com.store.view.components.cards.spaces.ProductSearchHeader;
import com.store.view.components.dialogs.user.CarritoDialog;
import com.store.view.components.dialogs.user.QuantityInputDialog;
import com.store.view.panels.BasePanel;
import com.store.view.userView.UserView;
import com.store.view.components.cards.constants.GridConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Panel para visualización y gestión de productos por parte de clientes.
 */
public class ProductosClientePanel extends BasePanel {
    private final ProductoServicioImpl productoServicio;
    private final ProductSearchHeader filterPanel;
    private final ProductGridPanel gridPanel;
    private final JScrollPane scrollPane;
    private final TitlePanel titlePanel;
    private final List<ProductoCarrito> carritoCompras;
    private final SaleServiceImpl saleService;

    /**
     * Constructor del panel de productos para clientes.
     * 
     * @param productoServicio Servicio de productos
     * @param carritoCompras Lista de productos en el carrito
     * @param saleService Servicio de ventas
     */
    public ProductosClientePanel(ProductoServicioImpl productoServicio, 
                               List<ProductoCarrito> carritoCompras,
                               SaleServiceImpl saleService) {
        this.productoServicio = productoServicio;
        this.carritoCompras = carritoCompras;
        this.saleService = saleService;
        setBackground(Colors.BACKGROUND);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        this.titlePanel = createTitlePanelWithCartButton();
        this.filterPanel = new ProductSearchHeader(productoServicio.obtenerCategorias());
        
        GridConstants gridConstants = new GridConstants();
        this.gridPanel = new ProductGridPanel(gridConstants, productoServicio, this);
        
        this.scrollPane = createScrollPane();
        
        setupUI();
        setupListeners();
        loadProducts();
    }

    /**
     * Obtiene la lista de productos en el carrito.
     * 
     * @return Lista de ProductoCarrito
     */
    public List<ProductoCarrito> getCarritoCompras() {
        return carritoCompras;
    }
    
    /**
     * Crea el panel de título con botón de carrito.
     * 
     * @return Panel de título configurado
     */
    private TitlePanel createTitlePanelWithCartButton() {
        TitlePanel panel = new TitlePanel("Productos Disponibles");
        
        JButton cartButton = new JButton("Ver carrito actual");
        cartButton.setBackground(Colors.PRIMARY);
        cartButton.setForeground(Color.WHITE);
        cartButton.setFocusPainted(false);
        cartButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        cartButton.addActionListener(_ -> showCurrentCart());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Colors.BACKGROUND);
        buttonPanel.add(cartButton);
        
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Muestra el diálogo del carrito de compras actual.
     */
    private void showCurrentCart() {
        if (carritoCompras.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El carrito está vacío",
                "Carrito de compras",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        CarritoDialog dialog = new CarritoDialog(
            SwingUtilities.getWindowAncestor(this),
            carritoCompras,
            saleService,
            productoServicio,
            ((UserView)SwingUtilities.getWindowAncestor(this)).getUsuario()
        );
        dialog.setVisible(true);
    }
    
    /**
     * Crea el panel de desplazamiento para la grilla de productos.
     * 
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
     * Configura la interfaz de usuario principal.
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
     * Configura los listeners para los componentes de filtrado.
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
     * Carga los productos en la grilla.
     */
    private void loadProducts() {
        List<Producto> productos = productoServicio.listarProductos();
        gridPanel.displayProducts(productos);
    }
    
    /**
     * Filtra los productos según los criterios seleccionados.
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
     * Reinicia la posición de desplazamiento del scroll.
     */
    private void resetScrollPosition() {
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));
    }

    /**
     * Agrega un producto al carrito de compras.
     * 
     * @param producto Producto a agregar
     */
    public void addToCart(Producto producto) {
        QuantityInputDialog dialog = new QuantityInputDialog(
            SwingUtilities.getWindowAncestor(this),
            producto.getNombre(),
            producto.getStock()
        );
        
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            int quantity = dialog.getSelectedQuantity();
            
            Optional<ProductoCarrito> existingProduct = carritoCompras.stream()
                .filter(p -> p.getProducto().getCodigo().equals(producto.getCodigo()))
                .findFirst();
            
            if (existingProduct.isPresent()) {
                existingProduct.get().aumentarCantidad(quantity);
            } else {
                carritoCompras.add(new ProductoCarrito(producto, quantity));
            }
            
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