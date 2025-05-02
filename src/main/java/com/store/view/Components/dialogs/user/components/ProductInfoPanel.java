package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel que muestra información detallada de un producto en un formato de cuadrícula.
 * Muestra categoría, stock, código y proveedor del producto con sus respectivas etiquetas.
 */
public class ProductInfoPanel extends JPanel {
    /**
     * Crea un panel de información de producto con formato de cuadrícula.
     * 
     * @param category Categoría del producto
     * @param stock Cantidad disponible en stock
     * @param code Código identificador del producto
     * @param supplier Proveedor del producto
     * @param hgap Espaciado horizontal entre elementos
     * @param vgap Espaciado vertical entre elementos
     * @param bottomSpacing Espaciado inferior del panel
     */
    public ProductInfoPanel(String category, int stock, String code, String supplier,
                          int hgap, int vgap, int bottomSpacing) {
        super(new GridLayout(0, 2, hgap, vgap));
        setBackground(Color.WHITE);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setBorder(new EmptyBorder(0, 0, bottomSpacing, 0));
        
        addInfoItem("Categoría", category);
        addInfoItem("Stock disponible", String.valueOf(stock));
        addInfoItem("Código", code != null ? code : "N/A");
        addInfoItem("Proveedor", supplier != null ? supplier : "N/A");
    }

    /**
     * Agrega un par de elementos (etiqueta y valor) al panel de información.
     * 
     * @param label Texto descriptivo del campo
     * @param value Valor correspondiente al campo
     */
    private void addInfoItem(String label, String value) {
        add(createInfoLabel(label + ":"));
        add(createInfoValue(value));
    }

    /**
     * Crea una etiqueta descriptiva para un campo de información.
     * 
     * @param text Texto de la etiqueta
     * @return JLabel configurado con el estilo apropiado
     */
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BOLD_BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }

    /**
     * Crea una etiqueta con el valor de un campo de información.
     * 
     * @param text Texto del valor
     * @return JLabel configurado con el estilo apropiado
     */
    private JLabel createInfoValue(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.PRIMARY_TEXT);
        return label;
    }
}