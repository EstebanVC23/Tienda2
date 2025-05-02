package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel que muestra la descripción de un producto con título y texto.
 */
public class ProductDescriptionPanel extends JPanel {
    /**
     * Crea un panel de descripción de producto con título y texto.
     * 
     * @param description Texto de la descripción del producto
     * @param titleText Texto del título
     * @param titleSpacing Espaciado entre el título y la descripción
     * @param topSpacing Espaciado superior de la descripción
     */
    public ProductDescriptionPanel(String description, String titleText, 
                                 int titleSpacing, int topSpacing) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(createTitleLabel(titleText));
        add(Box.createRigidArea(new Dimension(0, titleSpacing)));
        add(createDescriptionText(description, topSpacing));
    }

    /**
     * Crea una etiqueta de título con estilo personalizado.
     * 
     * @param text Texto del título
     * @return Etiqueta de título configurada
     */
    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.SECTION_TITLE);
        label.setForeground(Colors.PRIMARY_TEXT);
        return label;
    }

    /**
     * Crea un área de texto para la descripción con estilo personalizado.
     * 
     * @param text Texto de la descripción
     * @param topSpacing Espaciado superior
     * @return Área de texto configurada
     */
    private JTextArea createDescriptionText(String text, int topSpacing) {
        JTextArea description = new JTextArea(text);
        description.setFont(Fonts.BODY);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(Color.WHITE);
        description.setBorder(new EmptyBorder(topSpacing, 0, 0, 0));
        return description;
    }
}