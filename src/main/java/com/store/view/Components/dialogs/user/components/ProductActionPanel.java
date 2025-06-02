package com.store.view.components.dialogs.user.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProductActionPanel extends JPanel {
    private final Runnable addToCartAction;
    private final Runnable removeFromCartAction;
    private final Runnable closeAction;
    
    private JButton addToCartButton;
    private JButton removeFromCartButton;
    private JButton closeButton;

    public ProductActionPanel(Runnable addToCartAction, 
                            Runnable removeFromCartAction, 
                            Runnable closeAction) {
        this.addToCartAction = addToCartAction;
        this.removeFromCartAction = removeFromCartAction;
        this.closeAction = closeAction;
        
        initializePanel();
        createButtons();
        setupLayout();
    }

    private void initializePanel() {
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    }

    private void createButtons() {
        // Crear botón de cerrar (siempre presente)
        closeButton = createButton("Cerrar", Colors.SECONDARY_TEXT, Color.WHITE, closeAction);
        closeButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(8, 16, 8, 16)
        ));

        // Crear botones del carrito solo si las acciones están disponibles
        if (addToCartAction != null) {
            addToCartButton = createButton("Añadir al carrito", Color.WHITE, Colors.PRIMARY_BLUE, addToCartAction);
            addToCartButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
                new EmptyBorder(8, 16, 8, 16)
            ));
        }

        if (removeFromCartAction != null) {
            removeFromCartButton = createButton("Quitar del carrito", Color.WHITE, Colors.ACCENT, removeFromCartAction);
            removeFromCartButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.ACCENT.darker(), 1),
                new EmptyBorder(8, 16, 8, 16)
            ));
            removeFromCartButton.setVisible(false); // Inicialmente oculto
        }
    }

    private JButton createButton(String text, Color textColor, Color backgroundColor, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setForeground(textColor);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        if (action != null) {
            button.addActionListener(_ -> {
                try {
                    action.run();
                } catch (Exception ex) {
                    System.err.println("Error ejecutando acción del botón: " + ex.getMessage());
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Error inesperado. Por favor, intenta de nuevo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            });
        } else {
            button.setEnabled(false);
        }
        
        return button;
    }

    private void setupLayout() {
        // Agregar botones en orden: cerrar, quitar del carrito, añadir al carrito
        add(closeButton);
        
        if (removeFromCartButton != null) {
            add(removeFromCartButton);
        }
        
        if (addToCartButton != null) {
            add(addToCartButton);
        }
    }

    // Métodos para controlar la visibilidad de los botones
    public void showAddToCartButton(boolean show) {
        if (addToCartButton != null) {
            addToCartButton.setVisible(show);
        }
    }

    public void showRemoveFromCartButton(boolean show) {
        if (removeFromCartButton != null) {
            removeFromCartButton.setVisible(show);
        }
    }

    public void setCartButtonsVisible(boolean addVisible, boolean removeVisible) {
        showAddToCartButton(addVisible);
        showRemoveFromCartButton(removeVisible);
    }

    // Métodos para habilitar/deshabilitar botones
    public void setAddToCartEnabled(boolean enabled) {
        if (addToCartButton != null) {
            addToCartButton.setEnabled(enabled);
        }
    }

    public void setRemoveFromCartEnabled(boolean enabled) {
        if (removeFromCartButton != null) {
            removeFromCartButton.setEnabled(enabled);
        }
    }

    public void setCartButtonsEnabled(boolean enabled) {
        setAddToCartEnabled(enabled);
        setRemoveFromCartEnabled(enabled);
    }

    // Getters para los botones (por si se necesitan desde fuera)
    public JButton getAddToCartButton() {
        return addToCartButton;
    }

    public JButton getRemoveFromCartButton() {
        return removeFromCartButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    // Método para verificar si tiene funcionalidad de carrito
    public boolean hasCartFunctionality() {
        return addToCartAction != null || removeFromCartAction != null;
    }

    // Método de debug
    public void printDebugInfo() {
        System.out.println("=== ProductActionPanel Debug Info ===");
        System.out.println("Add to cart action: " + (addToCartAction != null ? "Available" : "NULL"));
        System.out.println("Remove from cart action: " + (removeFromCartAction != null ? "Available" : "NULL"));
        System.out.println("Close action: " + (closeAction != null ? "Available" : "NULL"));
        System.out.println("Add button created: " + (addToCartButton != null));
        System.out.println("Remove button created: " + (removeFromCartButton != null));
        System.out.println("Close button created: " + (closeButton != null));
        System.out.println("======================================");
    }
}