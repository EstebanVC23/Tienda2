package com.store.view.components.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Tabla personalizada que extiende JTable con funcionalidades adicionales.
 * Incluye un modelo de tabla no editable y capacidad de ordenamiento.
 */
public class CustomTable extends JTable {
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;

    /**
     * Crea una nueva tabla personalizada con los nombres de columna especificados.
     * @param columnNames Nombres de las columnas para la tabla
     */
    public CustomTable(String[] columnNames) {
        this.tableModel = createCustomTableModel(columnNames);
        setModel(tableModel);
        TableStyler.styleTable(this);
        this.sorter = new TableRowSorter<>(tableModel);
        setRowSorter(sorter);
    }

    /**
     * Crea y configura el modelo de tabla personalizado.
     * @param columnNames Nombres de las columnas
     * @return Modelo de tabla configurado
     */
    private DefaultTableModel createCustomTableModel(String[] columnNames) {
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (getRowCount() == 0) {
                    return Object.class;
                }
                Object value = getValueAt(0, column);
                return value != null ? value.getClass() : Object.class;
            }
        };
    }

    /**
     * Obtiene el modelo de tabla subyacente.
     * @return Modelo de tabla DefaultTableModel
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Obtiene el ordenador de filas de la tabla.
     * @return TableRowSorter para el modelo de tabla
     */
    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
}