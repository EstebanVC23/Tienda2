package com.store.view.components.NavBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.store.utils.Colors;
import com.store.view.components.buttons.NavButton;

/**
 * Panel que contiene y gestiona los botones de navegación de la aplicación.
 * Coordina la interacción entre los botones y la barra de navegación principal.
 */
class NavButtonsPanel extends JPanel {

    /**
     * Construye un panel de botones de navegación con las opciones especificadas.
     * 
     * @param listener Manejador de eventos para acciones de los botones
     * @param options Etiquetas para los botones de navegación
     * @param navbar Referencia a la barra de navegación principal para manejar el estado activo
     */
    public NavButtonsPanel(ActionListener listener, String[] options, Navbar navbar) {
        configurarPanel();
        JPanel buttonsContainer = crearContenedorBotones(listener, options, navbar);
        add(buttonsContainer);
    }

    /**
     * Configura las propiedades básicas del panel.
     */
    private void configurarPanel() {
        setLayout(new GridBagLayout());
        setBackground(Colors.PANEL_BACKGROUND);
    }

    /**
     * Crea y configura el contenedor de botones de navegación.
     * 
     * @param listener Manejador de eventos para los botones
     * @param options Opciones de navegación
     * @param navbar Barra de navegación padre
     * @return Panel conteniendo los botones configurados
     */
    private JPanel crearContenedorBotones(ActionListener listener, String[] options, Navbar navbar) {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        container.setBackground(Colors.PANEL_BACKGROUND);

        for (String label : options) {
            NavButton button = crearBotonNavegacion(label, listener, navbar);
            container.add(button);

            if (label.equals("Productos")) {
                navbar.setActiveButton(button);
            }
        }

        return container;
    }

    /**
     * Crea y configura un botón de navegación individual.
     * 
     * @param label Texto del botón
     * @param listener Manejador de eventos
     * @param navbar Barra de navegación padre
     * @return Botón de navegación configurado
     */
    private NavButton crearBotonNavegacion(String label, ActionListener listener, Navbar navbar) {
        NavButton button = new NavButton(label);
        button.addActionListener(e -> {
            navbar.setActiveButton(button);
            listener.actionPerformed(e);
        });
        return button;
    }
}