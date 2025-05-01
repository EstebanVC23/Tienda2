package com.store.utils;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class JsonProcessor {
    public static void main(String[] args) {
        String email = "root";
        String password = "123";
        String filePath = "src/main/resources/json/usuarios.json";
        
        // Verificar si el directorio existe, si no, crearlo
        try {
            Path directoryPath = Paths.get(filePath).getParent();
            if (directoryPath != null && !Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                System.out.println("Se ha creado el directorio: " + directoryPath);
            }
        } catch (IOException e) {
            System.err.println("Error al crear el directorio: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        JsonArray jsonArray;
        
        // Verificar si el archivo existe
        if (Files.exists(Paths.get(filePath))) {
            try {
                // Leer el archivo JSON existente
                String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
                JsonElement jsonElement = JsonParser.parseString(jsonString);
                
                if (jsonElement.isJsonArray()) {
                    jsonArray = jsonElement.getAsJsonArray();
                } else {
                    System.out.println("El archivo JSON no contiene un array válido. Creando uno nuevo.");
                    jsonArray = new JsonArray();
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        } else {
            // Si el archivo no existe, crear un nuevo array JSON
            System.out.println("El archivo no existe. Creando un nuevo archivo JSON con un usuario root.");
            jsonArray = new JsonArray();
        }
        
        boolean rootUserFound = false;
        
        // Buscar el usuario con email "root"
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            
            if (jsonObject.has("email") && email.equals(jsonObject.get("email").getAsString())) {
                String encryptedPassword = PasswordUtils.encrypt(password); // Encriptar la contraseña "123"
                jsonObject.addProperty("password", encryptedPassword);
                rootUserFound = true;
                System.out.println("Se ha cambiado la contraseña del usuario root a '123' (hasheada).");
                break;
            }
        }
        
        // Si no se encontró el usuario root, crearlo
        if (!rootUserFound) {
            System.out.println("No se encontró ningún usuario con email 'root'. Creando uno nuevo.");
            JsonObject rootUser = new JsonObject();
            rootUser.addProperty("id", 999); // ID especial
            rootUser.addProperty("nombre", "Administrador");
            rootUser.addProperty("apellido", "Sistema");
            rootUser.addProperty("email", "root");
            rootUser.addProperty("tipoDocumento", "DNI");
            rootUser.addProperty("numeroDocumento", "00000000");
            rootUser.addProperty("direccion", "Dirección Administrativa");
            rootUser.addProperty("telefono", "+000000000");
            rootUser.addProperty("password", PasswordUtils.encrypt("123")); // Encriptar la contraseña "123"
            rootUser.addProperty("rol", "ADMIN");
            rootUser.addProperty("estadoActivo", true);
            
            // Agregar el usuario root al array
            jsonArray.add(rootUser);
        }
        
        // Guardar los cambios en el archivo JSON
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
            System.out.println("Archivo JSON guardado correctamente en: " + filePath);
            
            // Mostrar la ruta absoluta para referencia
            Path absolutePath = Paths.get(filePath).toAbsolutePath();
            System.out.println("Ruta absoluta del archivo: " + absolutePath);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}