package com.store.view.components.shared;

import com.store.utils.Fonts;
import com.store.utils.Colors;
import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class RadioButtonGroup {
    private final ButtonGroup buttonGroup;
    private final JPanel container;

    public RadioButtonGroup(String... options) {
        this.buttonGroup = new ButtonGroup();
        this.container = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        container.setBackground(Colors.PANEL_BACKGROUND);
        
        for (String option : options) {
            addOption(option);
        }
    }

    private void addOption(String text) {
        JRadioButton radio = new JRadioButton(text);
        radio.setFont(Fonts.BODY);
        radio.setBackground(Colors.PANEL_BACKGROUND);
        radio.setFocusPainted(false);
        buttonGroup.add(radio);
        container.add(radio);
        if (buttonGroup.getButtonCount() == 1) radio.setSelected(true);
    }

    public JPanel getContainer() {
        return container;
    }

    public String getSelectedText() {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) return button.getText();
        }
        return null;
    }

    public boolean isSelected(String text) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.getText().equals(text)) return button.isSelected();
        }
        return false;
    }
}