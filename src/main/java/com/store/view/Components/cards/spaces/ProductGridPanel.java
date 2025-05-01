package com.store.view.components.cards.spaces;

import com.store.models.Producto;
import com.store.utils.Colors;
import com.store.view.components.cards.ProductCard;
import com.store.view.components.cards.constants.GridConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductGridPanel extends JPanel {
    private final JPanel contentPanel;
    private final GridConstants constants;

    public ProductGridPanel(GridConstants constants) {
        this.constants = constants;
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND);
        
        contentPanel = new JPanel(new GridLayout(0, constants.COLUMN_COUNT, 
            constants.H_GAP, constants.V_GAP));
        contentPanel.setBackground(Colors.BACKGROUND);
        
        enableFastMouseWheelScrolling(contentPanel);
    }

    public ProductGridPanel() {
        this(new GridConstants());
    }

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

    private void showEmptyState() {
        contentPanel.setLayout(new BorderLayout());
        JLabel emptyLabel = new JLabel(constants.EMPTY_TEXT, SwingConstants.CENTER);
        emptyLabel.setFont(constants.EMPTY_FONT);
        emptyLabel.setForeground(Colors.SECONDARY_TEXT);
        contentPanel.add(emptyLabel, BorderLayout.CENTER);
    }

    private void showProductsGrid(List<Producto> productos) {
        contentPanel.setLayout(new GridLayout(0, constants.COLUMN_COUNT, 
            constants.H_GAP, constants.V_GAP));
        productos.forEach(p -> contentPanel.add(new ProductCard(p)));
    }

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

    @Override
    public Dimension getPreferredSize() {
        int width = constants.COLUMN_COUNT * (constants.CARD_WIDTH + constants.H_GAP);
        int rowCount = (int) Math.ceil(contentPanel.getComponentCount() / (double) constants.COLUMN_COUNT);
        int height = rowCount * constants.ROW_HEIGHT;
        return new Dimension(width, Math.max(height, constants.MIN_HEIGHT));
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}