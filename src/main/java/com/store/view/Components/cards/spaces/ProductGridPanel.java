package com.store.view.components.cards.spaces;

import com.store.models.Producto;
import com.store.utils.Colors;
import com.store.view.components.cards.ProductCard;
import com.store.view.components.cards.constants.GridConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra una cuadrícula de productos con capacidad de desplazamiento.
 * Permite visualizar productos en un diseño de cuadrícula adaptable.
 */
public class ProductGridPanel extends JPanel {
    private final JPanel contentPanel;
    private final GridConstants constants;

    /**
     * Constructor que inicializa el panel con constantes personalizadas.
     * @param constants Constantes de configuración de la cuadrícula
     */
    public ProductGridPanel(GridConstants constants) {
        this.constants = constants;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND);
        
        contentPanel = new JPanel(new GridLayout(0, constants.COLUMN_COUNT, 
            constants.H_GAP, constants.V_GAP));
        contentPanel.setBackground(Colors.BACKGROUND);
        
        enableFastMouseWheelScrolling(contentPanel);
    }

    /**
     * Constructor por defecto que usa constantes predeterminadas.
     */
    public ProductGridPanel() {
        this(new GridConstants());
    }

    /**
     * Muestra la lista de productos en la cuadrícula.
     * @param productos Lista de productos a mostrar
     */
    public void displayProducts(List<Producto> productos) {
        contentPanel.removeAll();
        
        if (productos.isEmpty()) {
            showEmptyState();
        } else {
            showProductsGrid(productos);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Muestra un mensaje cuando no hay productos disponibles.
     */
    private void showEmptyState() {
        contentPanel.setLayout(new BorderLayout());
        JLabel emptyLabel = new JLabel(constants.EMPTY_TEXT, SwingConstants.CENTER);
        emptyLabel.setFont(constants.EMPTY_FONT);
        emptyLabel.setForeground(Colors.SECONDARY_TEXT);
        contentPanel.add(emptyLabel, BorderLayout.CENTER);
    }

    /**
     * Muestra los productos en formato de cuadrícula.
     * @param productos Lista de productos a mostrar
     */
    private void showProductsGrid(List<Producto> productos) {
        contentPanel.setLayout(new GridLayout(0, constants.COLUMN_COUNT, 
            constants.H_GAP, constants.V_GAP));
        productos.forEach(p -> contentPanel.add(new ProductCard(p)));
    }

    /**
     * Habilita el desplazamiento rápido con la rueda del mouse.
     * @param panel Panel al que aplicar el desplazamiento
     */
    private void enableFastMouseWheelScrolling(JPanel panel) {
        panel.addMouseWheelListener(e -> {
            JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(
                JScrollPane.class, panel);
            if (scrollPane != null) {
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                int units = e.getUnitsToScroll() * constants.SCROLL_SPEED;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + units);
            }
        });
    }

    /**
     * Establece el tamaño preferido del panel.
     * @return Dimensiones preferidas del panel
     */
    @Override
    public Dimension getPreferredSize() {
        int width = constants.COLUMN_COUNT * (constants.CARD_WIDTH + constants.H_GAP);
        int rowCount = (int) Math.ceil(contentPanel.getComponentCount() / (double) constants.COLUMN_COUNT);
        int height = rowCount * constants.ROW_HEIGHT;
        return new Dimension(width, Math.max(height, constants.MIN_HEIGHT));
    }

    /**
     * Obtiene el panel de contenido interno.
     * @return JPanel que contiene los elementos de la cuadrícula
     */
    public JPanel getContentPanel() {
        return contentPanel;
    }
}