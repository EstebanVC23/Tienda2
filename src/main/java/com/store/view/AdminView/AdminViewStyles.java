package com.store.view.AdminView;

import java.awt.*;
import javax.swing.*;

public class AdminViewStyles {
    public static final Color BACKGROUND_COLOR = new Color(240, 245, 249);
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    public static JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return label;
    }
}