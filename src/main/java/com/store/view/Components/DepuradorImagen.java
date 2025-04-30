package com.store.view.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class DepuradorImagen {

    public static void main(String[] args) {
        new DepuradorImagen().probarCargaImagen();
    }

    public void probarCargaImagen() {
        String ruta = "Img/card.jpeg"; // sin slash inicial

        // Verificar con ClassLoader
        System.out.println("Buscando con ClassLoader...");
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) {
            System.out.println("✔ Imagen encontrada: " + url);
        } else {
            System.err.println("✘ No se encontró la imagen con ClassLoader.");
        }

        // Intentar abrir el InputStream
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(ruta)) {
            if (is != null) {
                System.out.println("✔ InputStream abierto correctamente.");

                // Intentamos leer la imagen
                BufferedImage img = ImageIO.read(is);
                if (img != null) {
                    System.out.println("✔ Imagen cargada correctamente. Dimensiones: " +
                            img.getWidth() + "x" + img.getHeight());

                    // Mostrar imagen en ventana
                    JFrame frame = new JFrame("Vista previa de imagen");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(new JLabel(new ImageIcon(img)));
                    frame.pack();
                    frame.setVisible(true);
                } else {
                    System.err.println("✘ No se pudo decodificar la imagen.");
                }
            } else {
                System.err.println("✘ InputStream es null.");
            }
        } catch (Exception e) {
            System.err.println("✘ Error al leer la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
