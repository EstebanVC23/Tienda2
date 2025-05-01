package com.store.view.components.dialogs.user;

import com.store.models.Producto;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.dialogs.constants.ProductDetailsConstants;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProductDetailsDialog extends JDialog {
    private final ProductDetailsConstants constants;

    public ProductDetailsDialog(Producto producto) {
        this.constants = new ProductDetailsConstants();
        initializeDialog();
        setupUI(producto);
    }

    private void initializeDialog() {
        setTitle(constants.TITLE);
        setSize(constants.WIDTH, constants.HEIGHT);
        setModal(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        getRootPane().setBorder(new EmptyBorder(
            constants.ROOT_PADDING, constants.ROOT_PADDING,
            constants.ROOT_PADDING, constants.ROOT_PADDING));
    }

    private void setupUI(Producto producto) {
        JPanel mainPanel = createMainPanel();
        JPanel contentPanel = createContentPanel(producto);
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, Colors.BORDER),
            new EmptyBorder(
                constants.MAIN_PADDING, constants.MAIN_PADDING,
                constants.MAIN_PADDING, constants.MAIN_PADDING)
        ));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JPanel createContentPanel(Producto producto) {
        JPanel panel = new JPanel(new BorderLayout(
            constants.CONTENT_HGAP, 0));
        panel.setBackground(Color.WHITE);
        
        panel.add(createImagePanel(producto), BorderLayout.WEST);
        panel.add(createDetailsPanel(producto), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createImagePanel(Producto producto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(
            constants.IMAGE_SIZE, constants.IMAGE_SIZE));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(
                constants.IMAGE_PADDING, constants.IMAGE_PADDING,
                constants.IMAGE_PADDING, constants.IMAGE_PADDING)
        ));
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Img/card.jpeg"));
            Image scaled = icon.getImage().getScaledInstance(
                constants.IMAGE_SIZE - 20, constants.IMAGE_SIZE - 20, 
                Image.SCALE_SMOOTH);
            panel.add(new JLabel(new ImageIcon(scaled), SwingConstants.CENTER));
        } catch (Exception e) {
            panel.add(createNoImageLabel());
        }
        return panel;
    }

    private JLabel createNoImageLabel() {
        JLabel label = new JLabel(constants.NO_IMAGE_TEXT, SwingConstants.CENTER);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    private JPanel createDetailsPanel(Producto producto) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        panel.add(createHeaderPanel(producto));
        panel.add(createInfoPanel(producto));
        panel.add(createDescriptionPanel(producto));
        return panel;
    }

    private JPanel createHeaderPanel(Producto producto) {
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
        panel.add(Box.createRigidArea(new Dimension(0, constants.NAME_PRICE_SPACING)));
        panel.add(priceLabel);
        panel.add(Box.createRigidArea(new Dimension(0, constants.HEADER_BOTTOM_SPACING)));
        return panel;
    }

    private JPanel createInfoPanel(Producto producto) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 
            constants.INFO_HGAP, constants.INFO_VGAP));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(new EmptyBorder(
            0, 0, constants.INFO_BOTTOM_SPACING, 0));
        
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

    private JPanel createDescriptionPanel(Producto producto) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel title = new JLabel(constants.DESCRIPTION_TITLE);
        title.setFont(Fonts.SECTION_TITLE);
        title.setForeground(Colors.PRIMARY_TEXT);
        
        JTextArea description = new JTextArea(producto.getDescripcion());
        description.setFont(Fonts.BODY);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(Color.WHITE);
        description.setBorder(new EmptyBorder(
            constants.DESCRIPTION_TOP_SPACING, 0, 0, 0));
        
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, constants.DESCRIPTION_TITLE_SPACING)));
        panel.add(description);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(
            FlowLayout.RIGHT, 
            constants.BUTTON_HGAP, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(
            constants.BUTTON_TOP_SPACING, 0, 0, 0));
        
        JButton closeButton = createSecondaryButton(constants.CLOSE_TEXT);
        closeButton.addActionListener((ActionEvent _) -> dispose());
        
        JButton addButton = createPrimaryButton(constants.ADD_TO_CART_TEXT);
        
        panel.add(closeButton);
        panel.add(addButton);
        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Colors.PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
            new EmptyBorder(
                constants.BUTTON_VERTICAL_PADDING, constants.BUTTON_HORIZONTAL_PADDING,
                constants.BUTTON_VERTICAL_PADDING, constants.BUTTON_HORIZONTAL_PADDING)
        ));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Color.WHITE);
        button.setForeground(Colors.PRIMARY_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(
                constants.BUTTON_VERTICAL_PADDING, constants.BUTTON_HORIZONTAL_PADDING,
                constants.BUTTON_VERTICAL_PADDING, constants.BUTTON_HORIZONTAL_PADDING)
        ));
        return button;
    }
}