package com.store.services;

import com.store.models.Usuario;
import com.store.repository.UsuarioRepositorioJson;
import com.store.utils.PasswordUtils;
import java.util.List;

/**
 * Clase UsuarioServicio.
 * 
 * Gestiona usuarios en el archivo usuarios.json, incluyendo validación,
 * registro, actualización, eliminación y recuperación de usuarios.
 */
public class UsuarioServicio {
    private UsuarioRepositorioJson usuarioRepositorio;

    /** 
     * Constructor de UsuarioServicio. 
     * Inicializa el repositorio de usuarios basado en JSON. 
     */
    public UsuarioServicio() {
        this.usuarioRepositorio = new UsuarioRepositorioJson();
    }

    public boolean validarUsuario(Usuario usuario) {
        Usuario usuarioEncontrado = usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail());
        return usuarioEncontrado != null 
                && PasswordUtils.verify(usuario.getPassword(), usuarioEncontrado.getPassword()) 
                && usuarioEncontrado.isEstadoActivo();
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail()) != null) {
            System.out.println("El usuario con el correo " + usuario.getEmail() + " ya está registrado.");
            return false;
        }

        // Encriptar la contraseña antes de almacenar el usuario
        usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));

        usuarioRepositorio.agregarCuenta(usuario);
        return true;
    }

    public boolean crearUsuario(Usuario usuario) {
        if (usuario.getId() == 0) {
            usuario.setId(generarNuevoId());
        }
        if (usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail()) != null) {
            System.out.println("El usuario con el correo " + usuario.getEmail() + " ya está registrado.");
            return false;
        }

        // Encriptar la contraseña antes de crear el usuario
        usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));

        usuarioRepositorio.agregarCuenta(usuario);
        System.out.println("Usuario creado exitosamente: " + usuario.getNombre());
        return true;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepositorio.obtenerUsuarioPorId(usuario.getId());
        if (usuarioExistente == null) {
            System.out.println("El usuario con ID " + usuario.getId() + " no existe.");
            return false;
        }

        Usuario usuarioPorEmail = usuarioRepositorio.obtenerUsuarioPorEmail(usuario.getEmail());
        if (usuarioPorEmail != null && usuarioPorEmail.getId() != usuario.getId()) {
            System.out.println("El email " + usuario.getEmail() + " ya está en uso por otro usuario.");
            return false;
        }
    
        if (!usuario.getPassword().equals(usuarioExistente.getPassword())) {
            usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        }

        return usuarioRepositorio.actualizarUsuario(usuario);
    }

    public boolean eliminarUsuario(int userId) {
        return usuarioRepositorio.eliminarUsuario(userId);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepositorio.obtenerUsuarioPorId(id);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepositorio.obtenerUsuarioPorEmail(email);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.obtenerUsuarios();
    }

    private int generarNuevoId() {
        List<Usuario> usuarios = usuarioRepositorio.obtenerUsuarios();
        return usuarios.isEmpty() ? 1 : usuarios.stream().mapToInt(Usuario::getId).max().orElse(0) + 1;
    }
}