package com.store.view.AdminView;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Clase utilitaria que define los estilos visuales para la interfaz de administración.
 * Utiliza las constantes de colores y fuentes definidas en las clases utilitarias.
 * Proporciona métodos para crear componentes estilizados consistentes.
 */
public class AdminViewStyles {
    
    /**
     * Crea un JLabel estilizado para títulos de sección.
     * @param text Texto a mostrar en el título
     * @return JLabel configurado con el estilo de título de sección
     */
    public static JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.TITLE);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(new EmptyBorder(20, 0, 20, 0));
        return label;
    }

    /**
     * Configura un botón con los estilos predeterminados de la aplicación.
     * @param button Botón a estilizar
     * @return El mismo botón con los estilos aplicados
     */
    public static JButton styleButton(JButton button) {
        button.setFont(Fonts.BUTTON);
        button.setBackground(Colors.PRIMARY);
        button.setForeground(Colors.ACTIVE_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        return button;
    }

    /**
     * Configura un panel con los estilos básicos de la aplicación.
     * @param panel Panel a estilizar
     * @return El mismo panel con los estilos aplicados
     */
    public static JPanel stylePanel(JPanel panel) {
        panel.setBackground(Colors.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }
}