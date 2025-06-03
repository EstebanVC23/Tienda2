package com.store.models;

/**
 * Represents an item in a sale, containing a product and its quantity.
 */
public class SaleItem {
    /**
     * The product associated with this sale item.
     */
    private Producto product;

    /**
     * The quantity of the product in this sale item.
     */
    private int quantity;

    /**
     * Constructs a new SaleItem with the specified product and quantity.
     *
     * @param product the product associated with this sale item
     * @param quantity the quantity of the product
     */
    public SaleItem(Producto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Returns the product associated with this sale item.
     *
     * @return the product
     */
    public Producto getProduct() {
        return product;
    }

    /**
     * Sets the product for this sale item.
     *
     * @param product the product to set
     */
    public void setProduct(Producto product) {
        this.product = product;
    }

    /**
     * Returns the quantity of the product in this sale item.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in this sale item.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
