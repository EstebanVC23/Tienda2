package com.store.services;

import com.store.models.Sale;
import com.store.models.Usuario;
import com.store.repository.SaleRepositorioJson;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SaleServiceImpl implements ISaleService {

    private final SaleRepositorioJson saleRepositorio;
    private final IUsuarioServicio usuarioServicio = new UsuarioServicioImpl();

    public SaleServiceImpl() {
        this.saleRepositorio = new SaleRepositorioJson();
    }

    private int generarNuevoId() {
        List<Sale> ventas = saleRepositorio.obtenerVentas();
        return ventas.stream()
                .mapToInt(Sale::getId)
                .max()
                .orElse(0) + 1;
    }

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

    @Override
    public boolean actualizarVenta(Sale sale) {
        return saleRepositorio.actualizarVenta(sale);
    }

    @Override
    public boolean eliminarVenta(int id) {
        return saleRepositorio.eliminarVenta(id);
    }

    @Override
    public Sale obtenerVentaPorId(int id) {
        return saleRepositorio.obtenerVentaPorId(id);
    }

    @Override
    public List<Sale> listarVentas() {
        return saleRepositorio.obtenerVentas();
    }

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


    @Override
    public List<Sale> buscarVentasPorFecha(Date fechaInicio, Date fechaFin) {
        return saleRepositorio.obtenerVentas().stream()
                .filter(v -> {
                    Date fechaVenta = v.getDate();
                    return fechaVenta != null && !fechaVenta.before(fechaInicio) && !fechaVenta.after(fechaFin);
                })
                .collect(Collectors.toList());
    }
}
