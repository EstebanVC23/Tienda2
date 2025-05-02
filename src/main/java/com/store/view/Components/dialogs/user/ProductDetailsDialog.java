package com.store.view.components.dialogs.user;

import com.store.models.Producto;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.dialogs.constants.ProductDetailsConstants;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ProductDetailsDialog extends BaseDetailsDialog {
    private final Producto producto;
    private final ProductDetailsConstants constants;

    public ProductDetailsDialog(Window parent, Producto producto) {
        super(parent, "Detalles del Producto", 700, 500);
        this.producto = producto;
        this.constants = new ProductDetailsConstants();
        setupLayout();
    }

    @Override
    protected void setupLayout() {
        JPanel mainPanel = createMainPanel();
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        
        panel.add(createImagePanel(), BorderLayout.WEST);
        panel.add(createDetailsPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Img/card.jpeg"));
            Image scaled = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            panel.add(new JLabel(new ImageIcon(scaled), SwingConstants.CENTER));
        } catch (Exception e) {
            panel.add(createNoImageLabel());
        }
        return panel;
    }

    private JLabel createNoImageLabel() {
        JLabel label = new JLabel("No hay imagen disponible", SwingConstants.CENTER);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        panel.add(createHeaderPanel());
        panel.add(createInfoPanel());
        panel.add(createDescriptionPanel());
        return panel;
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameLabel = new JLabel(producto.getNombre());
        nameLabel.setFont(Fonts.TITLE);
        nameLabel.setForeground(Colors.PRIMARY_TEXT);
        
        JLabel priceLabel = new JLabel(String.format("$%,.2f", producto.getPrecio()));
        priceLabel.setFont(Fonts.SUBTITLE);
        priceLabel.setForeground(Colors.ACCENT);
        
        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(priceLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        addInfoItem(panel, "Categoría", producto.getCategoria());
        addInfoItem(panel, "Stock disponible", String.valueOf(producto.getStock()));
        addInfoItem(panel, "Código", producto.getCodigo() != null ? producto.getCodigo() : "N/A");
        addInfoItem(panel, "Proveedor", producto.getProveedor() != null ? producto.getProveedor() : "N/A");
        return panel;
    }

    private void addInfoItem(JPanel panel, String label, String value) {
        panel.add(createInfoLabel(label + ":"));
        panel.add(createInfoValue(value));
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BOLD_BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    private JLabel createInfoValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.PRIMARY_TEXT);
        return label;
    }

    private JPanel createDescriptionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel title = new JLabel("Descripción");
        title.setFont(Fonts.SECTION_TITLE);
        title.setForeground(Colors.PRIMARY_TEXT);
        
        JTextArea description = new JTextArea(producto.getDescripcion());
        description.setFont(Fonts.BODY);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(Color.WHITE);
        description.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(description);
        return panel;
    }

    private JPanel createButtonPanel() {
        return createButtonPanel(
            constants.ADD_TO_CART_TEXT,
            constants.CLOSE_TEXT,
            this::addToCart,
            this::dispose
        );
    }

    private void addToCart() {
        // Lógica para añadir al carrito
        JOptionPane.showMessageDialog(this, 
            "Producto añadido al carrito", 
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}