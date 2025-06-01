package com.store.models;

public class SaleItem {
    private Producto product;
    private int quantity;

    public SaleItem(Producto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
