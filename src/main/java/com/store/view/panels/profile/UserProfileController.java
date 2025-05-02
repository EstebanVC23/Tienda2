package com.store.view.panels.profile;

import com.store.models.Usuario;
import com.store.services.UsuarioServicioImpl;
import com.store.utils.PasswordUtils;

/**
 * Controlador para las operaciones relacionadas con el perfil de usuario.
 * Gestiona la actualización de datos del perfil y el cambio de contraseña,
 * actuando como intermediario entre la vista y el servicio de usuarios.
 */
public class UserProfileController {
    private final UsuarioServicioImpl usuarioServicio;
    private Usuario usuario;

    /**
     * Construye un nuevo controlador de perfil de usuario.
     * @param usuarioServicio Servicio para operaciones con usuarios
     * @param usuario Usuario actual cuyos datos se gestionarán
     */
    public UserProfileController(UsuarioServicioImpl usuarioServicio, Usuario usuario) {
        this.usuarioServicio = usuarioServicio;
        this.usuario = usuario;
    }

    /**
     * Actualiza los datos del perfil del usuario.
     * @param nombre Nuevo nombre del usuario
     * @param apellido Nuevo apellido del usuario
     * @param email Nuevo email del usuario
     * @param telefono Nuevo teléfono del usuario
     * @param tipoDocumento Nuevo tipo de documento del usuario
     * @param numeroDocumento Nuevo número de documento del usuario
     * @param direccion Nueva dirección del usuario
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarPerfil(String nombre, String apellido, String email,
                                   String telefono, String tipoDocumento,
                                   String numeroDocumento, String direccion) {
        Usuario usuarioActualizado = cloneUsuario(usuario);
        usuarioActualizado.setNombre(nombre);
        usuarioActualizado.setApellido(apellido);
        usuarioActualizado.setEmail(email);
        usuarioActualizado.setTelefono(telefono);
        usuarioActualizado.setTipoDocumento(tipoDocumento);
        usuarioActualizado.setNumeroDocumento(numeroDocumento);
        usuarioActualizado.setDireccion(direccion);
        
        boolean resultado = usuarioServicio.actualizarUsuario(usuarioActualizado);
        if(resultado) {
            usuario = usuarioActualizado;
        }
        return resultado;
    }

    /**
     * Cambia la contraseña del usuario verificando primero la contraseña actual.
     * @param contraseñaActual Contraseña actual del usuario para validación
     * @param nuevaContraseña Nueva contraseña a establecer
     * @return true si el cambio fue exitoso, false si la contraseña actual no coincide
     */
    public boolean cambiarContraseña(String contraseñaActual, String nuevaContraseña) {
        if(!PasswordUtils.verify(contraseñaActual, usuario.getPassword())) {
            return false;
        }
        
        usuario.setPassword(PasswordUtils.encrypt(nuevaContraseña));
        return usuarioServicio.actualizarUsuario(usuario);
    }

    /**
     * Obtiene el usuario actual gestionado por este controlador.
     * @return Objeto Usuario con los datos actuales
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Crea una copia del usuario original para evitar modificar el objeto directamente.
     * @param original Usuario original a clonar
     * @return Nueva instancia de Usuario con los mismos valores
     */
    private Usuario cloneUsuario(Usuario original) {
        Usuario clone = new Usuario();
        clone.setId(original.getId());
        clone.setNombre(original.getNombre());
        clone.setApellido(original.getApellido());
        clone.setEmail(original.getEmail());
        clone.setTelefono(original.getTelefono());
        clone.setTipoDocumento(original.getTipoDocumento());
        clone.setNumeroDocumento(original.getNumeroDocumento());
        clone.setDireccion(original.getDireccion());
        clone.setRol(original.getRol());
        clone.setEstadoActivo(original.getEstado());
        clone.setPassword(original.getPassword());
        return clone;
    }
}