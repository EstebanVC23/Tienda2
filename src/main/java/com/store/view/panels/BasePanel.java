package com.store.view.panels;

import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.tables.CustomTable;

import javax.swing.*;
import java.awt.*;

/**
 * Clase abstracta base para los paneles principales de la aplicación.
 * Proporciona funcionalidades comunes como creación de tablas, paneles inferiores
 * y diálogos de mensaje/confirmación. Las clases concretas deben implementar refreshTable().
 */
public abstract class BasePanel extends JPanel {
    protected CustomTable table;
    protected AdminFilterPanel filterPanel;

    /**
     * Constructor que inicializa el panel con un BorderLayout.
     */
    public BasePanel() {
        setLayout(new BorderLayout());
    }

    /**
     * Crea un panel contenedor para una tabla con scroll.
     * @param table La tabla que se mostrará en el panel
     * @return JPanel configurado con la tabla y scroll
     */
    protected JPanel createTablePanel(CustomTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea un panel inferior con botones centrados.
     * @param buttons Botones a incluir en el panel
     * @return JPanel configurado con los botones
     */
    protected JPanel createBottomPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    /**
     * Muestra un diálogo de mensaje al usuario.
     * @param message El mensaje a mostrar
     * @param title El título del diálogo
     */
    protected void showSelectionMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un diálogo de confirmación al usuario.
     * @param message El mensaje de confirmación
     * @param title El título del diálogo
     * @return true si el usuario selecciona "Sí", false en caso contrario
     */
    protected boolean confirmAction(String message, String title) {
        int option = JOptionPane.showConfirmDialog(this, 
            message, 
            title, 
            JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    /**
     * Método abstracto para actualizar los datos de la tabla.
     * Debe ser implementado por las clases concretas.
     */
    public abstract void refreshTable();
}