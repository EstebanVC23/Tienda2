package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel que muestra la imagen de un producto o un mensaje cuando no hay imagen disponible.
 */
public class ProductImagePanel extends JPanel {
    /**
     * Crea un panel para mostrar la imagen del producto.
     * 
     * @param size Dimensiones del panel
     * @param scaleOffset Reducción de tamaño para la imagen
     * @param padding Relleno interno del panel
     * @param bgColor Color de fondo del panel
     * @param noImageText Texto a mostrar cuando no hay imagen disponible
     */
    public ProductImagePanel(Dimension size, int scaleOffset, int padding, Color bgColor, String noImageText) {
        super(new BorderLayout());
        setPreferredSize(size);
        setBackground(bgColor);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(padding, padding, padding, padding)
        ));
        add(createImageContent(size.width - scaleOffset, size.height - scaleOffset, noImageText));
    }

    /**
     * Crea el contenido de imagen escalado o muestra texto alternativo.
     * 
     * @param width Ancho de la imagen
     * @param height Alto de la imagen
     * @param noImageText Texto alternativo cuando no hay imagen
     * @return Componente con la imagen o texto alternativo
     */
    private JComponent createImageContent(int width, int height, String noImageText) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Img/card.jpeg"));
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
        } catch (Exception e) {
            return createNoImageLabel(noImageText);
        }
    }

    /**
     * Crea una etiqueta para mostrar cuando no hay imagen disponible.
     * 
     * @param text Texto a mostrar
     * @return Etiqueta configurada
     */
    private JLabel createNoImageLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(Fonts.BODY);
        label.setForeground(Colors.SECONDARY_TEXT);
        return label;
    }
}