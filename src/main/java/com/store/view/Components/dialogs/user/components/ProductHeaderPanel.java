package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;

/**
 * Panel que muestra el encabezado de un producto con nombre y precio.
 */
public class ProductHeaderPanel extends JPanel {
    /**
     * Crea un panel de encabezado para producto con nombre y precio.
     * 
     * @param name Nombre del producto
     * @param price Precio del producto
     * @param namePriceSpacing Espaciado entre el nombre y el precio
     * @param bottomSpacing Espaciado inferior del panel
     */
    public ProductHeaderPanel(String name, double price, int namePriceSpacing, int bottomSpacing) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(createNameLabel(name));
        add(Box.createRigidArea(new Dimension(0, namePriceSpacing)));
        add(createPriceLabel(price));
        add(Box.createRigidArea(new Dimension(0, bottomSpacing)));
    }

    /**
     * Crea una etiqueta para el nombre del producto con estilo personalizado.
     * 
     * @param name Nombre del producto
     * @return Etiqueta de nombre configurada
     */
    private JLabel createNameLabel(String name) {
        JLabel label = new JLabel(name);
        label.setFont(Fonts.TITLE);
        label.setForeground(Colors.PRIMARY_TEXT);
        return label;
    }

    /**
     * Crea una etiqueta para el precio del producto con estilo personalizado.
     * 
     * @param price Precio del producto
     * @return Etiqueta de precio configurada
     */
    private JLabel createPriceLabel(double price) {
        JLabel label = new JLabel(String.format("$%,.2f", price));
        label.setFont(Fonts.SUBTITLE);
        label.setForeground(Colors.ACCENT);
        return label;
    }
}