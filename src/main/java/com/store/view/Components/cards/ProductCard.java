package com.store.view.components.cards;

import com.store.models.Producto;
import com.store.services.IShoppingCartService;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.cards.constants.ProductCardConstants;
import com.store.view.components.dialogs.user.ProductDetailsDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProductCard extends BaseCard {
    private final Producto producto;
    private final IShoppingCartService cartService;
    private final int userId;

    public ProductCard(Producto producto, IShoppingCartService cartService, int userId) {
        super();
        this.producto = producto;
        this.cartService = cartService;
        this.userId = userId;
        setupCard();
    }

    // Constructor adicional para compatibilidad
    public ProductCard(Producto producto) {
        this(producto, null, -1); // Valores por defecto para mantener compatibilidad
    }

    protected void setupCard() {
        setPreferredSize(ProductCardConstants.CARD_SIZE);
        add(createImagePanel(), BorderLayout.CENTER);
        add(createDetailsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(ProductCardConstants.IMAGE_SIZE);
        panel.setBackground(ProductCardConstants.IMAGE_BG_COLOR);
        panel.setBorder(createImageBorder());
        panel.add(createImageContent(), BorderLayout.CENTER);
        return panel;
    }

    private Border createImageBorder() {
        return new CompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(
                ProductCardConstants.IMAGE_PADDING,
                ProductCardConstants.IMAGE_PADDING,
                ProductCardConstants.IMAGE_PADDING,
                ProductCardConstants.IMAGE_PADDING
            )
        );
    }

    private Component createImageContent() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Img/card.jpeg"));
            Image scaled = icon.getImage().getScaledInstance(
                ProductCardConstants.IMAGE_SIZE.width - ProductCardConstants.IMAGE_SCALE_OFFSET,
                ProductCardConstants.IMAGE_SIZE.height - ProductCardConstants.IMAGE_SCALE_OFFSET,
                Image.SCALE_SMOOTH
            );
            return new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
        } catch (Exception e) {
            return createErrorLabel();
        }
    }

    protected JLabel createErrorLabel() {
        JLabel label = new JLabel("Imagen no disponible", SwingConstants.CENTER);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    protected JPanel createDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(ProductCardConstants.DETAILS_PADDING_TOP, 0, 0, 0));
        
        panel.add(createLabel(producto.getNombre(), Fonts.SECTION_TITLE, Colors.PRIMARY_TEXT));
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_SMALL)));
        panel.add(createCategoryTag());
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_MEDIUM)));
        panel.add(createPriceLabel());
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_SMALL)));
        panel.add(createStockLabel());
        
        if (cartService != null && userId != -1) {
            panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_LARGE)));
            panel.add(createAddToCartButton());
        }
        
        return panel;
    }

    protected JLabel createPriceLabel() {
        return new JLabel(String.format("$%,.2f", producto.getPrecio())) {{
            setFont(Fonts.SUBTITLE);
            setForeground(Colors.ACCENT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }};
    }

    protected JPanel createCategoryTag() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(producto.getCategoria()) {{
            setFont(Fonts.SMALL);
            setForeground(Colors.PRIMARY_BLUE);
            setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(Colors.LIGHT_BLUE, 1),
                new EmptyBorder(3, 8, 3, 8)
            ));
        }});
        return panel;
    }

    protected JLabel createStockLabel() {
        String text = "Disponibles: " + producto.getStock();
        return new JLabel(text) {{
            setFont(Fonts.SMALL);
            setForeground(producto.getStock() > 0 ? Colors.PRIMARY_TEXT : Colors.ACCENT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }};
    }

    protected JButton createAddToCartButton() {
        JButton button = new JButton("Añadir al carrito") {{
            setFont(Fonts.BUTTON);
            setBackground(Colors.PRIMARY_BLUE);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
                new EmptyBorder(8, 0, 8, 0)
            ));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, ProductCardConstants.BUTTON_MAX_HEIGHT));
            addActionListener(_ -> handleAddToCart());
        }};
        return button;
    }

    protected void handleAddToCart() {
        if (cartService == null || userId == -1) return;
        
        boolean success = cartService.addToCart(userId, producto, 1);
        String message = success ? "Producto agregado al carrito" : "No se pudo agregar el producto al carrito";
        int messageType = success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        
        JOptionPane.showMessageDialog(
            SwingUtilities.getWindowAncestor(this),
            message,
            "Carrito",
            messageType
        );
    }

    public void showProductDetails() {
        if (producto == null) {
            throw new IllegalStateException("No se puede mostrar detalles: el producto es nulo");
        }
        
        if (cartService == null || userId == -1) {
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                "Funcionalidad no disponible",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        ProductDetailsDialog detailsDialog = new ProductDetailsDialog(
            parentWindow, 
            producto, 
            cartService, 
            userId
        );
        detailsDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        detailsDialog.setLocationRelativeTo(parentWindow);
        detailsDialog.setVisible(true);
    }
}