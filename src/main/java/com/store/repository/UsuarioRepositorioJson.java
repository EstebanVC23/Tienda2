package com.store.repository;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.store.models.Usuario;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Repositorio para gestionar usuarios almacenados en formato JSON.
 * Proporciona métodos para cargar, guardar, agregar, actualizar, eliminar y buscar usuarios.
 */
public class UsuarioRepositorioJson {
    
    private static final String FILE_PATH = "src/main/resources/json/usuarios.json";
    private List<Usuario> usuarios;

    /**
     * Constructor que inicializa el repositorio cargando los usuarios desde el archivo JSON.
     */
    public UsuarioRepositorioJson() {
        this.usuarios = cargarUsuarios();
    }

    /**
     * Carga los usuarios desde el archivo JSON.
     * @return Lista de usuarios cargados, o lista vacía si ocurre un error.
     */
    private List<Usuario> cargarUsuarios() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new Gson().fromJson(jsonString, new TypeToken<List<Usuario>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda los usuarios actuales en el archivo JSON.
     */
    private void guardarUsuarios() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo usuario al repositorio y guarda los cambios.
     * @param newUsuario Usuario a agregar.
     */
    public void agregarCuenta(Usuario newUsuario) {
        usuarios.add(newUsuario);
        guardarUsuarios();
    }

    /**
     * Actualiza un usuario existente en el repositorio.
     * @param usuarioModificado Usuario con los datos actualizados.
     * @return true si se actualizó correctamente, false si no se encontró el usuario.
     */
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

    /**
     * Elimina un usuario del repositorio.
     * @param userId ID del usuario a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró el usuario.
     */
    public boolean eliminarUsuario(int userId) {
        if (usuarios.removeIf(u -> u.getId() == userId)) {
            guardarUsuarios();
            return true;
        }
        return false;
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene un usuario por su email.
     * @param email Email del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los usuarios del repositorio.
     * @return Lista completa de usuarios.
     */
    public List<Usuario> obtenerUsuarios() {
        return usuarios;
    }

    /**
     * Inactiva un usuario cambiando su estado a inactivo.
     * @param userId ID del usuario a inactivar.
     * @return true si se inactivó correctamente, false si no se encontró el usuario.
     */
    public boolean inactivarUsuario(int userId) {
        Usuario usuario = obtenerUsuarioPorId(userId);
        if (usuario != null) {
            usuario.setEstadoActivo(false);
            guardarUsuarios();
            return true;
        }
        return false;
    }

    /**
     * Activa un usuario cambiando su estado a activo.
     * @param userId ID del usuario a activar.
     * @return true si se activó correctamente, false si no se encontró el usuario.
     */
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