package com.store.view.components.cards;

import com.store.utils.Colors;
import com.store.utils.Fonts;

import javax.swing.*;
import java.awt.*;

/**
 * Componente de tarjeta para mostrar información de usuario.
 * Muestra un par de valores etiquetados (label-value) en formato vertical.
 */
public class UserInfoCard extends BaseCard {
    
    /**
     * Crea una nueva tarjeta de información de usuario.
     * @param label Texto descriptivo o etiqueta del dato.
     * @param value Valor de la información a mostrar.
     */
    public UserInfoCard(String label, String value) {
        super();
        
        JLabel labelText = createLabel(label, Fonts.SMALL, Colors.SECONDARY_TEXT);
        JLabel valueText = createLabel(value, Fonts.BODY, Colors.PRIMARY_TEXT);
        
        add(labelText, BorderLayout.NORTH);
        add(valueText, BorderLayout.CENTER);
        
        setCursor(Cursor.getDefaultCursor());
    }
}