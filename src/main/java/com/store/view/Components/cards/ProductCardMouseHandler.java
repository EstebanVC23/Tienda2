package com.store.view.components.cards;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Manejador de eventos de ratón para las tarjetas de producto.
 * Controla las interacciones como clics y efectos hover.
 */
public class ProductCardMouseHandler extends MouseAdapter {
    private final ProductCard card;

    /**
     * Crea un nuevo manejador de eventos para la tarjeta especificada.
     * @param card Tarjeta de producto a la que se asociarán los eventos.
     */
    public ProductCardMouseHandler(ProductCard card) {
        this.card = card;
    }

    /**
     * Maneja el evento de clic del ratón, mostrando los detalles del producto.
     * @param e Evento de ratón que disparó la acción.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        card.showProductDetails();
    }
    
    /**
     * Maneja el evento de entrada del ratón, activando el efecto hover.
     * @param e Evento de ratón que disparó la acción.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        card.setHoverEffect(true);
    }
    
    /**
     * Maneja el evento de salida del ratón, desactivando el efecto hover.
     * @param e Evento de ratón que disparó la acción.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        card.setHoverEffect(false);
    }
}