package com.store.utils;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class ChangePassword {
    public static void main(String[] args) {
        String email = "esvca";
        String password = "321";
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
            System.out.println("El archivo no existe. Creando un nuevo archivo JSON con un usuario" + email + ".");
            jsonArray = new JsonArray();
        }
        
        boolean UserFound = false;
        
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            
            if (jsonObject.has("email") && email.equals(jsonObject.get("email").getAsString())) {
                String encryptedPassword = PasswordUtils.encrypt(password); // Encriptar la contraseña "123"
                jsonObject.addProperty("password", encryptedPassword);
                UserFound = true;
                System.out.println("Se ha cambiado la contraseña del usuario " + email + " a '" + password + "' (hasheada).");
                break;
            }
        }
        
        // Si no se encontró el usuario root, crearlo
        if (!UserFound) {
            System.out.println("El usuario " + email + " no existe.");
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