package com.store.view.components.dialogs.constants;

import java.awt.*;

/**
 * Clase que contiene constantes para la configuración del diálogo de detalles de producto.
 * Agrupa todas las constantes relacionadas con dimensiones, textos, colores y espaciados
 * utilizados en el diálogo de detalles de producto.
 */
public class ProductDetailsConstants {
    /** Título del diálogo */
    public static final String TITLE = "Detalles del Producto";
    /** Ancho del diálogo */
    public static final int WIDTH = 700;
    /** Alto del diálogo */
    public static final int HEIGHT = 500;
    
    // Constantes para la imagen del producto
    /** Tamaño del panel de imagen */
    public static final Dimension IMAGE_SIZE = new Dimension(200, 200);
    /** Reducción de tamaño para la imagen dentro del panel */
    public static final int IMAGE_SCALE_OFFSET = 20;
    /** Relleno interno del panel de imagen */
    public static final int IMAGE_PADDING = 10;
    /** Color de fondo del panel de imagen */
    public static final Color IMAGE_BG_COLOR = new Color(245, 247, 250);
    /** Texto alternativo cuando no hay imagen disponible */
    public static final String NO_IMAGE_TEXT = "No hay imagen disponible";
    
    // Constantes para espaciado del contenido
    /** Espaciado horizontal entre componentes principales */
    public static final int CONTENT_HGAP = 15;
    /** Espaciado vertical entre componentes principales */
    public static final int CONTENT_VGAP = 0;
    
    // Constantes para el encabezado
    /** Espaciado entre el nombre y el precio del producto */
    public static final int NAME_PRICE_SPACING = 5;
    /** Espaciado inferior del panel de encabezado */
    public static final int HEADER_BOTTOM_SPACING = 15;
    
    // Constantes para el panel de información
    /** Espaciado horizontal entre elementos de información */
    public static final int INFO_HGAP = 10;
    /** Espaciado vertical entre elementos de información */
    public static final int INFO_VGAP = 5;
    /** Espaciado inferior del panel de información */
    public static final int INFO_BOTTOM_SPACING = 15;
    
    // Constantes para la descripción
    /** Título de la sección de descripción */
    public static final String DESCRIPTION_TITLE = "Descripción";
    /** Espaciado entre el título y el texto de descripción */
    public static final int DESCRIPTION_TITLE_SPACING = 5;
    /** Espaciado superior del texto de descripción */
    public static final int DESCRIPTION_TOP_SPACING = 10;
    
    // Constantes para botones y mensajes
    /** Texto del botón para añadir al carrito */
    public static final String ADD_TO_CART_TEXT = "Añadir al carrito";
    /** Texto del botón para cerrar */
    public static final String CLOSE_TEXT = "Cerrar";
    /** Mensaje al añadir producto al carrito */
    public static final String ADD_TO_CART_MESSAGE = "Producto añadido al carrito";
    /** Título del mensaje al añadir producto al carrito */
    public static final String ADD_TO_CART_TITLE = "Éxito";
}