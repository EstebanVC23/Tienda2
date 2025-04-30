package com.store.view.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.border.EmptyBorder;
import java.text.NumberFormat;
import java.util.Locale;

import com.store.models.Producto;
import com.store.utils.Colors;

/**
 * Componente que muestra un producto en formato de tarjeta visual
 * para la interfaz de usuario.
 */
public class ContentCard extends JPanel {
    private static final int CARD_WIDTH = 280;
    private static final int CARD_HEIGHT = 350;
    
    private final Producto producto;
    private final JLabel imageLabel;
    private final JPanel infoPanel;
    
    /**
     * Constructor para la tarjeta de producto.
     * 
     * @param producto El producto a mostrar en la tarjeta
     */
    public ContentCard(Producto producto) {
        this.producto = producto;
        
        // Configuración del panel principal
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        
        // Panel de imagen
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(CARD_WIDTH, 180));
        imagePanel.setBackground(new Color(245, 247, 250));
        
        // Cargar imagen
        ImageIcon imageIcon = loadProductImage();
        imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        // Panel de información
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Etiqueta de categoría
        JLabel categoryLabel = new JLabel(producto.getCategoria().toUpperCase());
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        categoryLabel.setForeground(new Color(120, 144, 156));
        categoryLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        // Etiqueta de título
        JLabel titleLabel = new JLabel(producto.getNombre());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Colors.DARK_BLUE);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        
        // Panel para descripción con scroll
        JTextArea descriptionArea = new JTextArea(producto.getDescripcion());
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descriptionArea.setForeground(Colors.INACTIVE_TEXT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setRows(2);
        
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(CARD_WIDTH - 30, 45));
        scrollPane.setMaximumSize(new Dimension(CARD_WIDTH - 30, 45));
        
        // Panel inferior con precio y stock
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setAlignmentX(LEFT_ALIGNMENT);
        bottomPanel.setMaximumSize(new Dimension(CARD_WIDTH - 30, 30));
        
        // Formatear precio con símbolo de moneda
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("es").setRegion("ES").build());
        JLabel priceLabel = new JLabel(currencyFormat.format(producto.getPrecio()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(new Color(38, 166, 154));
        
        // Etiqueta de stock
        JLabel stockLabel = new JLabel(getStockText());
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        stockLabel.setForeground(getStockColor());
        stockLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        bottomPanel.add(priceLabel, BorderLayout.WEST);
        bottomPanel.add(stockLabel, BorderLayout.EAST);
        
        // Botón de añadir al carrito
        JButton addToCartButton = new JButton("Añadir al carrito");
        addToCartButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBackground(Colors.DARK_BLUE);
        addToCartButton.setBorderPainted(false);
        addToCartButton.setFocusPainted(false);
        addToCartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToCartButton.setAlignmentX(LEFT_ALIGNMENT);
        
        // Agregar espacio entre los componentes
        infoPanel.add(categoryLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(scrollPane);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(bottomPanel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(addToCartButton);
        
        // Efectos de hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colors.DARK_BLUE, 2),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
                ));
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
                ));
                repaint();
            }
        });
        
        // Agregar componentes al panel principal
        add(imagePanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }
    
    /**
     * Carga la imagen del producto desde la ruta especificada.
     * Método corregido para manejar correctamente los recursos.
     */
    private ImageIcon loadProductImage() {
        BufferedImage originalImage = null;
        InputStream imgStream = null;
    
        try {
            // Lista de rutas posibles donde puede estar la imagen
            String[] posiblesRutas = {    // Ruta absoluta desde el classpath
                "Img/card.jpeg"
            };
    
            for (String path : posiblesRutas) {
                // Intentamos con getClass().getResourceAsStream
                imgStream = getClass().getResourceAsStream(path);
                if (imgStream == null) {
                    // Intentamos con getClassLoader().getResourceAsStream
                    imgStream = getClass().getClassLoader().getResourceAsStream(path);
                }
            }
    
            if (imgStream != null) {
                originalImage = ImageIO.read(imgStream);
            } else {
                System.err.println("No se pudo encontrar ninguna imagen para el producto.");
            }
    
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (imgStream != null) {
                try {
                    imgStream.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el stream: " + e.getMessage());
                }
            }
        }
    
        // Si se cargó la imagen, redimensionarla
        if (originalImage != null) {
            return resizeImage(originalImage);
        } else {
            // Crear una imagen placeholder si no se pudo cargar
            return createPlaceholderImage();
        }
    }
    
    
    /**
     * Redimensiona una imagen manteniendo la relación de aspecto
     */
    private ImageIcon resizeImage(BufferedImage originalImage) {
        // Redimensionar manteniendo la relación de aspecto
        int width = CARD_WIDTH;
        int height = 180;
        
        // Calcular dimensiones manteniendo proporciones
        double ratio = (double) originalImage.getWidth() / originalImage.getHeight();
        if (originalImage.getWidth() > originalImage.getHeight()) {
            height = (int) (width / ratio);
        } else {
            width = (int) (height * ratio);
        }
        
        Image resizedImg = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        // Crear una imagen del tamaño exacto y centrar la imagen redimensionada
        BufferedImage finalImage = new BufferedImage(CARD_WIDTH, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = finalImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setColor(new Color(245, 247, 250)); // Color de fondo
        g2d.fillRect(0, 0, CARD_WIDTH, 180);
        
        // Centrar la imagen
        int x = (CARD_WIDTH - width) / 2;
        int y = (180 - height) / 2;
        g2d.drawImage(resizedImg, x, y, width, height, null);
        g2d.dispose();
        
        return new ImageIcon(finalImage);
    }
    
    /**
     * Crea una imagen placeholder cuando no se puede cargar la imagen
     */
    private ImageIcon createPlaceholderImage() {
        BufferedImage placeholder = new BufferedImage(CARD_WIDTH, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(new Color(245, 247, 250)); // Mismo color que el panel
        g2d.fillRect(0, 0, CARD_WIDTH, 180);
        
        g2d.setColor(Colors.INACTIVE_TEXT);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Centrar el texto
        FontMetrics fm = g2d.getFontMetrics();
        String message = "Imagen no disponible";
        int textWidth = fm.stringWidth(message);
        int textHeight = fm.getHeight();
        int x = (CARD_WIDTH - textWidth) / 2;
        int y = 90 + (textHeight / 4);
        
        g2d.drawString(message, x, y);
        
        // Dibujar un icono de imagen no disponible
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(CARD_WIDTH/2-30, 40, 60, 45);
        g2d.drawLine(CARD_WIDTH/2-30, 40, CARD_WIDTH/2+30, 85);
        g2d.drawLine(CARD_WIDTH/2+30, 40, CARD_WIDTH/2-30, 85);
        
        g2d.dispose();
        
        return new ImageIcon(placeholder);
    }
    
    /**
     * Determina el texto a mostrar basado en el stock del producto.
     */
    private String getStockText() {
        if (producto.getStock() > 10) {
            return "En stock";
        } else if (producto.getStock() > 0) {
            return "¡Últimas unidades!";
        } else {
            return "Agotado";
        }
    }
    
    /**
     * Determina el color del texto de stock basado en la cantidad disponible.
     */
    private Color getStockColor() {
        if (producto.getStock() > 10) {
            return new Color(46, 125, 50);  // Verde
        } else if (producto.getStock() > 0) {
            return new Color(255, 152, 0);  // Naranja
        } else {
            return new Color(211, 47, 47);  // Rojo
        }
    }
    
    /**
     * Devuelve el producto asociado a esta tarjeta.
     */
    public Producto getProducto() {
        return producto;
    }
}