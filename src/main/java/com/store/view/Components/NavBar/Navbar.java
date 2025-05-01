package com.store.view.components.NavBar;

import javax.swing.*;

import com.store.utils.Colors;

import java.awt.*;
import java.awt.event.*;

public class Navbar extends JPanel {
    private JButton activeButton;
    
    public Navbar(ActionListener navActionListener, String[] options) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(207, 216, 220)),
            BorderFactory.createEmptyBorder(5, 20, 5, 20)
        ));
        
        JPanel navbarContent = new JPanel();
        navbarContent.setLayout(new BorderLayout());
        navbarContent.setBackground(Color.WHITE);
        
        // Panel del logo
        JPanel logoPanel = createLogoPanel();
        
        // Panel de botones de navegación (ahora centrado)
        JPanel buttonsPanel = createButtonsPanel(navActionListener, options);
        
        navbarContent.add(logoPanel, BorderLayout.WEST);
        navbarContent.add(buttonsPanel, BorderLayout.CENTER);
        
        add(navbarContent, BorderLayout.CENTER);
    }
    
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        logoPanel.setBackground(Color.WHITE);
        
        JLabel logoIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colors.PRIMARY_BLUE);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String text = "MC";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(text, x, y);
                g2.dispose();
            }
        };
        logoIcon.setPreferredSize(new Dimension(32, 32));
        
        JLabel logoLabel = new JLabel("MotoCoreBD");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoLabel.setForeground(Colors.DARK_BLUE);
        
        logoPanel.add(logoIcon);
        logoPanel.add(logoLabel);
        
        return logoPanel;
    }
    
    private JPanel createButtonsPanel(ActionListener listener, String[] buttonLabels) {
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setBackground(Color.WHITE);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonsPanel.setBackground(Color.WHITE);

        for (String label : buttonLabels) {
            NavButton button = new NavButton(label);
            button.addActionListener(listener);
            buttonsPanel.add(button);
            
            if (label.equals("Employees")) {
                setActiveButton(button);
            }
        }
        
        centeringPanel.add(buttonsPanel);
        return centeringPanel;
    }
    
    public void setActiveButton(JButton button) {
        if (activeButton != null && activeButton instanceof NavButton) {
            ((NavButton) activeButton).setActive(false);
        }
        
        activeButton = button;
        if (activeButton instanceof NavButton) {
            ((NavButton) activeButton).setActive(true);
        }
    }
}