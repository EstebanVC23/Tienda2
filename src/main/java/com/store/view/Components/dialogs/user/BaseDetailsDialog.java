package com.store.view.components.dialogs.user;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public abstract class BaseDetailsDialog extends JDialog {
    protected static final int DEFAULT_PADDING = 15;
    
    public BaseDetailsDialog(Window parent, String title, int width, int height) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        initializeDialog(width, height);
    }

    private void initializeDialog(int width, int height) {
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(getOwner());
        getRootPane().setBorder(new EmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
    }

    protected JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, Colors.BORDER),
            new EmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING)
        ));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    protected JPanel createButtonPanel(String primaryActionText, String secondaryActionText, 
                                     Runnable primaryAction, Runnable secondaryAction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(DEFAULT_PADDING, 0, 0, 0));
        
        JButton secondaryButton = createButton(secondaryActionText, false);
        secondaryButton.addActionListener(_ -> secondaryAction.run());
        
        JButton primaryButton = createButton(primaryActionText, true);
        primaryButton.addActionListener(_ -> primaryAction.run());
        
        panel.add(secondaryButton);
        panel.add(primaryButton);
        return panel;
    }

    protected JButton createButton(String text, boolean primary) {
        JButton button = new JButton(text);
        button.setFont(Fonts.BUTTON);
        button.setFocusPainted(false);
        
        if (primary) {
            button.setBackground(Colors.PRIMARY_BLUE);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.DARK_BLUE, 1),
                new EmptyBorder(8, 16, 8, 16)
            ));
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Colors.PRIMARY_TEXT);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER, 1),
                new EmptyBorder(8, 16, 8, 16)
            ));
        }
        
        return button;
    }

    protected abstract void setupLayout();
}