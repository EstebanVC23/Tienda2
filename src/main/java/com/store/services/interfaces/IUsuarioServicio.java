package com.store.services.interfaces;

import com.store.models.Usuario;
import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para la gestión de usuarios.
 * Proporciona métodos para autenticación, CRUD y gestión de estados.
 */
public interface IUsuarioServicio {

    /**
     * Valida las credenciales de un usuario.
     * @param usuario Usuario con email y contraseña a validar
     * @return true si las credenciales son válidas y el usuario está activo, false en caso contrario
     */
    boolean validarUsuario(Usuario usuario);

    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario Datos del usuario a registrar
     * @return true si el registro fue exitoso, false si falló la validación o el usuario ya existe
     */
    boolean crearUsuario(Usuario usuario);

    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario Usuario con datos actualizados
     * @return true si la actualización fue exitosa, false si el usuario no existe o hay conflictos
     */
    boolean actualizarUsuario(Usuario usuario);

    /**
     * Elimina un usuario del sistema.
     * @param userId ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el usuario
     */
    boolean eliminarUsuario(int userId);

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     */
    Usuario obtenerUsuarioPorId(int id);

    /**
     * Obtiene un usuario por su email.
     * @param email Email del usuario
     * @return Usuario encontrado o null si no existe
     */
    Usuario obtenerUsuarioPorEmail(String email);

    /**
     * Obtiene todos los usuarios registrados.
     * @return Lista de usuarios (vacía si no hay registros)
     */
    List<Usuario> listarUsuarios();

    /**
     * Cambia el estado activo/inactivo de un usuario.
     * @param userId ID del usuario
     * @param activo true para activar, false para desactivar
     * @return true si la operación fue exitosa, false si no se encontró el usuario
     */
    boolean cambiarEstadoUsuario(int userId, boolean activo);
}