package com.store.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.store.models.Sale;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para manejar operaciones CRUD de ventas en formato JSON.
 * Permite cargar, guardar, agregar, actualizar, eliminar y consultar ventas.
 */
public class SaleRepositorioJson {

    private static final String FILE_PATH = "src/main/resources/json/sales.json";
    private List<Sale> ventas;
    private Gson gson;

    /**
     * Constructor que inicializa el repositorio.
     * Configura el objeto Gson y carga las ventas desde el archivo JSON.
     */
    public SaleRepositorioJson() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        ventas = cargarVentas();
    }

    /**
     * Carga las ventas desde el archivo JSON.
     * 
     * @return Lista de ventas cargadas desde el archivo. Si el archivo no existe o está vacío,
     *         retorna una lista vacía.
     */
    private List<Sale> cargarVentas() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            List<Sale> loadedSales = gson.fromJson(jsonString, new TypeToken<List<Sale>>() {}.getType());
            return loadedSales != null ? loadedSales : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar ventas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista actual de ventas en el archivo JSON.
     */
    private void guardarVentas() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(ventas, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar ventas: " + e.getMessage());
        }
    }

    /**
     * Verifica si existe una venta con el ID especificado.
     * 
     * @param id El ID de la venta a verificar.
     * @return true si existe una venta con el ID, false en caso contrario.
     */
    public boolean existeIdVenta(int id) {
        return ventas.stream().anyMatch(v -> v.getId() == id);
    }

    /**
     * Agrega una nueva venta al repositorio y guarda los cambios en el archivo.
     * 
     * @param sale La venta a agregar.
     */
    public void agregarVenta(Sale sale) {
        ventas.add(sale);
        guardarVentas();
    }

    /**
     * Actualiza una venta existente en el repositorio.
     * 
     * @param sale La venta con los datos actualizados.
     * @return true si la venta fue encontrada y actualizada, false en caso contrario.
     */
    public boolean actualizarVenta(Sale sale) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getId() == sale.getId()) {
                ventas.set(i, sale);
                guardarVentas();
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina una venta del repositorio.
     * 
     * @param id El ID de la venta a eliminar.
     * @return true si la venta fue encontrada y eliminada, false en caso contrario.
     */
    public boolean eliminarVenta(int id) {
        boolean removed = ventas.removeIf(v -> v.getId() == id);
        if (removed) {
            guardarVentas();
        }
        return removed;
    }

    /**
     * Obtiene una venta por su ID.
     * 
     * @param id El ID de la venta a buscar.
     * @return La venta encontrada o null si no existe.
     */
    public Sale obtenerVentaPorId(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todas las ventas del repositorio.
     * 
     * @return Una lista con todas las ventas. La lista devuelta es una copia para
     *         evitar modificaciones externas no controladas.
     */
    public List<Sale> obtenerVentas() {
        return new ArrayList<>(ventas);
    }
}