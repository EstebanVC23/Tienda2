package com.store.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Sale {
    private int id;
    private Date date;
    private int customerId;
    private List<SaleItem> items;
    private double total;
    private SaleStatus status;

    /**
     * Constructor por defecto que inicializa una venta vacía.
     * Este constructor es útil para crear una nueva venta
     * sin necesidad de proporcionar todos los detalles inmediatamente.
     */
    public Sale() {
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.status = SaleStatus.PENDING;
    }

    /**
     * Constructor que permite crear una venta con todos los detalles necesarios.
     * @param id Identificador único de la venta
     * @param date Fecha de la venta
     * @param customerId Identificador del cliente asociado a la venta
     * @param items Lista de artículos vendidos en esta venta
     * @param status Estado actual de la venta (PENDING, COMPLETED, CANCELLED)
     */
    public Sale(int id, Date date, int customerId, List<SaleItem> items, SaleStatus status) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.items = items != null ? items : new ArrayList<>();
        this.status = status != null ? status : SaleStatus.PENDING;
        this.total = calculateTotal();
    }

    /**
     * Calcula el total de la venta sumando el precio de cada artículo multiplicado por su cantidad.
     * Este método se invoca al establecer los artículos o al crear una nueva venta.
     * @return El total calculado de la venta.
     */
    private double calculateTotal() {
        double sum = 0.0;
        if (items != null) {
            for (SaleItem item : items) {
                sum += item.getProduct().getPrecio() * item.getQuantity();
            }
        }
        return sum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public List<SaleItem> getItems() { return items; }
    public void setItems(List<SaleItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        this.total = calculateTotal();
    }

    public double getTotal() { return total; }

    public SaleStatus getStatus() { return status; }
    public void setStatus(SaleStatus status) { this.status = status; }

    /**
     * Recalcula el total de la venta.
     */
    public void recalculateTotal() {
        this.total = calculateTotal();
    }
}
