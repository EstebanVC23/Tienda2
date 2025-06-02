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

    public ProductDetailsDialog(Window parent, Producto producto, 
                             IShoppingCartService cartService, int userId) {
        super(parent, ProductDetailsConstants.TITLE, 
             ProductDetailsConstants.WIDTH, 
             ProductDetailsConstants.HEIGHT);
        this.producto = producto;
        this.cartService = cartService;
        this.userId = userId;
        initComponents();
        setupLayout();
        updateCartButtons();
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
        
        // Modificamos el actionPanel para incluir ambos botones
        this.addToCartButton = new JButton(ProductDetailsConstants.ADD_TO_CART_TEXT);
        this.removeFromCartButton = new JButton(ProductDetailsConstants.REMOVE_FROM_CART_TEXT);
        
        this.actionPanel = new ProductActionPanel(
            this::addToCart,
            this::removeFromCart,
            this::dispose
        );
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
    }
    
    private void removeFromCart() {
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
    }
    
    private void updateCartButtons() {
        boolean isInCart = cartService.getCartItems(userId).stream()
            .anyMatch(item -> item.getProduct().getCodigo().equals(producto.getCodigo()));
        
        addToCartButton.setVisible(!isInCart);
        removeFromCartButton.setVisible(isInCart);
    }
}