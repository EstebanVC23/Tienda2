package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;

public class ProductActionPanel extends JPanel {
    private final JButton addToCartButton;
    private final JButton removeFromCartButton;
    private final JButton closeButton;

    public ProductActionPanel(Runnable addToCartAction, 
                           Runnable removeFromCartAction,
                           Runnable closeAction) {
        super(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 0, 0, 0));
        
        this.closeButton = createCloseButton(closeAction, "Cerrar");
        this.addToCartButton = createAddToCartButton(addToCartAction, "Agregar al carrito");
        this.removeFromCartButton = createRemoveFromCartButton(removeFromCartAction, "Quitar del carrito");
        
        add(closeButton);
        add(removeFromCartButton);
        add(addToCartButton);
    }

    private JButton createCloseButton(Runnable action, String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Color.WHITE);
        button.setForeground(Colors.PRIMARY_TEXT);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(8, 16, 8, 16)
        ));
        button.addActionListener(_ -> action.run());
        return button;
    }

    private JButton createAddToCartButton(Runnable action, String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Colors.PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
            new EmptyBorder(8, 16, 8, 16)
        ));
        button.addActionListener(_ -> action.run());
        return button;
    }
    
    private JButton createRemoveFromCartButton(Runnable action, String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setBackground(Colors.ACCENT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Colors.ACCENT.darker(), 1), // Usamos darker() en lugar de DARK_ACCENT
            new EmptyBorder(8, 16, 8, 16)
        ));
        button.addActionListener(_ -> action.run());
        return button;
    }

    public JButton getAddToCartButton() {
        return addToCartButton;
    }
    
    public JButton getRemoveFromCartButton() {
        return removeFromCartButton;
    }
}