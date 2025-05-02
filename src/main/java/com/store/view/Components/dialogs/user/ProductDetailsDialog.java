package com.store.view.components.dialogs.user;

import com.store.models.Producto;
import com.store.view.components.dialogs.constants.ProductDetailsConstants;
import com.store.view.components.dialogs.user.components.*;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo que muestra los detalles completos de un producto, incluyendo imagen,
 * información básica, descripción y acciones disponibles.
 */
public class ProductDetailsDialog extends BaseDetailsDialog {
    private final Producto producto;
    
    private ProductImagePanel imagePanel;
    private ProductHeaderPanel headerPanel;
    private ProductInfoPanel infoPanel;
    private ProductDescriptionPanel descriptionPanel;
    private ProductActionPanel actionPanel;

    /**
     * Crea un nuevo diálogo de detalles de producto.
     * 
     * @param parent Ventana padre del diálogo
     * @param producto Producto cuyos detalles se mostrarán
     */
    public ProductDetailsDialog(Window parent, Producto producto) {
        super(parent, ProductDetailsConstants.TITLE, 
             ProductDetailsConstants.WIDTH, 
             ProductDetailsConstants.HEIGHT);
        this.producto = producto;
        initComponents();
        setupLayout();
    }

    /**
     * Inicializa todos los componentes del diálogo usando las constantes definidas.
     */
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
        
        this.actionPanel = new ProductActionPanel(
            this::addToCart,
            this::dispose,
            ProductDetailsConstants.ADD_TO_CART_TEXT,
            ProductDetailsConstants.CLOSE_TEXT
        );
    }

    /**
     * Configura el diseño principal del diálogo, organizando los componentes
     * en un layout BorderLayout con la imagen a la izquierda y los detalles a la derecha.
     */
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

    /**
     * Crea el panel que contiene los detalles del producto (encabezado, información y descripción).
     * 
     * @return JPanel con los componentes de detalles organizados verticalmente
     */
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        panel.add(headerPanel);
        panel.add(infoPanel);
        panel.add(descriptionPanel);
        
        return panel;
    }

    /**
     * Maneja la acción de agregar el producto al carrito, mostrando un mensaje de confirmación.
     */
    private void addToCart() {
        JOptionPane.showMessageDialog(this,
            ProductDetailsConstants.ADD_TO_CART_MESSAGE,
            ProductDetailsConstants.ADD_TO_CART_TITLE,
            JOptionPane.INFORMATION_MESSAGE);
    }
}