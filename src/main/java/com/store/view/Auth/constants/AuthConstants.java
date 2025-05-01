package com.store.view.Auth.constants;

import java.awt.Dimension;
import java.awt.Insets;

/**
 * Clase que contiene constantes utilizadas en las interfaces de autenticación
 */
public class AuthConstants {
    // Dimensiones
    public static final Dimension WINDOW_SIZE = new Dimension(1000, 900);
    public static final Dimension BUTTON_SIZE = new Dimension(150, 40);
    public static final int DOCUMENT_FIELD_HEIGHT = 40;
    public static final int DOCUMENT_FIELD_GAP = 10;
    
    // Margenes y espaciados
    public static final Insets PANEL_PADDING = new Insets(40, 60, 40, 60);
    public static final Insets FIELD_MARGIN = new Insets(10, 10, 10, 10);
    public static final Insets BUTTON_PADDING = new Insets(10, 20, 10, 20);
    
    // Tamaños de fuente
    public static final float TITLE_FONT_SIZE = 24f;
    public static final float SUBTITLE_FONT_SIZE = 18f;
    public static final float BUTTON_FONT_SIZE = 14f;
    public static final float FIELD_FONT_SIZE = 12f;
    
    // Colores específicos para autenticación
    public static final String WELCOME_MESSAGE = "Bienvenido a StoreApp";
    public static final String REGISTER_TITLE = "REGISTRATE";
    public static final String REGISTER_SUBTITLE = "Crea tu cuenta";
    
    // Constantes para campos de documento
    public static final String[] DOCUMENT_TYPES = {"DNI", "Pasaporte", "Cédula"};
    public static final int DOCUMENT_COMBO_WIDTH = 120;
    
    // Constantes para mensajes
    public static final String SUCCESS_REGISTER = "Usuario registrado exitosamente";
    public static final String ERROR_REGISTER = "Error al registrar usuario";
    public static final String EMPTY_FIELDS = "Por favor, complete todos los campos";
    public static final String PASSWORD_MISMATCH = "Las contraseñas no coinciden";
    public static final String EMAIL_EXISTS = "El correo ya está registrado";
    
    // Configuración de campos
    public static final int FIELD_COLUMNS = 20;
    public static final int PASSWORD_COLUMNS = 15;
}