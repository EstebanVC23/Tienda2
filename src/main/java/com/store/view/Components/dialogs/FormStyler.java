package com.store.view.components.dialogs;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Utilidad para estilizar componentes de formularios con un diseño consistente.
 * Proporciona métodos estáticos para crear componentes de formulario pre-estilizados.
 */
public class FormStyler {
    
    /**
     * Crea un panel base para formularios con estilo predefinido.
     * @return JPanel configurado con layout vertical, padding y color de fondo
     */
    public static JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        panel.setBackground(Colors.PANEL_BACKGROUND);
        return panel;
    }

    /**
     * Crea una etiqueta para formularios con estilo consistente.
     * @param text Texto a mostrar en la etiqueta
     * @return JLabel estilizado para formularios
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
     * Crea un campo de texto para formularios con estilo predefinido.
     * @return JTextField estilizado con tamaño máximo y borde
     */
    public static JTextField createFormTextField() {
        JTextField field = new JTextField();
        field.setFont(Fonts.BODY);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBorder(createFieldBorder());
        field.setBackground(Color.WHITE);
        return field;
    }

    /**
     * Crea un área de texto para formularios con estilo consistente.
     * @return JTextArea configurado con wrap de texto y borde
     */
    public static JTextArea createFormTextArea() {
        JTextArea area = new JTextArea(3, 20);
        area.setFont(Fonts.BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(createFieldBorder());
        area.setBackground(Color.WHITE);
        return area;
    }

    /**
     * Crea un combobox para formularios con estilo personalizado.
     * @param <T> Tipo de los elementos del combobox
     * @return JComboBox estilizado con renderer personalizado
     */
    public static <T> JComboBox<T> createFormComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(Fonts.BODY);
        combo.setBackground(Color.WHITE);
        combo.setBorder(createFieldBorder());
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(3, 5, 3, 5));
                return this;
            }
        });
        return combo;
    }

    /**
     * Crea un spinner para formularios con estilo consistente.
     * @return JSpinner configurado con editor numérico y borde
     */
    public static JSpinner createFormSpinner() {
        JSpinner spinner = new JSpinner();
        spinner.setFont(Fonts.BODY);
        spinner.setBorder(createFieldBorder());
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        editor.getTextField().setEditable(true);
        editor.getTextField().setBackground(Color.WHITE);
        return spinner;
    }

    /**
     * Crea un borde consistente para campos de formulario.
     * @return Border compuesto con línea y padding interno
     */
    private static Border createFieldBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
    }
}