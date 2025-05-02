package com.store.view.components.shared;

import com.store.utils.Fonts;
import com.store.utils.Colors;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Clase utilitaria para crear componentes de entrada de formulario estandarizados.
 * <p>
 * Proporciona métodos estáticos para crear y configurar componentes de UI
 * con un estilo consistente en toda la aplicación.
 */
public class FormInputComponents {
    
    /**
     * Crea un área de texto configurada con el estilo de la aplicación.
     *
     * @param text Texto inicial del área
     * @param rows Número de filas visibles
     * @param columns Número de columnas visibles
     * @return JTextArea configurado
     */
    public static JTextArea createTextArea(String text, int rows, int columns) {
        JTextArea area = new JTextArea(text, rows, columns);
        area.setFont(Fonts.BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    /**
     * Crea un spinner para valores decimales con formato específico.
     *
     * @param value Valor inicial
     * @param step Incremento/decremento por paso
     * @param min Valor mínimo permitido
     * @param max Valor máximo permitido
     * @return JSpinner configurado para decimales
     */
    public static JSpinner createDecimalSpinner(double value, double step, double min, double max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0.00");
        spinner.setEditor(editor);
        return spinner;
    }

    /**
     * Crea un spinner para valores enteros con formato específico.
     *
     * @param value Valor inicial
     * @param step Incremento/decremento por paso
     * @param min Valor mínimo permitido
     * @param max Valor máximo permitido
     * @return JSpinner configurado para enteros
     */
    public static JSpinner createIntegerSpinner(int value, int step, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
        spinner.setFont(Fonts.BODY);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
        return spinner;
    }

    /**
     * Crea un campo de contraseña con estilo estandarizado.
     *
     * @return JPasswordField configurado
     */
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

    /**
     * Crea un panel combinado para entrada de documentos (tipo + número).
     *
     * @param selectedType Tipo de documento seleccionado inicialmente
     * @param number Número de documento inicial
     * @return JPanel con los componentes combinados
     */
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