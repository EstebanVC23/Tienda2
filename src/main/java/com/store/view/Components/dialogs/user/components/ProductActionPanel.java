package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel que contiene los botones de acción para productos.
 */
public class ProductActionPanel extends JPanel {
    /**
     * Crea un panel de acciones para productos con botones personalizados.
     * 
     * @param addToCartAction Acción a ejecutar al agregar al carrito
     * @param closeAction Acción a ejecutar al cerrar
     * @param addToCartText Texto para el botón de agregar al carrito
     * @param closeText Texto para el botón de cerrar
     */
    public ProductActionPanel(Runnable addToCartAction, 
                           Runnable closeAction,
                           String addToCartText,
                           String closeText) {
        super(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 0, 0, 0));
        
        add(createCloseButton(closeAction, closeText));
        add(createAddToCartButton(addToCartAction, addToCartText));
    }

    /**
     * Crea un botón de cierre con estilo personalizado.
     * 
     * @param action Acción a ejecutar al hacer clic
     * @param text Texto del botón
     * @return Botón configurado
     */
    private JButton createCloseButton(Runnable action, String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Color.WHITE);
        button.setForeground(Colors.PRIMARY_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(8, 16, 8, 16)
        ));
        button.addActionListener(_ -> action.run());
        return button;
    }

    /**
     * Crea un botón de agregar al carrito con estilo personalizado.
     * 
     * @param action Acción a ejecutar al hacer clic
     * @param text Texto del botón
     * @return Botón configurado
     */
    private JButton createAddToCartButton(Runnable action, String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Colors.PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
            new EmptyBorder(8, 16, 8, 16)
        ));
        button.addActionListener(_ -> action.run());
        return button;
    }
}