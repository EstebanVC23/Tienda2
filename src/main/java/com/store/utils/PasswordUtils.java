package com.store.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Clase utilitaria para el manejo seguro de contraseñas.
 * Proporciona métodos para encriptar, verificar y validar contraseñas usando hashing con salt.
 */
public class PasswordUtils {
    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "SHA-256";
    
    /**
     * Encripta una contraseña usando salt y algoritmo SHA-256.
     * @param password Contraseña a encriptar
     * @return String con la contraseña encriptada en formato Base64 (salt + hash)
     * @throws RuntimeException Si el algoritmo de hash no está disponible
     */
    public static String encrypt(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(salt);
            byte[] hashedPassword = digest.digest(password.getBytes());
            
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);
            
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }
    
    /**
     * Verifica si una contraseña coincide con un hash almacenado.
     * @param password Contraseña a verificar
     * @param storedHash Hash almacenado para comparación
     * @return true si la contraseña coincide, false en caso contrario o si hay error
     */
    public static boolean verify(String password, String storedHash) {
        if (password == null || storedHash == null || storedHash.isEmpty()) {
            return false;
        }

        try {
            byte[] combined = Base64.getDecoder().decode(storedHash);
            
            if (combined.length <= SALT_LENGTH) {
                System.err.println("[ERROR] Hash almacenado es inválido");
                return false;
            }
            
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);
            
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(salt);
            byte[] testHash = digest.digest(password.getBytes());
            
            for (int i = 0; i < testHash.length; i++) {
                if (testHash[i] != combined[SALT_LENGTH + i]) {
                    return false;
                }
            }
            return true;
        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            System.err.println("Error al verificar contraseña: " + e.getMessage());
            return false;
        }
    }

    /**
     * Determina si un string parece ser un hash de contraseña válido.
     * @param password String a evaluar
     * @return true si el string parece ser un hash válido, false en caso contrario
     */
    public static boolean isHashed(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(password);
            return decoded.length > SALT_LENGTH;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Método de prueba para demostrar la funcionalidad de la clase.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        String password = "123";
        String wrongPassword = "123";
        
        String encrypted = encrypt(password);
        System.out.println("Contraseña encriptada: " + encrypted);
        
        boolean result1 = verify(password, encrypted);
        System.out.println("Verificación con contraseña correcta: " + result1);
        
        boolean result2 = verify(wrongPassword, encrypted);
        System.out.println("Verificación con contraseña incorrecta: " + result2);
        
        boolean result3 = verify(password, "");
        System.out.println("Verificación con hash vacío: " + result3);
    }
}