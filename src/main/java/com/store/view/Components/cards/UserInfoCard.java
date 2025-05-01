package com.store.view.components.cards;

import javax.swing.*;
import java.awt.*;

public class UserInfoCard extends JPanel {
    public UserInfoCard(String label, String value) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelText.setForeground(new Color(100, 100, 100));
        
        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueText.setForeground(new Color(60, 60, 60));
        
        add(labelText, BorderLayout.NORTH);
        add(valueText, BorderLayout.CENTER);
    }
}