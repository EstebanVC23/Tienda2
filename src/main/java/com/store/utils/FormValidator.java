package com.store.utils;

import com.store.models.Producto;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;
import java.util.function.Consumer;

public class FormValidator {
    
    private static final Color ERROR_COLOR = new Color(255, 221, 221);
    private static final Color NORMAL_COLOR = Color.WHITE;
    
    public static void numericOnly(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    
    public static void decimalOnly(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newText = field.getText() + string;
                if (newText.matches("\\d*\\.?\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (newText.matches("\\d*\\.?\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    
    public static void setupStockSpinner(JSpinner spinner, int currentStock) {
        spinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9999, 1));
        JTextField editor = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
        numericOnly(editor);
    }
    
    public static void setupPriceSpinner(JSpinner spinner, double initialValue) {
        spinner.setModel(new javax.swing.SpinnerNumberModel(initialValue, 0.0, 99999.99, 0.01));
        JTextField editor = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
        decimalOnly(editor);
    }
    
    public static boolean validateRequired(JTextComponent component, Consumer<String> errorCallback) {
        String text = component.getText().trim();
        if (text.isEmpty()) {
            highlightError(component);
            if (errorCallback != null) {
                errorCallback.accept("Este campo es obligatorio");
            }
            return false;
        }
        resetHighlight(component);
        return true;
    }
    
    public static boolean validateDecimal(JTextComponent component, Consumer<String> errorCallback) {
        String text = component.getText().trim();
        try {
            if (!text.isEmpty()) {
                Double.parseDouble(text);
                resetHighlight(component);
                return true;
            }
        } catch (NumberFormatException e) {
            // Fall through to error handling
        }
        
        highlightError(component);
        if (errorCallback != null) {
            errorCallback.accept("Ingrese un número válido");
        }
        return false;
    }
    
    public static boolean validateInteger(JTextComponent component, Consumer<String> errorCallback) {
        String text = component.getText().trim();
        try {
            if (!text.isEmpty()) {
                Integer.parseInt(text);
                resetHighlight(component);
                return true;
            }
        } catch (NumberFormatException e) {
            // Fall through to error handling
        }
        
        highlightError(component);
        if (errorCallback != null) {
            errorCallback.accept("Ingrese un número entero");
        }
        return false;
    }
    
    public static boolean validateProduct(Producto producto, Consumer<String> errorCallback) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            if (errorCallback != null) errorCallback.accept("El nombre es obligatorio");
            return false;
        }
        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
            if (errorCallback != null) errorCallback.accept("La descripción es obligatoria");
            return false;
        }
        if (producto.getPrecio() <= 0) {
            if (errorCallback != null) errorCallback.accept("El precio debe ser mayor a cero");
            return false;
        }
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            if (errorCallback != null) errorCallback.accept("Seleccione una categoría");
            return false;
        }
        if (producto.getProveedor() == null || producto.getProveedor().trim().isEmpty()) {
            if (errorCallback != null) errorCallback.accept("Seleccione un proveedor");
            return false;
        }
        return true;
    }
    
    private static void highlightError(JComponent component) {
        component.setBackground(ERROR_COLOR);
        component.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.ERROR_RED, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }
    
    private static void resetHighlight(JComponent component) {
        component.setBackground(NORMAL_COLOR);
        component.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }
    
    public static void addLiveValidation(JTextComponent component, Consumer<JTextComponent> validator) {
        component.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validator.accept(component); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validator.accept(component); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validator.accept(component); }
        });
    }
}