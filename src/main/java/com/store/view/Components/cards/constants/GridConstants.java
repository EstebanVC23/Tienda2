package com.store.view.components.cards.constants;

import java.awt.Font;

/**
 * Constantes para la configuración de grids que muestran tarjetas de productos.
 * Define parámetros de diseño y comportamiento para disposiciones en cuadrícula.
 */
public class GridConstants {
    /**
     * Número de columnas predeterminado para el layout de grid
     */
    public final int COLUMN_COUNT = 4;
    
    /**
     * Ancho estándar para cada tarjeta en píxeles
     */
    public final int CARD_WIDTH = 250;
    
    /**
     * Espaciado horizontal entre elementos del grid en píxeles
     */
    public final int H_GAP = 15;
    
    /**
     * Espaciado vertical entre elementos del grid en píxeles
     */
    public final int V_GAP = 15;
    
    /**
     * Velocidad de desplazamiento del scroll (en píxeles por evento)
     */
    public final int SCROLL_SPEED = 20;
    
    /**
     * Altura estándar de cada fila del grid en píxeles
     */
    public final int ROW_HEIGHT = 350;
    
    /**
     * Altura mínima que debe tener el contenedor del grid en píxeles
     */
    public final int MIN_HEIGHT = 600;
    
    /**
     * Mensaje a mostrar cuando no hay productos disponibles
     */
    public final String EMPTY_TEXT = "No se encontraron productos";
    
    /**
     * Fuente a utilizar para el mensaje de lista vacía
     */
    public final Font EMPTY_FONT = new Font("Segoe UI", Font.ITALIC, 16);
}