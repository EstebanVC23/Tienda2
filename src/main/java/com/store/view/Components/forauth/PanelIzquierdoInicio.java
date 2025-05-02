package com.store.view.components.forauth;

import javax.swing.*;
import java.awt.*;
import com.store.utils.Colors;
import com.store.utils.Fonts;

/**
 * Panel personalizado para la sección izquierda de la pantalla de inicio.
 * Muestra un título, subtítulo e imagen con fondo degradado azul.
 */
public class PanelIzquierdoInicio extends JPanel {
    private JLabel labelLogo;
    private String titulo;
    private String subtitulo;

    /**
     * Construye un nuevo panel con título y subtítulo especificados.
     * @param titulo Texto principal a mostrar en el panel
     * @param subtitulo Texto secundario a mostrar bajo el título
     */
    public PanelIzquierdoInicio(String titulo, String subtitulo) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;

        setLayout(new BorderLayout());
        setOpaque(false);
        crearPanel();
    }

    /**
     * Configura y organiza los componentes visuales del panel.
     */
    private void crearPanel() {
        labelLogo = new JLabel(titulo, JLabel.CENTER);
        labelLogo.setForeground(Colors.ACTIVE_TEXT);
        labelLogo.setFont(Fonts.TITLE);

        JLabel subTitulo = new JLabel(subtitulo, JLabel.CENTER);
        subTitulo.setForeground(Colors.SECONDARY_GRAY);
    
        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        ImageIcon iconoOriginal = null;
        try {
            java.io.InputStream imgStream = getClass().getResourceAsStream("/Img/store.jpg");
            if (imgStream != null) {
                iconoOriginal = new ImageIcon(javax.imageio.ImageIO.read(imgStream));
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }

        JLabel iconoLabel;

        if (iconoOriginal != null) {
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
            iconoLabel = new JLabel(iconoEscalado);
        } else {
            iconoLabel = new JLabel("LOGO");
            iconoLabel.setForeground(Colors.ACTIVE_TEXT);
            iconoLabel.setFont(Fonts.SUBTITLE);
            iconoLabel.setPreferredSize(new Dimension(120, 120));
            iconoLabel.setMinimumSize(new Dimension(120, 120));
            iconoLabel.setMaximumSize(new Dimension(120, 120));
        }

        iconoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(Box.createVerticalGlue());
        panelCentro.add(iconoLabel);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentro.add(labelLogo);
        labelLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(subTitulo);
        subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);
    }

    /**
     * Pinta el fondo del panel con un degradado de azul claro a oscuro.
     * @param g Objeto Graphics usado para el renderizado
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gradient = new GradientPaint(
                0, 0, Colors.SECONDARY_BLUE,
                0, getHeight(), Colors.DARK_BLUE
        );

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}