package com.store.view.components.dialogs;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Clase utilitaria para estilizar componentes de formularios de manera consistente.
 * Proporciona métodos estáticos para crear y configurar componentes de UI con el estilo de la aplicación.
 */
public class FormStyler {
    // Usamos las constantes existentes de Fonts
    private static final Font LABEL_FONT = Fonts.BOLD_BODY;
    private static final Color FIELD_BACKGROUND = Color.WHITE;
    private static final Border FIELD_BORDER = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Colors.BORDER, 1),
        BorderFactory.createEmptyBorder(8, 12, 8, 12)
    );
    private static final Border COMBO_BORDER = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Colors.BORDER, 1),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
    );

    /**
     * Crea y configura un panel base para formularios.
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
     */
    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        return label;
    }

    /**
     * Crea y configura un campo de texto para formularios.
     */
    public static JTextField createFormTextField() {
        JTextField field = new JTextField();
        field.setFont(Fonts.BODY);  // Usamos Fonts.BODY directamente
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBorder(FIELD_BORDER);
        field.setBackground(FIELD_BACKGROUND);
        return field;
    }

    /**
     * Crea y configura un área de texto para formularios.
     */
    public static JTextArea createFormTextArea() {
        JTextArea area = new JTextArea(3, 20);
        area.setFont(Fonts.BODY);  // Usamos Fonts.BODY directamente
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(FIELD_BORDER);
        area.setBackground(FIELD_BACKGROUND);
        return area;
    }

    /**
     * Crea y configura un combo box para formularios.
     */
    public static JComboBox<String> createFormComboBox() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(Fonts.BODY);  // Usamos Fonts.BODY directamente
        combo.setBackground(FIELD_BACKGROUND);
        combo.setBorder(COMBO_BORDER);
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
     */
    public static JSpinner createFormSpinner() {
        JSpinner spinner = new JSpinner();
        spinner.setFont(Fonts.BODY);  // Usamos Fonts.BODY directamente
        spinner.setBorder(COMBO_BORDER);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(true);
        return spinner;
    }
}