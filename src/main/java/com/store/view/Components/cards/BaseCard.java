package com.store.view.components.cards;

import com.store.utils.Colors;
import com.store.view.components.cards.constants.BaseCardConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clase base abstracta para tarjetas de la interfaz gráfica.
 * Proporciona funcionalidad común y estilos base para todas las tarjetas.
 */
public abstract class BaseCard extends JPanel {
    protected final BaseCardConstants constants;

    /**
     * Constructor que inicializa la tarjeta con constantes personalizadas.
     * @param constants Configuración de constantes para la tarjeta
     */
    public BaseCard(BaseCardConstants constants) {
        this.constants = constants;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setCommonBorder();
    }

    /**
     * Constructor por defecto que usa constantes predeterminadas.
     */
    public BaseCard() {
        this(new BaseCardConstants());
    }

    /**
     * Establece el borde común para todas las tarjetas.
     */
    protected void setCommonBorder() {
        setBorder(createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(constants.PADDING, constants.PADDING, 
                          constants.PADDING, constants.PADDING)
        ));
    }

    /**
     * Aplica efecto visual al pasar el mouse sobre la tarjeta.
     * @param hover true para activar el efecto hover, false para desactivarlo
     */
    protected void setHoverEffect(boolean hover) {
        setBorder(createCompoundBorder(
            BorderFactory.createLineBorder(hover ? Colors.PRIMARY_BLUE : Colors.BORDER, 1),
            new EmptyBorder(constants.PADDING, constants.PADDING, 
                          constants.PADDING, constants.PADDING)
        ));
    }

    /**
     * Crea un borde compuesto.
     * @param outside Borde exterior
     * @param inside Borde interior
     * @return CompoundBorder configurado
     */
    private CompoundBorder createCompoundBorder(Border outside, Border inside) {
        return new CompoundBorder(outside, inside);
    }

    /**
     * Crea una etiqueta con estilo consistente.
     * @param text Texto a mostrar
     * @param font Fuente a utilizar
     * @param color Color del texto
     * @return JLabel configurado
     */
    protected JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Crea un panel con disposición vertical.
     * @return JPanel configurado con BoxLayout vertical
     */
    protected JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}