package com.store.view.panels.profile;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.utils.PasswordUtils;

public class UserProfileController {
    private final UsuarioServicio usuarioServicio;
    private Usuario usuario;

    public UserProfileController(UsuarioServicio usuarioServicio, Usuario usuario) {
        this.usuarioServicio = usuarioServicio;
        this.usuario = usuario;
    }

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
        usuarioActualizado.setDireccion(direccion); // Actualizar dirección
        
        boolean resultado = usuarioServicio.actualizarUsuario(usuarioActualizado);
        if(resultado) {
            usuario = usuarioActualizado;
        }
        return resultado;
    }

    public boolean cambiarContraseña(String contraseñaActual, String nuevaContraseña) {
        if(!PasswordUtils.verify(contraseñaActual, usuario.getPassword())) {
            return false;
        }
        
        usuario.setPassword(PasswordUtils.encrypt(nuevaContraseña));
        return usuarioServicio.actualizarUsuario(usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

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