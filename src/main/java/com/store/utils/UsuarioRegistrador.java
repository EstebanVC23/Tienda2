package com.store.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.store.models.Usuario;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para registrar usuarios iniciales en el sistema.
 * Permite registrar un usuario por defecto con valores constantes o usuarios personalizados.
 */
public class UsuarioRegistrador {
    
    /** Ruta del archivo JSON donde se almacenan los usuarios */
    private static final String FILE_PATH = "src/main/resources/json/usuarios.json";
    
    /** Constantes para el usuario por defecto */
    private static final int ID = 1;
    private static final String NOMBRE = "Ana";
    private static final String APELLIDO = "García";
    private static final String EMAIL = "esvca";
    private static final String PASSWORD = "123";
    private static final String ROL = "USER";
    private static final boolean ESTADO_ACTIVO = true;
    
    /**
     * Método principal para ejecutar el registro del usuario por defecto.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        registrarUsuarioConstante();
        System.out.println("Usuario registrado exitosamente!");
    }
    
    /**
     * Registra un usuario con los valores constantes definidos en la clase.
     */
    public static void registrarUsuarioConstante() {
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setId(ID);
            nuevoUsuario.setNombre(NOMBRE);
            nuevoUsuario.setApellido(APELLIDO);
            nuevoUsuario.setEmail(EMAIL);
            nuevoUsuario.setPassword(PASSWORD);
            nuevoUsuario.setRol(ROL);
            nuevoUsuario.setEstadoActivo(ESTADO_ACTIVO);
            
            registrarUsuario(nuevoUsuario);
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario Usuario a registrar
     * @throws IOException Si ocurre un error al acceder al archivo de usuarios
     */
    public static void registrarUsuario(Usuario usuario) throws IOException {
        List<Usuario> usuarios = cargarUsuarios();
        
        if (existeUsuario(usuarios, usuario.getEmail())) {
            System.out.println("Error: El usuario con email " + usuario.getEmail() + " ya existe");
            return;
        }
        
        usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        usuarios.add(usuario);
        guardarUsuarios(usuarios);
    }
    
    /**
     * Carga la lista de usuarios desde el archivo JSON.
     * @return Lista de usuarios registrados
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private static List<Usuario> cargarUsuarios() throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        List<Usuario> usuarios = new Gson().fromJson(jsonString, new TypeToken<List<Usuario>>(){}.getType());
        return usuarios != null ? usuarios : new ArrayList<>();
    }
    
    /**
     * Guarda la lista de usuarios en el archivo JSON.
     * @param usuarios Lista de usuarios a guardar
     * @throws IOException Si ocurre un error al escribir en el archivo
     */
    private static void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(usuarios, writer);
        }
    }
    
    /**
     * Verifica si ya existe un usuario con el email especificado.
     * @param usuarios Lista de usuarios donde buscar
     * @param email Email a verificar
     * @return true si el usuario existe, false en caso contrario
     */
    private static boolean existeUsuario(List<Usuario> usuarios, String email) {
        return usuarios.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}