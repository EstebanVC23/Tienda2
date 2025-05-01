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

public class UsuarioRegistrador {
    // Ruta del archivo JSON (constante)
    private static final String FILE_PATH = "src/main/resources/json/usuarios.json";
    
    // CONSTANTES con los datos del usuario a registrar
    private static final int ID = 1;
    private static final String NOMBRE = "Ana";
    private static final String APELLIDO = "García";
    private static final String EMAIL = "esvca";
    private static final String PASSWORD = "123"; // Se hasheará al guardar
    private static final String ROL = "USER";
    private static final boolean ESTADO_ACTIVO = true;
    
    public static void main(String[] args) {
        // Crear y registrar el usuario con las constantes definidas
        registrarUsuarioConstante();
        System.out.println("Usuario registrado exitosamente!");
    }
    
    public static void registrarUsuarioConstante() {
        try {
            // Crear el usuario con las constantes
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setId(ID);
            nuevoUsuario.setNombre(NOMBRE);
            nuevoUsuario.setApellido(APELLIDO);
            nuevoUsuario.setEmail(EMAIL);
            nuevoUsuario.setPassword(PASSWORD);
            nuevoUsuario.setRol(ROL);
            nuevoUsuario.setEstadoActivo(ESTADO_ACTIVO);
            
            // Registrar el usuario
            registrarUsuario(nuevoUsuario);
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }
    
    public static void registrarUsuario(Usuario usuario) throws IOException {
        // 1. Cargar usuarios existentes
        List<Usuario> usuarios = cargarUsuarios();
        
        // 2. Verificar si el usuario ya existe
        if (existeUsuario(usuarios, usuario.getEmail())) {
            System.out.println("Error: El usuario con email " + usuario.getEmail() + " ya existe");
            return;
        }
        
        // 3. Hashear la contraseña antes de guardar
        usuario.setPassword(PasswordUtils.encrypt(usuario.getPassword()));
        
        // 4. Agregar el nuevo usuario
        usuarios.add(usuario);
        
        // 5. Guardar en el archivo
        guardarUsuarios(usuarios);
    }
    
    private static List<Usuario> cargarUsuarios() throws IOException {
        String jsonString = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        List<Usuario> usuarios = new Gson().fromJson(jsonString, new TypeToken<List<Usuario>>(){}.getType());
        return usuarios != null ? usuarios : new ArrayList<>();
    }
    
    private static void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(usuarios, writer);
        }
    }
    
    private static boolean existeUsuario(List<Usuario> usuarios, String email) {
        return usuarios.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}