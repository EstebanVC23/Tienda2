package com.store.view.components.dialogs.user;

import com.store.models.Producto;
import com.store.models.ProductoCarrito;
import com.store.view.components.dialogs.constants.ProductDetailsConstants;
import com.store.view.components.dialogs.user.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ProductDetailsDialog extends BaseDetailsDialog {
    private final Producto producto;
    private Consumer<ProductoCarrito> onAddToCart; // Callback para agregar al carrito
    
    private ProductImagePanel imagePanel;
    private ProductHeaderPanel headerPanel;
    private ProductInfoPanel infoPanel;
    private ProductDescriptionPanel descriptionPanel;
    private ProductActionPanel actionPanel;

    // Constructor modificado para recibir el callback
    public ProductDetailsDialog(Window parent, Producto producto, Consumer<ProductoCarrito> onAddToCart) {
        super(parent, ProductDetailsConstants.TITLE, 
             ProductDetailsConstants.WIDTH, 
             ProductDetailsConstants.HEIGHT);
        this.producto = producto;
        this.onAddToCart = onAddToCart; // Asignar el callback
        
        initComponents();
        setupLayout();
    }

    // Constructor sobrecargado para mantener compatibilidad (sin callback)
    public ProductDetailsDialog(Window parent, Producto producto) {
        this(parent, producto, null);
    }

    private void initComponents() {
        this.imagePanel = new ProductImagePanel(
            ProductDetailsConstants.IMAGE_SIZE,
            ProductDetailsConstants.IMAGE_SCALE_OFFSET,
            ProductDetailsConstants.IMAGE_PADDING,
            ProductDetailsConstants.IMAGE_BG_COLOR,
            ProductDetailsConstants.NO_IMAGE_TEXT
        );
        
        this.headerPanel = new ProductHeaderPanel(
            producto.getNombre(),
            producto.getPrecio(),
            ProductDetailsConstants.NAME_PRICE_SPACING,
            ProductDetailsConstants.HEADER_BOTTOM_SPACING
        );
        
        this.infoPanel = new ProductInfoPanel(
            producto.getCategoria(),
            producto.getStock(),
            producto.getCodigo(),
            producto.getProveedor(),
            ProductDetailsConstants.INFO_HGAP,
            ProductDetailsConstants.INFO_VGAP,
            ProductDetailsConstants.INFO_BOTTOM_SPACING
        );
        
        this.descriptionPanel = new ProductDescriptionPanel(
            producto.getDescripcion(),
            ProductDetailsConstants.DESCRIPTION_TITLE,
            ProductDetailsConstants.DESCRIPTION_TITLE_SPACING,
            ProductDetailsConstants.DESCRIPTION_TOP_SPACING
        );
        
        // Panel de acción con el callback actualizado
        this.actionPanel = new ProductActionPanel(
            this::openQuantityDialog, // Acción para agregar al carrito
            null, // No hay acción de quitar del carrito
            this::dispose // Acción para cerrar
        );
    }

    private void openQuantityDialog() {
        QuantityInputDialog quantityDialog = new QuantityInputDialog(
            this, 
            producto.getNombre(), 
            producto.getStock()
        );
        
        quantityDialog.setVisible(true);
        
        if (quantityDialog.isConfirmed()) {
            int selectedQuantity = quantityDialog.getSelectedQuantity();
            
            // Si hay callback, usar la lógica real del carrito
            if (onAddToCart != null) {
                ProductoCarrito productoCarrito = new ProductoCarrito(producto, selectedQuantity);
                onAddToCart.accept(productoCarrito);
                // Cerrar el diálogo después de agregar al carrito
                dispose();
            } else {
                // Fallback: solo mostrar mensaje (para compatibilidad)
                JOptionPane.showMessageDialog(
                    this, 
                    String.format("Agregado al carrito: %d unidades de %s", 
                        selectedQuantity, producto.getNombre()),
                    "Producto agregado",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    @Override
    protected void setupLayout() {
        JPanel mainPanel = createMainPanel();
        
        JPanel contentPanel = new JPanel(new BorderLayout(
            ProductDetailsConstants.CONTENT_HGAP, 
            ProductDetailsConstants.CONTENT_VGAP));
        contentPanel.setBackground(Color.WHITE);
        
        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(createDetailsPanel(), BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        panel.add(headerPanel);
        panel.add(infoPanel);
        panel.add(descriptionPanel);
        
        return panel;
    }
}