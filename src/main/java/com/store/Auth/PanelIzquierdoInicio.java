package com.store.Auth;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelIzquierdoInicio que extiende JPanel.
 * Esta clase representa un panel con un título, un subtítulo y una imagen, con
 * un fondo personalizado.
 */
public class PanelIzquierdoInicio extends JPanel {
    private JLabel labelLogo;
    private String titulo;
    private String subtitulo;

    /**
     * Constructor de la clase PanelIzquierdoInicio.
     *
     * @param titulo    Texto del título principal.
     * @param subtitulo Texto del subtítulo.
     */
    public PanelIzquierdoInicio(String titulo, String subtitulo) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;

        setLayout(new BorderLayout());
        setOpaque(false);
        crearPanel();
    }

    /**
     * Método privado que configura los componentes del panel.
     */
    private void crearPanel() {
        labelLogo = new JLabel(titulo, JLabel.CENTER);
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JLabel subTitulo = new JLabel(subtitulo, JLabel.CENTER);
        subTitulo.setForeground(new Color(236, 240, 241, 200)); // Color gris claro con transparencia
    
        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        // Carga de la imagen corregida
        ImageIcon iconoOriginal = null;
        try {
            // Usa getResourceAsStream para mejor manejo de recursos
            java.io.InputStream imgStream = getClass().getResourceAsStream("/Img/store.jpg");
            if (imgStream != null) {
                iconoOriginal = new ImageIcon(javax.imageio.ImageIO.read(imgStream));
            } else {
                System.err.println("No se encontró el recurso: /Img/store.jpg");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }

        JLabel iconoLabel;

        if (iconoOriginal != null) {
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
            iconoLabel = new JLabel(iconoEscalado);
        } else {
            // Fallback visual si no se carga la imagen
            iconoLabel = new JLabel("LOGO");
            iconoLabel.setForeground(Color.WHITE);
            iconoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
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
     * Sobrescribe el método paintComponent para personalizar el fondo del panel con
     * un gradiente de color.
     *
     * @param g Objeto Graphics que permite dibujar en el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(41, 128, 185), // Azul claro y brillante (parte superior)
                0, getHeight(), new Color(26, 83, 119) // Azul más oscuro (parte inferior)
        );

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}