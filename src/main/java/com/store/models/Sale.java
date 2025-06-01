package com.store.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Sale {
    private int id;
    private Date date;
    private int customerId;  // Cambié a customerId para clarificar que es un id
    private List<SaleItem> items;
    private double total;
    private SaleStatus status;

    // Constructor vacío inicializa lista y valores por defecto
    public Sale() {
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.status = SaleStatus.PENDING;
    }

    // Constructor completo
    public Sale(int id, Date date, int customerId, List<SaleItem> items, SaleStatus status) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.items = items != null ? items : new ArrayList<>();
        this.status = status != null ? status : SaleStatus.PENDING;
        this.total = calculateTotal();
    }

    // Método para recalcular el total basado en items
    private double calculateTotal() {
        double sum = 0.0;
        if (items != null) {
            for (SaleItem item : items) {
                sum += item.getProduct().getPrecio() * item.getQuantity();
            }
        }
        return sum;
    }

    // Getters y setters
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

    // Puedes agregar método para actualizar total si cambian items manualmente
    public void recalculateTotal() {
        this.total = calculateTotal();
    }
}
