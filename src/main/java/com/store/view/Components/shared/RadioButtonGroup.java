package com.store.view.components.shared;

import com.store.utils.Fonts;
import com.store.utils.Colors;
import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * Componente agrupado de botones de radio personalizado.
 * <p>
 * Proporciona una implementación reutilizable de un grupo de botones de radio
 * con estilo consistente y métodos útiles para manejar la selección.
 */
public class RadioButtonGroup {
    private final ButtonGroup buttonGroup;
    private final JPanel container;

    /**
     * Crea un nuevo grupo de botones de radio con las opciones especificadas.
     * 
     * @param options Opciones que se mostrarán como botones de radio
     */
    public RadioButtonGroup(String... options) {
        this.buttonGroup = new ButtonGroup();
        this.container = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        container.setBackground(Colors.PANEL_BACKGROUND);
        
        for (String option : options) {
            addOption(option);
        }
    }

    /**
     * Añade una opción al grupo de botones de radio.
     * 
     * @param text Texto que se mostrará junto al botón de radio
     */
    private void addOption(String text) {
        JRadioButton radio = new JRadioButton(text);
        radio.setFont(Fonts.BODY);
        radio.setBackground(Colors.PANEL_BACKGROUND);
        radio.setFocusPainted(false);
        buttonGroup.add(radio);
        container.add(radio);
        // Selecciona el primer botón por defecto
        if (buttonGroup.getButtonCount() == 1) radio.setSelected(true);
    }

    /**
     * Obtiene el panel contenedor con los botones de radio.
     * 
     * @return JPanel que contiene los botones de radio
     */
    public JPanel getContainer() {
        return container;
    }

    /**
     * Obtiene el texto de la opción seleccionada.
     * 
     * @return Texto de la opción seleccionada, o null si no hay selección
     */
    public String getSelectedText() {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) return button.getText();
        }
        return null;
    }

    /**
     * Verifica si una opción específica está seleccionada.
     * 
     * @param text Texto de la opción a verificar
     * @return true si la opción está seleccionada, false en caso contrario
     */
    public boolean isSelected(String text) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.getText().equals(text)) return button.isSelected();
        }
        return false;
    }
}