package com.store.view.components.dialogs.user;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.shared.FormInputComponents;
import com.store.view.components.buttons.CustomButton;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;

/**
 * Diálogo para seleccionar la cantidad de un producto.
 */
public class QuantityInputDialog extends JDialog {
    private int selectedQuantity = 1;
    private boolean confirmed = false;
    private final JSpinner quantitySpinner;
    private final JLabel errorLabel;
    
    /**
     * Constructor del diálogo de selección de cantidad.
     * 
     * @param parent Ventana padre
     * @param nombreArticulo Nombre del artículo
     * @param maxStock Stock máximo disponible
     */
    public QuantityInputDialog(Window parent, String nombreArticulo, int maxStock) {
        super(parent, "Seleccionar Cantidad", Dialog.ModalityType.APPLICATION_MODAL);
        
        setSize(400, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Colors.BACKGROUND);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Colors.BACKGROUND);
        
        JLabel articleLabel = new JLabel(nombreArticulo);
        articleLabel.setFont(Fonts.SUBTITLE);
        articleLabel.setForeground(Colors.PRIMARY_TEXT);
        articleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        articleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel instructionLabel = new JLabel("Seleccione la cantidad:");
        instructionLabel.setFont(Fonts.BODY);
        instructionLabel.setForeground(Colors.PRIMARY_TEXT);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel stockLabel = new JLabel(String.format("Disponibles: %d unidades", maxStock));
        stockLabel.setFont(Fonts.SMALL);
        stockLabel.setForeground(Colors.SECONDARY_TEXT);
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        stockLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        quantitySpinner = FormInputComponents.createIntegerSpinner(1, 1, 1, maxStock);
        quantitySpinner.setFont(Fonts.SECTION_TITLE);
        quantitySpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(quantitySpinner, "#");
        quantitySpinner.setEditor(editor);
        
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        spinnerPanel.setBackground(Colors.BACKGROUND);
        spinnerPanel.add(quantitySpinner);
        
        errorLabel = new JLabel(" ");
        errorLabel.setFont(Fonts.SMALL);
        errorLabel.setForeground(Colors.ERROR_RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        contentPanel.add(articleLabel);
        contentPanel.add(instructionLabel);
        contentPanel.add(stockLabel);
        contentPanel.add(spinnerPanel);
        contentPanel.add(errorLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Colors.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        CustomButton cancelButton = new CustomButton("Cancelar", Colors.SECONDARY_GRAY);
        cancelButton.setRound(true);
        cancelButton.addActionListener(_ -> dispose());
        
        CustomButton confirmButton = new CustomButton("Confirmar", Colors.PRIMARY);
        confirmButton.setRound(true);
        confirmButton.addActionListener(_ -> {
            try {
                int quantity = (Integer) quantitySpinner.getValue();
                if (quantity < 1) {
                    errorLabel.setText("La cantidad debe ser al menos 1");
                    return;
                }
                if (quantity > maxStock) {
                    errorLabel.setText("No hay suficiente stock disponible");
                    return;
                }
                
                selectedQuantity = quantity;
                confirmed = true;
                dispose();
            } catch (Exception e) {
                errorLabel.setText("Cantidad inválida");
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
        
        setupKeyBindings(confirmButton, cancelButton);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    /**
     * Configura los atajos de teclado para el diálogo.
     * 
     * @param confirmButton Botón de confirmación
     * @param cancelButton Botón de cancelación
     */
    private void setupKeyBindings(CustomButton confirmButton, CustomButton cancelButton) {
        getRootPane().setDefaultButton(confirmButton);
        
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButton.doClick();
            }
        });
    }
    
    /**
     * Obtiene la cantidad seleccionada.
     * 
     * @return Cantidad seleccionada
     */
    public int getSelectedQuantity() {
        return selectedQuantity;
    }
    
    /**
     * Indica si se confirmó la selección.
     * 
     * @return true si se confirmó, false en caso contrario
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}