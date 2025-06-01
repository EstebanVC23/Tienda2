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

public class SaleRepositorioJson {

    private static final String FILE_PATH = "src/main/resources/json/sales.json";
    private List<Sale> ventas;
    private Gson gson;

    public SaleRepositorioJson() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        ventas = cargarVentas();
    }

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

    private void guardarVentas() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(ventas, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar ventas: " + e.getMessage());
        }
    }

    public boolean existeIdVenta(int id) {
        return ventas.stream().anyMatch(v -> v.getId() == id);
    }

    public void agregarVenta(Sale sale) {
        ventas.add(sale);
        guardarVentas();
    }

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

    public boolean eliminarVenta(int id) {
        boolean removed = ventas.removeIf(v -> v.getId() == id);
        if (removed) {
            guardarVentas();
        }
        return removed;
    }

    public Sale obtenerVentaPorId(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Sale> obtenerVentas() {
        return new ArrayList<>(ventas);
    }
}
