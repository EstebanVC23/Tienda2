package com.store.view.panels;

import com.store.view.components.CustomTable;
import com.store.view.components.filters.AdminFilterPanel;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {
    protected CustomTable table;
    protected AdminFilterPanel filterPanel;

    public BasePanel() {
        setLayout(new BorderLayout());
    }

    protected JPanel createTablePanel(CustomTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    protected JPanel createBottomPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    protected void showSelectionMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    protected boolean confirmAction(String message, String title) {
        int option = JOptionPane.showConfirmDialog(this, 
            message, 
            title, 
            JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    public abstract void refreshTable();
}