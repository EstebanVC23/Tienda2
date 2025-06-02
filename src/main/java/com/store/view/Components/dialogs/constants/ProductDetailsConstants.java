package com.store.view.components.dialogs.constants;

import java.awt.Color;
import java.awt.Dimension;

public class ProductDetailsConstants {
    public static final String TITLE = "Detalles del Producto";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    // Image panel constants
    public static final Dimension IMAGE_SIZE = new Dimension(300, 300);
    public static final int IMAGE_SCALE_OFFSET = 30;
    public static final int IMAGE_PADDING = 15;
    public static final Color IMAGE_BG_COLOR = new Color(245, 245, 245);
    public static final String NO_IMAGE_TEXT = "Imagen no disponible";
    
    // Header panel constants
    public static final int NAME_PRICE_SPACING = 5;
    public static final int HEADER_BOTTOM_SPACING = 15;
    
    // Info panel constants
    public static final int INFO_HGAP = 20;
    public static final int INFO_VGAP = 5;
    public static final int INFO_BOTTOM_SPACING = 20;
    
    // Description panel constants
    public static final String DESCRIPTION_TITLE = "Descripción";
    public static final int DESCRIPTION_TITLE_SPACING = 5;
    public static final int DESCRIPTION_TOP_SPACING = 5;
    
    // Content layout
    public static final int CONTENT_HGAP = 20;
    public static final int CONTENT_VGAP = 0;
    
    // Action texts
    public static final String ADD_TO_CART_TEXT = "Agregar al carrito";
    public static final String REMOVE_FROM_CART_TEXT = "Quitar del carrito";
    public static final String CLOSE_TEXT = "Cerrar";
    
    // Messages
    public static final String ADD_TO_CART_TITLE = "Carrito de compras";
    public static final String ADD_TO_CART_SUCCESS_MESSAGE = "Producto agregado al carrito";
    public static final String ADD_TO_CART_ERROR_MESSAGE = "No se pudo agregar el producto al carrito";
    
    public static final String REMOVE_FROM_CART_TITLE = "Carrito de compras";
    public static final String REMOVE_FROM_CART_SUCCESS_MESSAGE = "Producto eliminado del carrito";
    public static final String REMOVE_FROM_CART_ERROR_MESSAGE = "No se pudo eliminar el producto del carrito";
}