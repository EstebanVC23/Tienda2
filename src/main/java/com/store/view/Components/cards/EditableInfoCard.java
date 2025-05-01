package com.store.view.components.cards;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.cards.constants.EditableCardConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class EditableInfoCard extends BaseCard {
    private final JLabel valueLabel;
    private final Consumer<String> onUpdate;
    private final EditableCardConstants constants;

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

    public EditableInfoCard(String label, String value, Consumer<String> onUpdate) {
        this(label, value, onUpdate, new EditableCardConstants());
    }

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