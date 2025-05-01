package com.store.view.components.cards;

import com.store.utils.Colors;
import com.store.view.components.cards.constants.BaseCardConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BaseCard extends JPanel {
    protected final BaseCardConstants constants;

    public BaseCard(BaseCardConstants constants) {
        this.constants = constants;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setCommonBorder();
    }

    public BaseCard() {
        this(new BaseCardConstants());
    }

    protected void setCommonBorder() {
        setBorder(createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            new EmptyBorder(constants.PADDING, constants.PADDING, 
                          constants.PADDING, constants.PADDING)
        ));
    }

    protected void setHoverEffect(boolean hover) {
        setBorder(createCompoundBorder(
            BorderFactory.createLineBorder(hover ? Colors.PRIMARY_BLUE : Colors.BORDER, 1),
            new EmptyBorder(constants.PADDING, constants.PADDING, 
                          constants.PADDING, constants.PADDING)
        ));
    }

    private CompoundBorder createCompoundBorder(Border outside, Border inside) {
        return new CompoundBorder(outside, inside);
    }

    protected JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    protected JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}