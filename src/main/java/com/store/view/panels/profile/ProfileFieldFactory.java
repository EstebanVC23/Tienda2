package com.store.view.panels.profile;

import javax.swing.*;
import java.awt.*;

/**
 * Factory class para crear componentes de interfaz de usuario estilizados
 * para los campos del perfil de usuario. Proporciona métodos estáticos
 * para crear etiquetas, campos de texto y áreas de texto con estilos consistentes.
 */
public class ProfileFieldFactory {
    
    /**
     * Crea una etiqueta (JLabel) con el estilo definido para los campos de perfil.
     * @param text El texto que mostrará la etiqueta
     * @return JLabel configurado con los estilos de la aplicación
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UserProfileConstants.LABEL_FONT);
        label.setForeground(UserProfileConstants.LABEL_COLOR);
        return label;
    }

    /**
     * Crea un campo de texto editable (JTextField) con el estilo definido.
     * @param value El valor inicial del campo de texto
     * @return JTextField configurado con los estilos de la aplicación
     */
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

    /**
     * Crea un área de texto editable (JTextArea) con el estilo definido.
     * @param value El valor inicial del área de texto
     * @return JTextArea configurado con los estilos de la aplicación
     */
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

    /**
     * Crea una etiqueta de solo lectura (JLabel) para mostrar valores no editables.
     * @param value El valor a mostrar en la etiqueta
     * @return JLabel configurado con los estilos de solo lectura
     */
    public static JLabel createReadOnlyField(String value) {
        JLabel label = new JLabel(value != null ? value : "-");
        label.setFont(UserProfileConstants.VALUE_FONT);
        label.setForeground(UserProfileConstants.VALUE_COLOR);
        return label;
    }
}