package com.store.utils;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class JsonProcessor {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\esvca\\OneDrive\\Documentos\\Tareas\\Progra IV\\Parciales\\Parcial 2\\Store\\usuarios.json";

        try {
            // Leer el archivo JSON
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonElement jsonElement = JsonParser.parseString(jsonString);

            JsonArray jsonArray;

            if (jsonElement.isJsonArray()) {
                jsonArray = jsonElement.getAsJsonArray();
            } else {
                System.out.println("El archivo JSON no contiene un array válido. Creando uno nuevo.");
                jsonArray = new JsonArray();
            }

            // Modificar los usuarios existentes
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                // Modificar el rol
                jsonObject.addProperty("rol", "USER");

                // Encriptar todas las contraseñas con "1234"
                String newPassword = "1234";
                String encryptedPassword = PasswordUtils.encrypt(newPassword);
                jsonObject.addProperty("password", encryptedPassword);
            }

            // Crear el superusuario
            JsonObject superUser = new JsonObject();
            superUser.addProperty("id", 999); // ID especial
            superUser.addProperty("nombre", "SuperUsuario");
            superUser.addProperty("apellido", "AdminMaster");
            superUser.addProperty("email", "root");
            superUser.addProperty("tipoDocumento", "DNI");
            superUser.addProperty("numeroDocumento", "00000000");
            superUser.addProperty("direccion", "Av. Principal 123");
            superUser.addProperty("telefono", "+999999999");
            superUser.addProperty("password", PasswordUtils.encrypt("123")); // Encriptar la contraseña "123"
            superUser.addProperty("rol", "ADMIN");
            superUser.addProperty("estadoActivo", true);

            // Agregar el superusuario al array
            jsonArray.add(superUser);

            // Guardar los cambios en el archivo JSON
            try (FileWriter writer = new FileWriter(filePath)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(jsonArray, writer);
            }

            System.out.println("Todas las contraseñas han sido reemplazadas y se ha agregado un superusuario.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}