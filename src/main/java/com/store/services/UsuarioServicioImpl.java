package com.store.services;

import com.store.models.Usuario;
import com.store.repository.UsuarioRepositorioJson;
import com.store.utils.PasswordUtils;
import java.util.List;

/**
 * Implementación concreta de {@link IUsuarioServicio} que gestiona usuarios
 * utilizando un repositorio JSON como almacenamiento.
 */
public class UsuarioServicioImpl implements IUsuarioServicio {

    private final UsuarioRepositorioJson usuarioRepositorio;

    /**
     * Constructor que inicializa el repositorio JSON.
     */
    public UsuarioServicioImpl() {
        this.usuarioRepositorio = new UsuarioRepositorioJson();
    }

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

    @Override
    public boolean eliminarUsuario(int userId) {
        return usuarioRepositorio.eliminarUsuario(userId);
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepositorio.obtenerUsuarioPorId(id);
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepositorio.obtenerUsuarioPorEmail(email);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.obtenerUsuarios();
    }

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
     * @return Nuevo ID disponible
     */
    private int generarNuevoId() {
        List<Usuario> usuarios = usuarioRepositorio.obtenerUsuarios();
        return usuarios.stream()
                .mapToInt(Usuario::getId)
                .max()
                .orElse(0) + 1;
    }
}