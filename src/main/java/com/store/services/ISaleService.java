package com.store.services;

import com.store.models.Sale;
import com.store.models.SaleItem;

import java.util.Date;
import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para la gestión de ventas.
 * Proporciona métodos para CRUD y consultas básicas sobre ventas.
 */
public interface ISaleService {

    /**
     * Crea una nueva venta en el sistema.
     * @param sale Venta a crear (si no tiene id, se puede generar automáticamente)
     * @return true si se creó exitosamente, false si ya existe una venta con el mismo id
     */
    boolean crearVenta(Sale sale);

    /**
     * Actualiza una venta existente.
     * @param sale Venta con datos actualizados
     * @return true si se actualizó correctamente, false si la venta no existe
     */
    boolean actualizarVenta(Sale sale);

    /**
     * Elimina una venta por su id.
     * @param id Identificador de la venta a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la venta
     */
    boolean eliminarVenta(int id);

    /**
     * Obtiene una venta por su id único.
     * @param id Id de la venta a buscar
     * @return Venta encontrada o null si no existe
     */
    Sale obtenerVentaPorId(int id);

    /**
     * Obtiene todas las ventas registradas.
     * @return Lista de ventas (vacía si no hay registros)
     */
    List<Sale> listarVentas();

    /**
     * Busca ventas asociadas a un cliente específico (por nombre o id).
     * @param cliente Identificador o nombre del cliente
     * @return Lista de ventas relacionadas con el cliente
     */
    List<Sale> buscarVentasPorCliente(String cliente);

    /**
     * Busca ventas realizadas dentro de un rango de fechas.
     * @param fechaInicio Fecha de inicio (inclusive)
     * @param fechaFin Fecha de fin (inclusive)
     * @return Lista de ventas en el rango de fechas especificado
     */
    List<Sale> buscarVentasPorFecha(Date fechaInicio, Date fechaFin);

    /**
     * Obtiene los artículos vendidos en una venta específica.
     * @param saleId Id de la venta para la cual se desean los artículos
     * @return Lista de artículos vendidos en esa venta
     */
    List<SaleItem> obtenerItemsVenta(int saleId);
}
