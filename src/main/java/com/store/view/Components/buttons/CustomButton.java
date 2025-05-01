package com.store.view.components.buttons;

import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomButton extends JButton {
    private Color hoverColor;
    private Color pressedColor;
    private int cornerRadius = 8;
    private boolean isRound = false;

    public CustomButton(String text, Color bgColor) {
        this(text, bgColor, Color.WHITE);
    }

    public CustomButton(String text, Color bgColor, Color fgColor) {
        super(text);
        setFont(Fonts.BUTTON);
        setForeground(fgColor);
        setBackground(bgColor);
        this.hoverColor = bgColor.darker();
        this.pressedColor = bgColor.brighter();
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Efecto hover y pressed
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

        // Fondo del botón
        if (isRound) {
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        } else {
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Sombra (opcional)
        if (getModel().isPressed()) {
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Texto
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = getBounds();
        int x = (r.width - fm.stringWidth(getText())) / 2;
        int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    // Métodos para personalización adicional
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
    }

    public void setRound(boolean round) {
        this.isRound = round;
        repaint();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (hoverColor == null || hoverColor.equals(getBackground().darker())) {
            hoverColor = bg.darker();
        }
    }
}