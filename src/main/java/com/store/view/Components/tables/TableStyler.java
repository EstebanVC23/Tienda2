package com.store.view.components.tables;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableStyler {
    public static void styleTable(JTable table) {
        // Estilo para la tabla
        table.setFont(Fonts.BODY);
        table.setRowHeight(30);
        table.setSelectionBackground(Colors.LIGHT_BLUE);
        table.setSelectionForeground(Colors.PRIMARY_TEXT);
        table.setGridColor(Colors.BORDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        
        // Estilo para el header (asegurando visibilidad)
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(Fonts.SECTION_TITLE);
            header.setBackground(Colors.PRIMARY_BLUE);
            header.setForeground(Color.WHITE);
            header.setPreferredSize(new Dimension(header.getWidth(), 35));
            header.setReorderingAllowed(false);
            header.setResizingAllowed(true);
        }
        
        // Renderer para celdas
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(new Color(248, 248, 248));
                }
                
                if (isSelected) {
                    setBackground(Colors.LIGHT_BLUE);
                }
                
                return this;
            }
        };
        
        cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
}