package com.store.view.panels.profile;

import javax.swing.*;
import java.awt.*;

public class ProfileFieldFactory {
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UserProfileConstants.LABEL_FONT);
        label.setForeground(UserProfileConstants.LABEL_COLOR);
        return label;
    }

    public static JTextField createEditableField(String value) {
        JTextField field = new JTextField(value != null ? value : "", 20);
        field.setFont(UserProfileConstants.VALUE_FONT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UserProfileConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING)
        ));
        return field;
    }

    public static JTextArea createEditableTextArea(String value) {
        JTextArea textArea = new JTextArea(value != null ? value : "", 3, 20);
        textArea.setFont(UserProfileConstants.VALUE_FONT);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UserProfileConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING, 
                UserProfileConstants.FIELD_PADDING)
        ));
        return textArea;
    }

    public static JLabel createReadOnlyField(String value) {
        JLabel label = new JLabel(value != null ? value : "-");
        label.setFont(UserProfileConstants.VALUE_FONT);
        label.setForeground(UserProfileConstants.VALUE_COLOR);
        return label;
    }
}