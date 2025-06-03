 package com.store.services;

import com.store.models.Sale;
import com.store.models.SaleItem;
import com.store.models.Usuario;
import com.store.repository.SaleRepositorioJson;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de ventas que gestiona operaciones CRUD y consultas
 * sobre ventas utilizando un repositorio JSON como almacenamiento.
 */
public class SaleServiceImpl implements ISaleService {

    private final SaleRepositorioJson saleRepositorio;
    private final IUsuarioServicio usuarioServicio = new UsuarioServicioImpl();

    /**
     * Constructor que inicializa el servicio con un repositorio JSON.
     */
    public SaleServiceImpl() {
        this.saleRepositorio = new SaleRepositorioJson();
    }

    /**
     * Genera un nuevo ID único para una venta basado en el ID más alto existente.
     * 
     * @return El nuevo ID generado (máximo ID existente + 1)
     */
    private int generarNuevoId() {
        List<Sale> ventas = saleRepositorio.obtenerVentas();
        return ventas.stream()
                .mapToInt(Sale::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Busca las ventas asociadas a un cliente específico por su ID.
     * 
     * @param userId ID del cliente cuyas ventas se desean buscar
     * @return Lista de ventas asociadas al cliente, lista vacía si no se encuentran ventas
     */
    @Override
    public List<Sale> buscarVentasPorCliente(int userId) {
        return saleRepositorio.obtenerVentas().stream()
                .filter(venta -> venta.getCustomerId() == userId)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva venta en el sistema.
     * 
     * @param sale La venta a crear. Si no tiene ID (id = 0), se genera uno automáticamente
     * @return true si la venta fue creada exitosamente, false si ya existe una venta con el mismo ID
     */
    @Override
    public boolean crearVenta(Sale sale) {
        if (sale.getId() == 0) {
            sale.setId(generarNuevoId());
        } else if (saleRepositorio.existeIdVenta(sale.getId())) {
            System.out.println("Ya existe una venta con el ID: " + sale.getId());
            return false;
        }
        saleRepositorio.agregarVenta(sale);
        return true;
    }

    /**
     * Actualiza los datos de una venta existente.
     * 
     * @param sale La venta con los datos actualizados
     * @return true si la venta fue actualizada exitosamente, false si no se encontró la venta
     */
    @Override
    public boolean actualizarVenta(Sale sale) {
        return saleRepositorio.actualizarVenta(sale);
    }

    /**
     * Elimina una venta del sistema.
     * 
     * @param id ID de la venta a eliminar
     * @return true si la venta fue eliminada exitosamente, false si no se encontró la venta
     */
    @Override
    public boolean eliminarVenta(int id) {
        return saleRepositorio.eliminarVenta(id);
    }

    /**
     * Obtiene una venta por su ID único.
     * 
     * @param id ID de la venta a buscar
     * @return La venta encontrada o null si no existe
     */
    @Override
    public Sale obtenerVentaPorId(int id) {
        return saleRepositorio.obtenerVentaPorId(id);
    }

    /**
     * Obtiene todas las ventas registradas en el sistema.
     * 
     * @return Lista de todas las ventas
     */
    @Override
    public List<Sale> listarVentas() {
        return saleRepositorio.obtenerVentas();
    }

    /**
     * Busca ventas asociadas a clientes cuyo nombre coincida con el parámetro.
     * 
     * @param nombreCliente Nombre o parte del nombre del cliente a buscar (no case sensitive)
     * @return Lista de ventas asociadas a clientes que coinciden con el criterio de búsqueda
     */
    @Override
    public List<Sale> buscarVentasPorCliente(String nombreCliente) {
        String nombreLower = nombreCliente.toLowerCase();

        // Obtener todos los usuarios que contengan el nombre buscado
        List<Usuario> usuariosCoincidentes = usuarioServicio.listarUsuarios().stream()
            .filter(u -> u.getNombre() != null && u.getNombre().toLowerCase().contains(nombreLower))
            .collect(Collectors.toList());

        if (usuariosCoincidentes.isEmpty()) {
            return Collections.emptyList();
        }

        // Obtener lista de IDs de esos usuarios
        Set<Integer> idsUsuarios = usuariosCoincidentes.stream()
            .map(Usuario::getId)
            .collect(Collectors.toSet());

        // Filtrar ventas que tengan customerId en la lista de IDs encontrados
        return saleRepositorio.obtenerVentas().stream()
            .filter(venta -> idsUsuarios.contains(venta.getCustomerId()))
            .collect(Collectors.toList());
    }

    /**
     * Busca ventas realizadas dentro de un rango de fechas específico.
     * 
     * @param fechaInicio Fecha de inicio del rango (inclusive)
     * @param fechaFin Fecha de fin del rango (inclusive)
     * @return Lista de ventas realizadas dentro del rango especificado
     */
    @Override
    public List<Sale> buscarVentasPorFecha(Date fechaInicio, Date fechaFin) {
        return saleRepositorio.obtenerVentas().stream()
                .filter(v -> {
                    Date fechaVenta = v.getDate();
                    return fechaVenta != null && !fechaVenta.before(fechaInicio) && !fechaVenta.after(fechaFin);
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los items (productos) asociados a una venta específica.
     * 
     * @param saleId ID de la venta cuyos items se desean obtener
     * @return Lista de items de la venta, lista vacía si no se encuentra la venta
     */
    @Override
    public List<SaleItem> obtenerItemsVenta(int saleId) {
        Sale venta = saleRepositorio.obtenerVentaPorId(saleId);
        return venta != null ? venta.getItems() : Collections.emptyList();
    }
}