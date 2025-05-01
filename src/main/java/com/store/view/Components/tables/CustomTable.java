package com.store.view.components.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CustomTable extends JTable {
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;

    public CustomTable(String[] columnNames) {
        this.tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                Object value = getValueAt(0, column);
                return value != null ? value.getClass() : Object.class;
            }
        };
        
        setModel(tableModel);
        TableStyler.styleTable(this);
        
        this.sorter = new TableRowSorter<>(tableModel);
        setRowSorter(sorter);
        
        // Configuraciones adicionales para asegurar visibilidad
        setFillsViewportHeight(true);
        setAutoCreateColumnsFromModel(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
}