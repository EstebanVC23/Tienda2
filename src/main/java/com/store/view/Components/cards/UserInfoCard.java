package com.store.view.components.cards;

import com.store.utils.Colors;
import com.store.utils.Fonts;

import javax.swing.*;
import java.awt.*;

public class UserInfoCard extends BaseCard {
    public UserInfoCard(String label, String value) {
        super();
        
        JLabel labelText = createLabel(label, Fonts.SMALL, Colors.SECONDARY_TEXT);
        JLabel valueText = createLabel(value, Fonts.BODY, Colors.PRIMARY_TEXT);
        
        add(labelText, BorderLayout.NORTH);
        add(valueText, BorderLayout.CENTER);
        
        // Eliminar el efecto hover si no es necesario
        setCursor(Cursor.getDefaultCursor());
    }
}