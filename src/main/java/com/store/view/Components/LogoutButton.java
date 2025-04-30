package com.store.view.Components;

import javax.swing.*;

import com.store.utils.Colors;

import java.awt.*;
import java.awt.event.*;

public class LogoutButton extends JButton {
    public LogoutButton(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(Color.BLUE);
        setBackground(Colors.LOGOUT_RED);
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new LogoutButtonHoverEffect(this));
    }
}

class LogoutButtonHoverEffect extends MouseAdapter {
    private final LogoutButton button;
    
    public LogoutButtonHoverEffect(LogoutButton button) {
        this.button = button;
    }
    
    @Override
    public void mouseEntered(MouseEvent evt) {
        button.setBackground(Colors.LOGOUT_RED_DARK);
    }
    
    @Override
    public void mouseExited(MouseEvent evt) {
        button.setBackground(Colors.LOGOUT_RED);
    }
    
    @Override
    public void mousePressed(MouseEvent evt) {
        button.setBackground(new Color(148, 20, 20));
    }
}