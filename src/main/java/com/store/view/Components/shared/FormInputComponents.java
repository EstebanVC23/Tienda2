package com.store.view.components.shared;

import com.store.utils.Fonts;
import com.store.utils.Colors;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FormInputComponents {
    
    public static JTextArea createTextArea(String text, int rows, int columns) {
        JTextArea area = new JTextArea(text, rows, columns);
        area.setFont(Fonts.BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static JSpinner createDecimalSpinner(double value, double step, double min, double max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0.00");
        spinner.setEditor(editor);
        return spinner;
    }

    public static JSpinner createIntegerSpinner(int value, int step, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
        return spinner;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(Fonts.BODY);
        Border border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );
        field.setBorder(border);
        return field;
    }

    public static JPanel createDocumentInputPanel(String selectedType, String number) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(Color.WHITE);
        
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"DNI", "Pasaporte", "Cédula"});
        if (selectedType != null) typeCombo.setSelectedItem(selectedType);
        
        JTextField numberField = new JTextField(number);
        numberField.setFont(Fonts.BODY);
        
        panel.add(typeCombo, BorderLayout.WEST);
        panel.add(numberField, BorderLayout.CENTER);
        return panel;
    }
}