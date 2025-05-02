package com.store.view.components.NavBar;

import javax.swing.*;
import java.awt.*;
import com.store.utils.Colors;
import com.store.utils.Fonts;

/**
 * Panel personalizado que muestra el logo de la aplicación compuesto por un icono circular
 * y el nombre de la aplicación. Utiliza los colores y fuentes definidos en las utilidades
 * de la aplicación para mantener consistencia visual.
 */
class LogoPanel extends JPanel {

    /**
     * Construye un nuevo panel de logo con el diseño y componentes configurados.
     */
    public LogoPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        setBackground(Colors.PANEL_BACKGROUND);
        
        add(new LogoIcon());
        add(new LogoLabel());
    }

    /**
     * Componente de icono circular personalizado que muestra las iniciales "MC".
     */
    private static class LogoIcon extends JLabel {
        
        /**
         * Construye el icono con tamaño fijo de 32x32 píxeles.
         */
        public LogoIcon() {
            setPreferredSize(new Dimension(32, 32));
        }
        
        /**
         * Dibuja el icono circular con las iniciales centradas.
         * @param g Objeto Graphics para el renderizado
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Colors.PRIMARY);
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(Colors.ACTIVE_TEXT);
            g2.setFont(Fonts.BUTTON);
            FontMetrics fm = g2.getFontMetrics();
            String text = "MC";
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(text, x, y);
            g2.dispose();
        }
    }

    /**
     * Componente de etiqueta que muestra el nombre de la aplicación.
     */
    private static class LogoLabel extends JLabel {
        
        /**
         * Construye la etiqueta con el nombre de la aplicación y estilo configurado.
         */
        public LogoLabel() {
            super("MotoCoreBD");
            setFont(Fonts.TITLE);
            setForeground(Colors.PRIMARY_TEXT);
        }
    }
}