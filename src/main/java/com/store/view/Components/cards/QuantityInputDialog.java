package com.store.view.components.cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class QuantityInputDialog extends JDialog {
    private int selectedQuantity = 1;
    private boolean confirmed = false;
    private final JSpinner quantitySpinner;
    
    public QuantityInputDialog(Window parent, int maxStock) {
        super(parent, "Seleccionar Cantidad", Dialog.ModalityType.APPLICATION_MODAL);
        
        setLayout(new BorderLayout(10, 10));
        setSize(300, 150);
        setLocationRelativeTo(parent);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Label
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 5);
        mainPanel.add(new JLabel("Cantidad:"), gbc);
        
        // Spinner
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, maxStock, 1));
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.insets = new Insets(10, 5, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(quantitySpinner, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(_ -> {
            selectedQuantity = (Integer) quantitySpinner.getValue();
            confirmed = true;
            dispose();
        });
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(_ -> dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Hacer que Enter confirme y Escape cancele
        getRootPane().setDefaultButton(okButton);
        
        // Configurar teclas
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    public int getSelectedQuantity() {
        return selectedQuantity;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
}