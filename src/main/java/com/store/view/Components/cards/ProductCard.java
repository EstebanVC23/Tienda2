package com.store.view.components.cards;

import com.store.models.Producto;
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

    public ProductCard(Producto producto) {
        super();
        this.producto = producto;
        setupCard();
    }

    private void setupCard() {
        setPreferredSize(ProductCardConstants.CARD_SIZE);
        add(createImagePanel(), BorderLayout.CENTER);
        add(createDetailsPanel(), BorderLayout.SOUTH);
        addMouseListener(new ProductCardMouseHandler(this));
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

    private JLabel createErrorLabel() {
        JLabel label = new JLabel("Imagen no disponible", SwingConstants.CENTER);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    private JPanel createDetailsPanel() {
        JPanel panel = createVerticalPanel();
        panel.setBorder(new EmptyBorder(ProductCardConstants.DETAILS_PADDING_TOP, 0, 0, 0));
        addDetailsComponents(panel);
        return panel;
    }

    private void addDetailsComponents(JPanel panel) {
        panel.add(createLabel(producto.getNombre(), Fonts.SECTION_TITLE, Colors.PRIMARY_TEXT));
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_SMALL)));
        panel.add(createCategoryTag());
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_MEDIUM)));
        panel.add(createPriceLabel());
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_SMALL)));
        panel.add(createStockLabel());
        panel.add(Box.createRigidArea(new Dimension(0, ProductCardConstants.SPACING_LARGE)));
        panel.add(createAddToCartButton());
    }

    private JLabel createPriceLabel() {
        return createLabel(String.format("$%,.2f", producto.getPrecio()), Fonts.SUBTITLE, Colors.ACCENT);
    }

    private JPanel createCategoryTag() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.WHITE);
        JLabel label = createLabel(producto.getCategoria(), Fonts.SMALL, Colors.PRIMARY_BLUE);
        label.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Colors.LIGHT_BLUE, 1),
            new EmptyBorder(3, 8, 3, 8)
        ));
        panel.add(label);
        return panel;
    }

    private JLabel createStockLabel() {
        String text = "Disponibles: " + producto.getStock();
        Color color = producto.getStock() > 0 ? Colors.PRIMARY_TEXT : Colors.ACCENT;
        return createLabel(text, Fonts.SMALL, color);
    }

    private JButton createAddToCartButton() {
        JButton button = new JButton("Añadir al carrito");
        button.setFont(Fonts.BUTTON);
        button.setBackground(Colors.PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
            new EmptyBorder(8, 0, 8, 0)
        ));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, ProductCardConstants.BUTTON_MAX_HEIGHT));
        return button;
    }

    protected void showProductDetails() {
        new ProductDetailsDialog(producto).setVisible(true);
    }
}