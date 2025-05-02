package com.store.view.components.buttons;

import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Botón personalizado con efectos visuales avanzados.
 * Permite esquinas redondeadas, colores para hover y pressed, y personalización del estilo.
 */
public class CustomButton extends JButton {
    private Color hoverColor;
    private Color pressedColor;
    private int cornerRadius = 8;
    private boolean isRound = false;

    /**
     * Crea un botón con texto y color de fondo.
     * @param text Texto del botón
     * @param bgColor Color de fondo
     */
    public CustomButton(String text, Color bgColor) {
        this(text, bgColor, Color.WHITE);
    }

    /**
     * Crea un botón con texto, color de fondo y color de texto.
     * @param text Texto del botón
     * @param bgColor Color de fondo
     * @param fgColor Color del texto
     */
    public CustomButton(String text, Color bgColor, Color fgColor) {
        this(text, bgColor, fgColor, Fonts.BUTTON);
    }

    /**
     * Crea un botón completamente personalizable.
     * @param text Texto del botón
     * @param bgColor Color de fondo
     * @param fgColor Color del texto
     * @param font Fuente del texto
     */
    public CustomButton(String text, Color bgColor, Color fgColor, Font font) {
        super(text);
        initButton(bgColor, fgColor, font);
        setupHoverEffects(bgColor);
    }

    /**
     * Inicializa las propiedades básicas del botón.
     */
    private void initButton(Color bgColor, Color fgColor, Font font) {
        setFont(font);
        setForeground(fgColor);
        setBackground(bgColor);
        this.hoverColor = bgColor.darker();
        this.pressedColor = bgColor.brighter();
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Configura los efectos de hover y pressed.
     */
    private void setupHoverEffects(Color bgColor) {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(bgColor);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(pressedColor);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(hoverColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintBackground(g2);
        paintPressedEffect(g2);
        paintText(g2);

        g2.dispose();
    }

    /**
     * Pinta el fondo del botón.
     */
    private void paintBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        if (isRound) {
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        } else {
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Pinta el efecto de pressed.
     */
    private void paintPressedEffect(Graphics2D g2) {
        if (getModel().isPressed()) {
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Pinta el texto del botón.
     */
    private void paintText(Graphics2D g2) {
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = getBounds();
        int x = (r.width - fm.stringWidth(getText())) / 2;
        int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), x, y);
    }

    /**
     * Establece el color para el estado hover.
     * @param hoverColor Color para el hover
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    /**
     * Establece el color para el estado pressed.
     * @param pressedColor Color para el pressed
     */
    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    /**
     * Establece el radio de las esquinas redondeadas.
     * @param radius Radio en píxeles
     */
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    /**
     * Habilita o deshabilita las esquinas redondeadas.
     * @param round true para habilitar esquinas redondeadas
     */
    public void setRound(boolean round) {
        this.isRound = round;
        repaint();
    }

    /**
     * Establece el color de fondo del botón.
     * @param bg Color de fondo
     */
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (hoverColor == null || hoverColor.equals(getBackground().darker())) {
            hoverColor = bg.darker();
        }
    }
}