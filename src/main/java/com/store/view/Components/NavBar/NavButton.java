package com.store.view.Components.NavBar;

import javax.swing.*;

import com.store.utils.Colors;

import java.awt.*;
import java.awt.event.*;

public class NavButton extends JButton {
    public NavButton(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Colors.INACTIVE_TEXT);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
        
        addMouseListener(new NavButtonHoverEffect(this));
    }
    
    public void setActive(boolean active) {
        if (active) {
            setForeground(Colors.PRIMARY_BLUE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
        } else {
            setForeground(Colors.INACTIVE_TEXT);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
}

class NavButtonHoverEffect extends MouseAdapter {
    private final NavButton button;
    
    public NavButtonHoverEffect(NavButton button) {
        this.button = button;
    }
    
    @Override
    public void mouseEntered(MouseEvent evt) {
        button.setForeground(Colors.DARK_BLUE);
    }
    
    @Override
    public void mouseExited(MouseEvent evt) {
        // Solo cambia si no está activo
        if (!button.getForeground().equals(Colors.PRIMARY_BLUE)) {
            button.setForeground(Colors.INACTIVE_TEXT);
        }
    }
}