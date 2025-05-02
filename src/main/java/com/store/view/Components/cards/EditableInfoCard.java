package com.store.view.components.cards;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.cards.constants.EditableCardConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Tarjeta editable que permite modificar su valor mediante interacción del usuario.
 * Extiende de BaseCard para mantener consistencia de estilos.
 */
public class EditableInfoCard extends BaseCard {
    private final JLabel valueLabel;
    private final Consumer<String> onUpdate;
    private final EditableCardConstants constants;

    /**
     * Constructor principal que inicializa la tarjeta editable.
     * @param label Texto descriptivo del campo
     * @param value Valor inicial a mostrar
     * @param onUpdate Callback que se ejecuta al actualizar el valor
     * @param constants Configuración de constantes para la tarjeta
     */
    public EditableInfoCard(String label, String value, Consumer<String> onUpdate, 
                          EditableCardConstants constants) {
        super(constants);
        this.constants = constants;
        this.onUpdate = onUpdate;
        
        JLabel labelText = createLabel(label, Fonts.SMALL, Colors.SECONDARY_TEXT);
        valueLabel = createLabel(value, Fonts.BODY, Colors.PRIMARY_TEXT);
        
        add(labelText, BorderLayout.NORTH);
        add(valueLabel, BorderLayout.CENTER);
        
        setupMouseListeners();
    }

    /**
     * Constructor alternativo que usa constantes predeterminadas.
     * @param label Texto descriptivo del campo
     * @param value Valor inicial a mostrar
     * @param onUpdate Callback que se ejecuta al actualizar el valor
     */
    public EditableInfoCard(String label, String value, Consumer<String> onUpdate) {
        this(label, value, onUpdate, new EditableCardConstants());
    }

    /**
     * Configura los listeners de mouse para interacción.
     */
    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == constants.CLICKS_TO_EDIT) {
                    editValue();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setHoverEffect(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setHoverEffect(false);
            }
        });
    }

    /**
     * Maneja la edición del valor mostrado en la tarjeta.
     * Muestra un diálogo de entrada y actualiza el valor si es válido.
     */
    private void editValue() {
        String newValue = JOptionPane.showInputDialog(
            this, 
            constants.EDIT_PROMPT_PREFIX + ((JLabel)getComponent(0)).getText() + ":", 
            valueLabel.getText()
        );
        
        if (newValue != null && !newValue.trim().isEmpty()) {
            valueLabel.setText(newValue);
            onUpdate.accept(newValue);
        }
    }
}