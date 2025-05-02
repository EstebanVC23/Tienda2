package com.store.view.components.dialogs;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clase utilitaria para estilizar componentes de formularios de manera consistente.
 * Proporciona métodos estáticos para crear y configurar componentes de UI con el estilo de la aplicación.
 */
public class FormStyler {
    /**
     * Crea y configura un panel base para formularios.
     * 
     * @return JPanel configurado con el layout y estilo adecuados para contener elementos de formulario
     */
    public static JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        panel.setBackground(Colors.PANEL_BACKGROUND);
        return panel;
    }

    /**
     * Crea y configura una etiqueta para formularios.
     * 
     * @param text Texto a mostrar en la etiqueta
     * @return JLabel configurado con el estilo de formulario
     */
    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BOLD_BODY);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        return label;
    }

    /**
     * Crea y configura un campo de texto para formularios.
     * 
     * @return JTextField configurado con el estilo de formulario
     */
    public static JTextField createFormTextField() {
        JTextField field = new JTextField();
        field.setFont(Fonts.BODY);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }

    /**
     * Crea y configura un área de texto para formularios.
     * 
     * @return JTextArea configurado con el estilo de formulario
     */
    public static JTextArea createFormTextArea() {
        JTextArea area = new JTextArea(3, 20);
        area.setFont(Fonts.BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        area.setBackground(Color.WHITE);
        return area;
    }

    /**
     * Crea y configura un combo box para formularios.
     * 
     * @return JComboBox configurado con el estilo de formulario
     */
    public static JComboBox<String> createFormComboBox() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(Fonts.BODY);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(3, 5, 3, 5));
                return this;
            }
        });
        return combo;
    }

    /**
     * Crea y configura un spinner para formularios.
     * 
     * @return JSpinner configurado con el estilo de formulario
     */
    public static JSpinner createFormSpinner() {
        JSpinner spinner = new JSpinner();
        spinner.setFont(Fonts.BODY);
        spinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        return spinner;
    }
}