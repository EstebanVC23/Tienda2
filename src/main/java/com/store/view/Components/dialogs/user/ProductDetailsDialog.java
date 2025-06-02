package com.store.view.components.dialogs.user;

import com.store.models.Producto;
import com.store.services.IShoppingCartService;
import com.store.view.components.dialogs.constants.ProductDetailsConstants;
import com.store.view.components.dialogs.user.components.*;

import javax.swing.*;
import java.awt.*;

public class ProductDetailsDialog extends BaseDetailsDialog {
    private final Producto producto;
    private final IShoppingCartService cartService;
    private final int userId;
    
    private ProductImagePanel imagePanel;
    private ProductHeaderPanel headerPanel;
    private ProductInfoPanel infoPanel;
    private ProductDescriptionPanel descriptionPanel;
    private ProductActionPanel actionPanel;
    private JButton addToCartButton;
    private JButton removeFromCartButton;
    private boolean cartFunctionalityEnabled;

    public ProductDetailsDialog(Window parent, Producto producto, 
                             IShoppingCartService cartService, int userId) {
        super(parent, ProductDetailsConstants.TITLE, 
             ProductDetailsConstants.WIDTH, 
             ProductDetailsConstants.HEIGHT);
        this.producto = producto;
        this.cartService = cartService;
        this.userId = userId;
        this.cartFunctionalityEnabled = (cartService != null && userId != -1);
        
        initComponents();
        setupLayout();
        
        // Solo actualizar botones del carrito si la funcionalidad está habilitada
        if (cartFunctionalityEnabled) {
            updateCartButtons();
        }
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
        
        // Crear botones solo si la funcionalidad del carrito está habilitada
        if (cartFunctionalityEnabled) {
            this.addToCartButton = new JButton(ProductDetailsConstants.ADD_TO_CART_TEXT);
            this.removeFromCartButton = new JButton(ProductDetailsConstants.REMOVE_FROM_CART_TEXT);
            
            this.actionPanel = new ProductActionPanel(
                this::addToCart,
                this::removeFromCart,
                this::dispose
            );
        } else {
            // Panel de acción solo con botón de cerrar
            this.actionPanel = new ProductActionPanel(
                null, // Sin acción de agregar
                null, // Sin acción de remover
                this::dispose
            );
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

    private void addToCart() {
        if (!cartFunctionalityEnabled) {
            showCartNotAvailableMessage();
            return;
        }
        
        try {
            boolean success = cartService.addToCart(userId, producto, 1);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    ProductDetailsConstants.ADD_TO_CART_SUCCESS_MESSAGE,
                    ProductDetailsConstants.ADD_TO_CART_TITLE,
                    JOptionPane.INFORMATION_MESSAGE);
                updateCartButtons();
            } else {
                JOptionPane.showMessageDialog(this,
                    ProductDetailsConstants.ADD_TO_CART_ERROR_MESSAGE,
                    ProductDetailsConstants.ADD_TO_CART_TITLE,
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error al agregar al carrito: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error inesperado al agregar el producto al carrito.",
                ProductDetailsConstants.ADD_TO_CART_TITLE,
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removeFromCart() {
        if (!cartFunctionalityEnabled) {
            showCartNotAvailableMessage();
            return;
        }
        
        try {
            boolean success = cartService.removeFromCart(userId, producto.getCodigo());
            if (success) {
                JOptionPane.showMessageDialog(this,
                    ProductDetailsConstants.REMOVE_FROM_CART_SUCCESS_MESSAGE,
                    ProductDetailsConstants.REMOVE_FROM_CART_TITLE,
                    JOptionPane.INFORMATION_MESSAGE);
                updateCartButtons();
            } else {
                JOptionPane.showMessageDialog(this,
                    ProductDetailsConstants.REMOVE_FROM_CART_ERROR_MESSAGE,
                    ProductDetailsConstants.REMOVE_FROM_CART_TITLE,
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error al remover del carrito: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error inesperado al remover el producto del carrito.",
                ProductDetailsConstants.REMOVE_FROM_CART_TITLE,
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCartButtons() {
        // Solo actualizar si la funcionalidad del carrito está habilitada
        if (!cartFunctionalityEnabled || addToCartButton == null || removeFromCartButton == null) {
            return;
        }
        
        try {
            boolean isInCart = cartService.getCartItems(userId).stream()
                .anyMatch(item -> item.getProduct().getCodigo().equals(producto.getCodigo()));
            
            addToCartButton.setVisible(!isInCart);
            removeFromCartButton.setVisible(isInCart);
        } catch (Exception e) {
            System.err.println("Error al actualizar botones del carrito: " + e.getMessage());
            e.printStackTrace();
            // En caso de error, mostrar solo el botón de agregar
            if (addToCartButton != null) {
                addToCartButton.setVisible(true);
            }
            if (removeFromCartButton != null) {
                removeFromCartButton.setVisible(false);
            }
        }
    }

    private void showCartNotAvailableMessage() {
        JOptionPane.showMessageDialog(this,
            "La funcionalidad del carrito no está disponible en este momento.",
            "Carrito no disponible",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void setAddToCartEnabled(boolean enabled) {
        this.cartFunctionalityEnabled = enabled && (cartService != null && userId != -1);
        
        if (actionPanel != null) {
            actionPanel.setVisible(cartFunctionalityEnabled);
        }
        
        if (addToCartButton != null) {
            addToCartButton.setVisible(cartFunctionalityEnabled);
        }
        
        if (removeFromCartButton != null) {
            removeFromCartButton.setVisible(cartFunctionalityEnabled);
        }
        
        // Actualizar botones si está habilitado
        if (cartFunctionalityEnabled) {
            updateCartButtons();
        }
    }
    
    // Método para verificar si la funcionalidad del carrito está disponible
    public boolean isCartFunctionalityEnabled() {
        return cartFunctionalityEnabled;
    }
    
    // Método para obtener información de debug
    public void printDebugInfo() {
        System.out.println("=== ProductDetailsDialog Debug Info ===");
        System.out.println("Cart service: " + (cartService != null ? "Available" : "NULL"));
        System.out.println("User ID: " + userId);
        System.out.println("Cart functionality enabled: " + cartFunctionalityEnabled);
        System.out.println("Product: " + (producto != null ? producto.getCodigo() : "NULL"));
        System.out.println("========================================");
    }
}