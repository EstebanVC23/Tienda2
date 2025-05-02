package com.store.view.components.NavBar;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import com.store.utils.Colors;
import com.store.view.components.buttons.NavButton;

/**
 * Barra de navegación principal que contiene el logo de la aplicación y los botones
 * de navegación. Maneja el estado activo de los botones y proporciona una interfaz
 * consistente para la navegación entre secciones.
 */
public class Navbar extends JPanel {
    private NavButton activeButton;
    
    /**
     * Construye una nueva barra de navegación con los botones especificados.
     * @param navActionListener Manejador de eventos para las acciones de los botones
     * @param options Opciones de navegación que se mostrarán como botones
     */
    public Navbar(ActionListener navActionListener, String[] options) {
        setLayout(new BorderLayout());
        setBackground(Colors.PANEL_BACKGROUND);
        setBorder(createNavbarBorder());
        
        JPanel navbarContent = createNavbarContent(navActionListener, options);
        add(navbarContent, BorderLayout.CENTER);
    }
    
    /**
     * Crea el borde compuesto para la barra de navegación.
     * @return Border configurado para la barra
     */
    private Border createNavbarBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(5, 20, 5, 20)
        );
    }
    
    /**
     * Crea el contenido principal de la barra de navegación.
     * @param navActionListener Manejador de eventos para los botones
     * @param options Opciones de navegación
     * @return Panel con los componentes de la barra de navegación
     */
    private JPanel createNavbarContent(ActionListener navActionListener, String[] options) {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Colors.PANEL_BACKGROUND);
        
        content.add(new LogoPanel(), BorderLayout.WEST);
        content.add(new NavButtonsPanel(navActionListener, options, this), BorderLayout.CENTER);
        
        return content;
    }
    
    /**
     * Establece el botón activo en la barra de navegación.
     * @param button Botón que se marcará como activo
     */
    public void setActiveButton(NavButton button) {
        if (activeButton != null) {
            activeButton.setActive(false);
        }
        activeButton = button;
        if (activeButton != null) {
            activeButton.setActive(true);
        }
    }
}