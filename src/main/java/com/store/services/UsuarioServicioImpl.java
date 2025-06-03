package com.store.services;

import com.store.models.Usuario;
import com.store.repository.UsuarioRepositorioJson;
import com.store.services.interfaces.IUsuarioServicio;
import com.store.utils.PasswordUtils;
import java.util.List;

/**
 * Implementación concreta de {@link IUsuarioServicio} que gestiona usuarios
 * utilizando un repositorio JSON como almacenamiento.
 * Proporciona operaciones CRUD para usuarios, validación de credenciales
 * y gestión de estados de cuentas.
 */
public class UsuarioServicioImpl implements IUsuarioServicio {

    private final UsuarioRepositorioJson usuarioRepositorio;

    /**
     * Constructor que inicializa el servicio con un repositorio JSON.
     */
    public UsuarioServicioImpl() {
        this.usuarioRepositorio = new UsuarioRepositorioJson();
    }

    /**
     * Valida las credenciales de un usuario.
     * 
     * @param usuario Objeto usuario con email y password a validar
     * @return true si las credenciales son válidas y el usuario está activo, false en caso contrario
     */
    @Override
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
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuario Objeto usuario a crear
     * @return true si el usuario fue creado exitosamente, false si los datos son inválidos o el email ya existe
     */
    @Override
    public boolean crearUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
            usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return false;
        }

        if (usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail()) != null) {
            return false;
        }

        if (!PasswordUtils.isHashed(usuario.getPassword())) {
            usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        }

        if (usuario.getId() == 0) {
            usuario.setId(generarNuevoId());
        }
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }
        if (!usuario.getEstado()) {
            usuario.setEstadoActivo(true);
        }

        usuarioRepositorio.agregarCuenta(usuario);
        return true;
    }

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param usuario Objeto usuario con los datos actualizados
     * @return true si el usuario fue actualizado exitosamente, false si el usuario no existe o el email ya está en uso
     */
    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        Usuario usuarioExistente = usuarioRepositorio.obtenerUsuarioPorId(usuario.getId());
        if (usuarioExistente == null) {
            return false;
        }

        Usuario usuarioConEmail = usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail());
        if (usuarioConEmail != null && usuarioConEmail.getId() != usuario.getId()) {
            return false;
        }

        if (!usuario.getPassword().equals(usuarioExistente.getPassword())) {
            usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        }

        return usuarioRepositorio.actualizarUsuario(usuario);
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param userId ID del usuario a eliminar
     * @return true si el usuario fue eliminado exitosamente, false si no se encontró el usuario
     */
    @Override
    public boolean eliminarUsuario(int userId) {
        return usuarioRepositorio.eliminarUsuario(userId);
    }

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id ID del usuario a buscar
     * @return El usuario encontrado o null si no existe
     */
    @Override
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepositorio.obtenerUsuarioPorId(id);
    }

    /**
     * Obtiene un usuario por su email.
     * 
     * @param email Email del usuario a buscar
     * @return El usuario encontrado o null si no existe
     */
    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepositorio.obtenerUsuarioPorEmail(email);
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * 
     * @return Lista de todos los usuarios
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.obtenerUsuarios();
    }

    /**
     * Cambia el estado activo/inactivo de un usuario.
     * 
     * @param userId ID del usuario a modificar
     * @param activo Nuevo estado del usuario (true = activo, false = inactivo)
     * @return true si el estado fue cambiado exitosamente, false si no se encontró el usuario
     */
    @Override
    public boolean cambiarEstadoUsuario(int userId, boolean activo) {
        Usuario usuario = usuarioRepositorio.obtenerUsuarioPorId(userId);
        if (usuario == null) {
            return false;
        }
        usuario.setEstadoActivo(activo);
        return usuarioRepositorio.actualizarUsuario(usuario);
    }

    /**
     * Genera un nuevo ID autoincremental basado en el máximo ID existente.
     * 
     * @return Nuevo ID disponible (máximo ID existente + 1)
     */
    private int generarNuevoId() {
        List<Usuario> usuarios = usuarioRepositorio.obtenerUsuarios();
        return usuarios.stream()
                .mapToInt(Usuario::getId)
                .max()
                .orElse(0) + 1;
    }
}