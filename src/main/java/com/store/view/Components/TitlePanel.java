package com.store.view.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public TitlePanel(String title) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Fonts.TITLE);
        titleLabel.setForeground(Colors.PRIMARY_TEXT);
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        
        // Opcional: Añadir un ícono junto al título
        JPanel titleContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titleContainer.setBackground(Color.WHITE);
        titleContainer.add(titleLabel);
        
        add(titleContainer, BorderLayout.WEST);
    }
}