package com.store.view.components.dialogs;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormComponents {
    public static class FormPanel extends JPanel {
        public FormPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(20, 25, 20, 25));
            setBackground(Colors.PANEL_BACKGROUND);
        }
        
        public void addField(String label, JComponent field) {
            add(new FormLabel(label));
            add(field);
            add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        public void addField(JComponent field) {
            add(field);
            add(Box.createRigidArea(new Dimension(0, 15)));
        }
    }
    
    public static class FormLabel extends JLabel {
        public FormLabel(String text) {
            super(text);
            setFont(Fonts.BOLD_BODY);
            setForeground(Colors.PRIMARY_TEXT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setBorder(new EmptyBorder(5, 0, 5, 0));
        }
    }
    
    public static class FormTextField extends JTextField {
        public FormTextField() {
            this("");
        }
        
        public FormTextField(String text) {
            super(text);
            setFont(Fonts.BODY);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            setBackground(Color.WHITE);
        }
    }
    
    public static class ReadOnlyTextField extends FormTextField {
        public ReadOnlyTextField(String text) {
            super(text);
            setEditable(false);
        }
    }
    
    public static class FormTextArea extends JTextArea {
        public FormTextArea() {
            this("");
        }
        
        public FormTextArea(String text) {
            super(text, 3, 20);
            setFont(Fonts.BODY);
            setLineWrap(true);
            setWrapStyleWord(true);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            setBackground(Color.WHITE);
        }
    }
    
    public static class FormComboBox<T> extends JComboBox<T> {
        public FormComboBox() {
            setFont(Fonts.BODY);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                             int index, boolean isSelected, 
                                                             boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setBorder(new EmptyBorder(3, 5, 3, 5));
                    return this;
                }
            });
        }
    }
    
    public static class FormSpinner extends JSpinner {
        public FormSpinner() {
            this(0, 1, 0, 9999);
        }
        
        public FormSpinner(double value, double step, double min, double max) {
            super(new SpinnerNumberModel(value, min, max, step));
            setFont(Fonts.BODY);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            ((DefaultEditor) getEditor()).getTextField().setEditable(true);
        }
    }
    
    public static class FormPasswordField extends JPasswordField {
        public FormPasswordField() {
            setFont(Fonts.BODY);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            setBackground(Color.WHITE);
        }
    }
    
    public static class ErrorLabel extends JLabel {
        public ErrorLabel() {
            super(" ");
            setForeground(Colors.ERROR_RED);
            setFont(Fonts.SMALL);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }
}