package com.store.services;

import com.store.models.Usuario;
import com.store.repository.UsuarioRepositorioJson;
import com.store.utils.PasswordUtils;
import java.util.List;

/**
 * Servicio para gestionar operaciones relacionadas con usuarios
 */
public class UsuarioServicio {
    private final UsuarioRepositorioJson usuarioRepositorio;

    public UsuarioServicio() {
        this.usuarioRepositorio = new UsuarioRepositorioJson();
    }

    /**
     * Valida las credenciales de un usuario
     * @param usuario Objeto Usuario con email y password a validar
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            return false;
        }

        Usuario usuarioEncontrado = usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail().trim());
        return usuarioEncontrado != null 
                && usuarioEncontrado.getEstado()
                && PasswordUtils.verify(usuario.getPassword(), usuarioEncontrado.getPassword());
    }

    /**
     * Registra un nuevo usuario en el sistema
     * @param usuario Objeto Usuario con los datos del nuevo usuario
     * @return true si el registro fue exitoso, false si falló
     */
    public boolean crearUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        // Validar campos obligatorios
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
            usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return false;
        }

        // Verificar si el usuario ya existe
        if (usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail()) != null) {
            return false;
        }

        // Asegurar que la contraseña esté hasheada
        if (!PasswordUtils.isHashed(usuario.getPassword())) {
            usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        }

        // Asignar valores por defecto si no están establecidos
        if (usuario.getId() == 0) {
            usuario.setId(generarNuevoId());
        }
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }
        if (!usuario.getEstado()) {
            usuario.setEstadoActivo(true);
        }

        // Guardar el usuario
        usuarioRepositorio.agregarCuenta(usuario);
        return true;
    }

    /**
     * Actualiza los datos de un usuario existente
     * @param usuario Objeto Usuario con los datos actualizados
     * @return true si la actualización fue exitosa, false si falló
     */
    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        Usuario usuarioExistente = usuarioRepositorio.obtenerUsuarioPorId(usuario.getId());
        if (usuarioExistente == null) {
            return false;
        }

        // Verificar si el email ya está en uso por otro usuario
        Usuario usuarioConEmail = usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail());
        if (usuarioConEmail != null && usuarioConEmail.getId() != usuario.getId()) {
            return false;
        }

        // Si la contraseña cambió, hashearla
        if (!usuario.getPassword().equals(usuarioExistente.getPassword())) {
            usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        }

        return usuarioRepositorio.actualizarUsuario(usuario);
    }

    /**
     * Elimina un usuario del sistema
     * @param userId ID del usuario a eliminar
     * @return true si la eliminación fue exitosa, false si falló
     */
    public boolean eliminarUsuario(int userId) {
        return usuarioRepositorio.eliminarUsuario(userId);
    }

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Objeto Usuario o null si no se encuentra
     */
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepositorio.obtenerUsuarioPorId(id);
    }

    /**
     * Obtiene un usuario por su email
     * @param email Email del usuario
     * @return Objeto Usuario o null si no se encuentra
     */
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepositorio.obtenerUsuarioPorEmail(email);
    }

    /**
     * Obtiene la lista de todos los usuarios
     * @return Lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.obtenerUsuarios();
    }

    /**
     * Genera un nuevo ID para un usuario
     * @return Nuevo ID disponible
     */
    private int generarNuevoId() {
        List<Usuario> usuarios = usuarioRepositorio.obtenerUsuarios();
        return usuarios.stream()
                .mapToInt(Usuario::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Activa o desactiva un usuario
     * @param userId ID del usuario
     * @param activo true para activar, false para desactivar
     * @return true si la operación fue exitosa, false si falló
     */
    public boolean cambiarEstadoUsuario(int userId, boolean activo) {
        Usuario usuario = usuarioRepositorio.obtenerUsuarioPorId(userId);
        if (usuario == null) {
            return false;
        }

        usuario.setEstadoActivo(activo);
        return usuarioRepositorio.actualizarUsuario(usuario);
    }
}