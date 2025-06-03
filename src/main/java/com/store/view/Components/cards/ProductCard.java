package com.store.view.components.cards;

import com.store.models.Producto;
import com.store.models.ProductoCarrito;
import com.store.services.ProductoServicioImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.cards.constants.ProductCardConstants;
import com.store.view.components.dialogs.user.ProductDetailsDialog;
import com.store.view.components.buttons.CustomButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class ProductCard extends BaseCard {
    private final Producto producto;
    private final ProductoServicioImpl productService;
    private Consumer<ProductoCarrito> onAddToCart;

    public ProductCard(Producto producto, ProductoServicioImpl productService) {
        super();
        this.productService = productService;
        this.producto = producto;
        setupCard();
        setupMouseListeners();
    }

    public void setOnAddToCart(Consumer<ProductoCarrito> listener) {
        this.onAddToCart = listener;
    }

    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showProductDetails();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    protected void setupCard() {
        setLayout(new BorderLayout());
        setPreferredSize(ProductCardConstants.CARD_SIZE);
        add(createImagePanel(), BorderLayout.CENTER);
        add(createDetailsAndButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createAddButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 0, 10, 10));
        
        if (producto.getStock() > 0) {
            CustomButton addButton = new CustomButton("Añadir", new Color(46, 204, 113), Color.WHITE);
            addButton.setPreferredSize(new Dimension(90, 32));
            addButton.setFont(Fonts.BUTTON);
            addButton.setCornerRadius(8);
            addButton.setRound(true);
            
            addButton.addActionListener(_ -> {
                QuantityInputDialog dialog = new QuantityInputDialog(
                    SwingUtilities.getWindowAncestor(this),
                    producto.getStock()
                );
                
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    int selectedQuantity = dialog.getSelectedQuantity();
                    ProductoCarrito productoCarrito = new ProductoCarrito(producto, selectedQuantity);
                    
                    if (onAddToCart != null) {
                        onAddToCart.accept(productoCarrito);
                    }
                }
            });
            
            panel.add(addButton);
        } else {
            JLabel outOfStock = new JLabel("Agotado");
            outOfStock.setForeground(Colors.ACCENT);
            outOfStock.setFont(Fonts.SMALL);
            panel.add(outOfStock);
        }
        
        return panel;
    }

    private JPanel createDetailsAndButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.add(createDetailsPanel());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createAddButtonPanel());
        return panel;
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

    public void showProductDetails() {
        if (producto == null) {
            throw new IllegalStateException("No se puede mostrar detalles: el producto es nulo");
        }
        
        Producto productoActualizado = productService.obtenerProductoPorCodigo(producto.getCodigo());
        
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        ProductDetailsDialog detailsDialog = new ProductDetailsDialog(
            parentWindow, 
            productoActualizado
        );
        
        detailsDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        detailsDialog.setLocationRelativeTo(parentWindow);
        detailsDialog.setVisible(true);
    }
}