package com.store.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Representa una venta realizada en la tienda.
 * Contiene información sobre el identificador, fecha, cliente, artículos vendidos,
 * total de la venta y estado actual de la venta.
 */
public class Sale {
    /**
     * Identificador único de la venta.
     */
    private int id;

    /**
     * Fecha en la que se realizó la venta.
     */
    private Date date;

    /**
     * Identificador del cliente asociado a la venta.
     */
    private int customerId;

    /**
     * Lista de artículos vendidos en esta venta.
     */
    private List<SaleItem> items;

    /**
     * Total calculado de la venta.
     */
    private double total;

    /**
     * Estado actual de la venta.
     */
    private SaleStatus status;

    /**
     * Crea una nueva instancia de Sale con valores por defecto.
     * Inicializa la lista de artículos como vacía, el total en 0.0 y el estado como COMPLETED.
     */
    public Sale() {
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.status = SaleStatus.COMPLETED;
    }

    /**
     * Crea una nueva instancia de Sale con los detalles proporcionados.
     *
     * @param id Identificador único de la venta.
     * @param date Fecha de la venta.
     * @param customerId Identificador del cliente asociado a la venta.
     * @param items Lista de artículos vendidos en esta venta.
     * @param status Estado actual de la venta.
     */
    public Sale(int id, Date date, int customerId, List<SaleItem> items, SaleStatus status) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.items = items != null ? items : new ArrayList<>();
        this.status = status != null ? status : SaleStatus.COMPLETED;
        this.total = calculateTotal();
    }

    /**
     * Calcula el total de la venta sumando el precio de cada artículo multiplicado por su cantidad.
     *
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

    /**
     * Obtiene el identificador único de la venta.
     *
     * @return El identificador de la venta.
     */
    public int getId() { return id; }

    /**
     * Establece el identificador único de la venta.
     *
     * @param id El identificador de la venta.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtiene la fecha de la venta.
     *
     * @return La fecha de la venta.
     */
    public Date getDate() { return date; }

    /**
     * Establece la fecha de la venta.
     *
     * @param date La fecha de la venta.
     */
    public void setDate(Date date) { this.date = date; }

    /**
     * Obtiene el identificador del cliente asociado a la venta.
     *
     * @return El identificador del cliente.
     */
    public int getCustomerId() { return customerId; }

    /**
     * Establece el identificador del cliente asociado a la venta.
     *
     * @param customerId El identificador del cliente.
     */
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    /**
     * Obtiene la lista de artículos vendidos en esta venta.
     *
     * @return La lista de artículos de la venta.
     */
    public List<SaleItem> getItems() { return items; }

    /**
     * Establece la lista de artículos vendidos en esta venta y recalcula el total.
     *
     * @param items La lista de artículos de la venta.
     */
    public void setItems(List<SaleItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        this.total = calculateTotal();
    }

    /**
     * Obtiene el total calculado de la venta.
     *
     * @return El total de la venta.
     */
    public double getTotal() { return total; }

    /**
     * Obtiene el estado actual de la venta.
     *
     * @return El estado de la venta.
     */
    public SaleStatus getStatus() { return status; }

    /**
     * Establece el estado actual de la venta.
     *
     * @param status El estado de la venta.
     */
    public void setStatus(SaleStatus status) { this.status = status; }

    /**
     * Recalcula el total de la venta en función de los artículos actuales.
     */
    public void recalculateTotal() {
        this.total = calculateTotal();
    }
}
