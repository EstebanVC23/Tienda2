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

/**
 * Componente de tarjeta de producto que muestra información básica de un producto
 * y permite interactuar con él.
 */
public class ProductCard extends BaseCard {
    private final Producto producto;

    /**
     * Crea una nueva tarjeta de producto.
     * @param producto El producto a mostrar en la tarjeta.
     */
    public ProductCard(Producto producto) {
        super();
        this.producto = producto;
        setupCard();
    }

    /**
     * Configura los aspectos básicos de la tarjeta.
     */
    private void setupCard() {
        setPreferredSize(ProductCardConstants.CARD_SIZE);
        add(createImagePanel(), BorderLayout.CENTER);
        add(createDetailsPanel(), BorderLayout.SOUTH);
        addMouseListener(new ProductCardMouseHandler(this));
    }

    /**
     * Crea el panel que contiene la imagen del producto.
     * @return Panel con la imagen del producto.
     */
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(ProductCardConstants.IMAGE_SIZE);
        panel.setBackground(ProductCardConstants.IMAGE_BG_COLOR);
        panel.setBorder(createImageBorder());
        panel.add(createImageContent(), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el borde para el panel de la imagen.
     * @return Borde compuesto para la imagen.
     */
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

    /**
     * Crea el contenido de la imagen del producto.
     * @return Componente con la imagen escalada o mensaje de error.
     */
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

    /**
     * Crea una etiqueta para mostrar cuando no se puede cargar la imagen.
     * @return Etiqueta con mensaje de error.
     */
    private JLabel createErrorLabel() {
        JLabel label = new JLabel("Imagen no disponible", SwingConstants.CENTER);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    /**
     * Crea el panel de detalles del producto.
     * @return Panel con información detallada del producto.
     */
    private JPanel createDetailsPanel() {
        JPanel panel = createVerticalPanel();
        panel.setBorder(new EmptyBorder(ProductCardConstants.DETAILS_PADDING_TOP, 0, 0, 0));
        addDetailsComponents(panel);
        return panel;
    }

    /**
     * Añade los componentes de detalles al panel especificado.
     * @param panel Panel al que se añadirán los componentes.
     */
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

    /**
     * Crea la etiqueta de precio del producto.
     * @return Etiqueta con el precio formateado.
     */
    private JLabel createPriceLabel() {
        return createLabel(String.format("$%,.2f", producto.getPrecio()), Fonts.SUBTITLE, Colors.ACCENT);
    }

    /**
     * Crea el tag de categoría del producto.
     * @return Panel que contiene la etiqueta de categoría.
     */
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

    /**
     * Crea la etiqueta de stock disponible.
     * @return Etiqueta con la cantidad de stock.
     */
    private JLabel createStockLabel() {
        String text = "Disponibles: " + producto.getStock();
        Color color = producto.getStock() > 0 ? Colors.PRIMARY_TEXT : Colors.ACCENT;
        return createLabel(text, Fonts.SMALL, color);
    }

    /**
     * Crea el botón para añadir al carrito.
     * @return Botón configurado para añadir productos al carrito.
     */
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

    /**
     * Muestra un diálogo modal con los detalles completos del producto.
     * El diálogo se centra respecto a la ventana padre de la aplicación y
     * muestra información detallada del producto actual.
     * 
     * @throws IllegalStateException si el producto es nulo o no está inicializado correctamente
     */
    protected void showProductDetails() {
        if (producto == null) {
            throw new IllegalStateException("No se puede mostrar detalles: el producto es nulo");
        }
        
        Window parentWindow = null;
        try {
            parentWindow = SwingUtilities.getWindowAncestor(this);
        } catch (Exception e) {
            parentWindow = null;
        }
        
        ProductDetailsDialog detailsDialog;
        try {
            detailsDialog = new ProductDetailsDialog(parentWindow, producto);
            detailsDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            
            if (parentWindow != null) {
                detailsDialog.setLocationRelativeTo(parentWindow);
            } else {
                detailsDialog.setLocationRelativeTo(null);
            }
            
            detailsDialog.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                parentWindow,
                "Error al mostrar los detalles del producto",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            throw new RuntimeException("Error al crear el diálogo de detalles", e);
        }
    }
}