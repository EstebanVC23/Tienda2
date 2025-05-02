package com.store.view.components.cards.spaces;

import java.awt.*;
import javax.swing.*;

/**
 * Layout personalizado que permite el ajuste automático de componentes en múltiples filas.
 * Extiende FlowLayout para proporcionar un comportamiento de "wrap" mejorado.
 */
public class WrapLayout extends FlowLayout {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor que inicializa el layout con alineación y espacios especificados.
     * @param align Alineación de los componentes (LEFT, CENTER, RIGHT)
     * @param hgap Espacio horizontal entre componentes
     * @param vgap Espacio vertical entre componentes
     */
    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    /**
     * Constructor que inicializa el layout con alineación y espacios por defecto.
     * @param align Alineación de los componentes (LEFT, CENTER, RIGHT)
     */
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    /**
     * Calcula el tamaño mínimo del layout basado en los componentes contenidos.
     * @param target Contenedor que usa este layout
     * @return Dimensiones mínimas calculadas para el layout
     */
    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    /**
     * Calcula el tamaño del layout basado en los componentes contenidos.
     * @param target Contenedor que usa este layout
     * @param preferred Indica si debe usarse el tamaño preferido (true) o mínimo (false)
     * @return Dimensiones calculadas para el layout
     */
    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            for (Component m : target.getComponents()) {
                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth != 0) rowWidth += hgap;
                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);
            dim.width += insets.left + insets.right + hgap * 2;
            dim.height += insets.top + insets.bottom + vgap * 2;

            Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
            if (scrollPane != null && target.isValid()) {
                dim.width = targetWidth;
            }

            return dim;
        }
    }

    /**
     * Añade una fila de componentes a las dimensiones calculadas.
     * @param dim Dimensiones acumuladas
     * @param rowWidth Ancho de la fila actual
     * @param rowHeight Alto de la fila actual
     */
    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);
        if (dim.height > 0) dim.height += getVgap();
        dim.height += rowHeight;
    }
}