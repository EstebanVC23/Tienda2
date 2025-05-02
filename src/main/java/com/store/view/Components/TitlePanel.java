package com.store.view.components;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;

/**
 * Panel de título personalizado para encabezados de sección en la interfaz de usuario.
 * Proporciona un área visualmente consistente para mostrar títulos principales,
 * con soporte para incluir iconos y configuración de estilos predefinidos.
 */
public class TitlePanel extends JPanel {

    /**
     * Construye un panel de título con el texto especificado.
     * 
     * @param title El texto del título que se mostrará en el panel. No puede ser nulo.
     * @throws IllegalArgumentException Si el título proporcionado es nulo.
     */
    public TitlePanel(String title) {
        if (title == null) {
            throw new IllegalArgumentException("El título no puede ser nulo");
        }

        configurePanelStyle();
        JLabel titleLabel = createTitleLabel(title);
        JPanel titleContainer = createTitleContainer(titleLabel);
        add(titleContainer, BorderLayout.WEST);
    }

    /**
     * Configura las propiedades básicas del panel:
     * - Layout: BorderLayout
     * - Color de fondo: Blanco
     * - Borde inferior con color y padding definidos
     */
    private void configurePanelStyle() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
    }

    /**
     * Crea y configura la etiqueta del título con los estilos visuales predefinidos.
     * 
     * @param title El texto del título.
     * @return JLabel configurada con los estilos adecuados.
     */
    private JLabel createTitleLabel(String title) {
        JLabel label = new JLabel(title);
        label.setFont(Fonts.TITLE);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setHorizontalAlignment(JLabel.LEFT);
        return label;
    }

    /**
     * Crea un contenedor para el título que permite agregar elementos adicionales
     * como iconos manteniendo la alineación adecuada.
     * 
     * @param titleLabel La etiqueta del título ya configurada.
     * @return Panel contenedor con el título y espacio para elementos adicionales.
     */
    private JPanel createTitleContainer(JLabel titleLabel) {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        container.setBackground(Color.WHITE);
        container.add(titleLabel);
        return container;
    }
}