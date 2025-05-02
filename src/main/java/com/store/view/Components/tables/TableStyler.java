package com.store.view.components.tables;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Utilidad para aplicar estilos consistentes a tablas JTable.
 * Proporciona configuración visual unificada para tablas en la aplicación.
 */
public class TableStyler {

    /**
     * Aplica estilos consistentes a una tabla JTable.
     * @param table Tabla a estilizar
     */
    public static void styleTable(JTable table) {
        configurarTablaBasica(table);
        configurarEncabezadoTabla(table);
        configurarRenderizadoCeldas(table);
    }

    /**
     * Configura las propiedades básicas de la tabla.
     * @param table Tabla a configurar
     */
    private static void configurarTablaBasica(JTable table) {
        table.setFont(Fonts.BODY);
        table.setRowHeight(30);
        table.setSelectionBackground(Colors.LIGHT_BLUE);
        table.setSelectionForeground(Colors.PRIMARY_TEXT);
        table.setGridColor(Colors.BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
    }

    /**
     * Configura el encabezado de la tabla.
     * @param table Tabla cuyo encabezado se va a configurar
     */
    private static void configurarEncabezadoTabla(JTable table) {
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(Fonts.SECTION_TITLE);
            header.setBackground(Colors.PRIMARY_BLUE);
            header.setForeground(Color.WHITE);
            header.setPreferredSize(new Dimension(header.getWidth(), 35));
            header.setReorderingAllowed(false);
            header.setResizingAllowed(true);
        }
    }

    /**
     * Configura el renderizado personalizado para las celdas.
     * @param table Tabla a configurar
     */
    private static void configurarRenderizadoCeldas(JTable table) {
        DefaultTableCellRenderer cellRenderer = crearCellRenderer();
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    /**
     * Crea y configura un renderizador personalizado para celdas.
     * @return Renderizador de celdas configurado
     */
    private static DefaultTableCellRenderer crearCellRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                setBackground(determinarColorFondo(row, isSelected));
                setHorizontalAlignment(SwingConstants.LEFT);
                
                return this;
            }

            /**
             * Determina el color de fondo basado en fila y selección.
             * @param row Índice de la fila
             * @param isSelected Indica si la celda está seleccionada
             * @return Color de fondo apropiado
             */
            private Color determinarColorFondo(int row, boolean isSelected) {
                if (isSelected) {
                    return Colors.LIGHT_BLUE;
                }
                return row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248);
            }
        };
    }
}