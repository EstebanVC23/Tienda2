package com.store.view.components.cards.spaces;

import com.store.models.Producto;
import com.store.models.ProductoCarrito;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.view.components.cards.ProductCard;
import com.store.view.components.cards.constants.GridConstants;
import com.store.view.panels.users.ProductosClientePanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Panel que muestra una cuadrícula de productos en forma de tarjetas.
 * Permite la visualización, interacción y gestión de productos en un layout de cuadrícula.
 */
public class ProductGridPanel extends JPanel {
    private final JPanel contentPanel;
    private final GridConstants constants;
    private ProductoServicioImpl productService;
    private ProductosClientePanel parentPanel;

    /**
     * Constructor que inicializa el panel de cuadrícula de productos.
     * 
     * @param constants Configuraciones de la cuadrícula (columnas, espacios, etc.)
     * @param productService Servicio para operaciones con productos
     * @param parentPanel Panel padre que contiene este componente
     */
    public ProductGridPanel(GridConstants constants, ProductoServicioImpl productService, ProductosClientePanel parentPanel) {
        this.constants = constants;
        this.productService = productService;
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND);
        
        contentPanel = new JPanel(new GridLayout(0, constants.COLUMN_COUNT, 
            constants.H_GAP, constants.V_GAP));
        contentPanel.setBackground(Colors.BACKGROUND);
        
        enableFastMouseWheelScrolling(contentPanel);
    }

    /**
     * Muestra una lista de productos en la cuadrícula.
     * 
     * @param productos Lista de productos a mostrar. Si está vacía, muestra un estado vacío.
     */
    public void displayProducts(List<Producto> productos) {
        contentPanel.removeAll();
        
        if (productos.isEmpty()) {
            showEmptyState();
        } else {
            showProductsGrid(productos);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Muestra un mensaje indicando que no hay productos disponibles.
     */
    private void showEmptyState() {
        contentPanel.setLayout(new BorderLayout());
        JLabel emptyLabel = new JLabel(constants.EMPTY_TEXT, SwingConstants.CENTER);
        emptyLabel.setFont(constants.EMPTY_FONT);
        emptyLabel.setForeground(Colors.SECONDARY_TEXT);
        contentPanel.add(emptyLabel, BorderLayout.CENTER);
    }

    /**
     * Muestra los productos en un layout de cuadrícula.
     * 
     * @param productos Lista de productos a mostrar
     */
    private void showProductsGrid(List<Producto> productos) {
        contentPanel.setLayout(new GridLayout(0, constants.COLUMN_COUNT,
            constants.H_GAP, constants.V_GAP));
                
        productos.forEach(p -> {
            ProductCard card = new ProductCard(p, productService);
                        
            card.setOnAddToCart(productoCarrito -> {
                agregarProductoCarritoDirecto(productoCarrito);
            });
                        
            contentPanel.add(card);
        });
    }

    /**
     * Agrega un producto al carrito de compras.
     * 
     * @param productoCarrito Producto con la cantidad seleccionada a agregar
     */
    private void agregarProductoCarritoDirecto(ProductoCarrito productoCarrito) {
        Optional<ProductoCarrito> existingProduct = parentPanel.getCarritoCompras().stream()
            .filter(p -> p.getProducto().getCodigo().equals(productoCarrito.getProducto().getCodigo()))
            .findFirst();
        
        if (existingProduct.isPresent()) {
            existingProduct.get().aumentarCantidad(productoCarrito.getCantidadSeleccionada());
        } else {
            parentPanel.getCarritoCompras().add(productoCarrito);
        }
        
        JOptionPane.showMessageDialog(this,
            productoCarrito.getCantidadSeleccionada() + " unidad(es) de " + 
            productoCarrito.getProducto().getNombre() + " añadidas al carrito",
            "Producto añadido", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Configura el desplazamiento rápido con la rueda del ratón.
     * 
     * @param panel Panel al que se aplicará la configuración de desplazamiento
     */
    private void enableFastMouseWheelScrolling(JPanel panel) {
        panel.addMouseWheelListener(e -> {
            JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(
                JScrollPane.class, panel);
            if (scrollPane != null) {
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                int units = e.getUnitsToScroll() * constants.SCROLL_SPEED;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + units);
            }
        });
    }

    /**
     * Obtiene el panel de contenido principal.
     * 
     * @return Panel que contiene los productos
     */
    public JPanel getContentPanel() {
        return contentPanel;
    }
}