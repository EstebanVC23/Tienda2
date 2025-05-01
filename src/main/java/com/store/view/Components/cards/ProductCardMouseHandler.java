package com.store.view.components.cards;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductCardMouseHandler extends MouseAdapter {
    private final ProductCard card;

    public ProductCardMouseHandler(ProductCard card) {
        this.card = card;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        card.showProductDetails();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        card.setHoverEffect(true);
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        card.setHoverEffect(false);
    }
}