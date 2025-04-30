package com.store.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.store.models.Usuario;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UsuarioRepositorioJson {
    
    private static final String FILE_PATH = "src/main/resources/json/usuarios.json";
    private List<Usuario> usuarios;

    public UsuarioRepositorioJson() {
        this.usuarios = cargarUsuarios();
    }

    private List<Usuario> cargarUsuarios() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new Gson().fromJson(jsonString, new TypeToken<List<Usuario>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void guardarUsuarios() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public void agregarCuenta(Usuario newUsuario) {
        usuarios.add(newUsuario);
        guardarUsuarios();
    }

    public boolean actualizarUsuario(Usuario usuarioModificado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioModificado.getId()) {
                usuarios.set(i, usuarioModificado);
                guardarUsuarios();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarUsuario(int userId) {
        if (usuarios.removeIf(u -> u.getId() == userId)) {
            guardarUsuarios();
            return true;
        }
        return false;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarios;
    }

    public boolean inactivarUsuario(int userId) {
        Usuario usuario = obtenerUsuarioPorId(userId);
        if (usuario != null) {
            usuario.setEstadoActivo(false);
            guardarUsuarios();
            return true;
        }
        return false;
    }

    public boolean activarUsuario(int userId) {
        Usuario usuario = obtenerUsuarioPorId(userId);
        if (usuario != null) {
            usuario.setEstadoActivo(true);
            guardarUsuarios();
            return true;
        }
        return false;
    }
}