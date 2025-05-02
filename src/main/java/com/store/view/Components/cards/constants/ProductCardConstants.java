package com.store.view.components.cards.constants;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Contiene constantes de diseño para las tarjetas de productos.
 * Define dimensiones, espaciados, colores y otros parámetros visuales
 * para mantener consistencia en la presentación de productos.
 */
public class ProductCardConstants {
    /**
     * Dimensiones totales de la tarjeta de producto (ancho x alto)
     */
    public static final Dimension CARD_SIZE = new Dimension(280, 360);
    
    /**
     * Dimensiones del área de imagen dentro de la tarjeta
     */
    public static final Dimension IMAGE_SIZE = new Dimension(240, 180);
    
    /**
     * Relleno (padding) alrededor de la imagen en píxeles
     */
    public static final int IMAGE_PADDING = 5;
    
    /**
     * Reducción de tamaño aplicada al escalar imágenes para mantener bordes
     */
    public static final int IMAGE_SCALE_OFFSET = 10;
    
    /**
     * Espacio superior en el área de detalles en píxeles
     */
    public static final int DETAILS_PADDING_TOP = 15;
    
    /**
     * Espaciado pequeño entre elementos (8px)
     */
    public static final int SPACING_SMALL = 8;
    
    /**
     * Espaciado medio entre elementos (12px)
     */
    public static final int SPACING_MEDIUM = 12;
    
    /**
     * Espaciado grande entre elementos (15px)
     */
    public static final int SPACING_LARGE = 15;
    
    /**
     * Color de fondo para el área de imagen
     */
    public static final Color IMAGE_BG_COLOR = new Color(245, 247, 250);
    
    /**
     * Altura máxima para botones dentro de la tarjeta
     */
    public static final int BUTTON_MAX_HEIGHT = 35;
}