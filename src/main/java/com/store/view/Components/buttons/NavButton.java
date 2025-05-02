package com.store.view.components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.store.utils.Colors;
import com.store.utils.Fonts;

/**
 * Botón personalizado para la barra de navegación con estados activo/inactivo
 * y efectos de hover. Utiliza los colores y fuentes definidos en la aplicación
 * para mantener consistencia visual.
 */
public class NavButton extends JButton {

    /**
     * Crea un nuevo botón de navegación con el texto especificado.
     * @param text Texto a mostrar en el botón
     */
    public NavButton(String text) {
        super(text);
        configurarEstiloInicial();
        agregarEfectoHover();
    }

    /**
     * Configura el estilo visual inicial del botón.
     */
    private void configurarEstiloInicial() {
        setFont(Fonts.BODY);
        setForeground(Colors.INACTIVE_TEXT);
        setBackground(Colors.PANEL_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
    }

    /**
     * Agrega el efecto hover al botón.
     */
    private void agregarEfectoHover() {
        addMouseListener(new NavButtonHoverEffect(this));
    }

    /**
     * Establece el estado activo/inactivo del botón.
     * @param active true para activar el estilo de botón activo, false para inactivo
     */
    public void setActive(boolean active) {
        setForeground(active ? Colors.PRIMARY : Colors.INACTIVE_TEXT);
        setFont(active ? Fonts.BOLD_BODY : Fonts.BODY);
    }
}

/**
 * Manejador de eventos de ratón para implementar efectos hover en los botones
 * de navegación. Cambia el color del texto cuando el ratón entra/sale del botón,
 * excepto cuando el botón está en estado activo.
 */
class NavButtonHoverEffect extends MouseAdapter {
    private final NavButton button;

    /**
     * Crea un nuevo manejador de efectos hover para el botón especificado.
     * @param button Botón al que se aplicarán los efectos
     */
    public NavButtonHoverEffect(NavButton button) {
        this.button = button;
    }

    /**
     * Maneja el evento de entrada del ratón cambiando el color del texto.
     * @param e Evento de ratón
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (!button.getForeground().equals(Colors.PRIMARY)) {
            button.setForeground(Colors.DARK_BLUE);
        }
    }

    /**
     * Maneja el evento de salida del ratón restaurando el color original.
     * @param e Evento de ratón
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (!button.getForeground().equals(Colors.PRIMARY)) {
            button.setForeground(Colors.INACTIVE_TEXT);
        }
    }
}