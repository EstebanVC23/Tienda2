package com.store.view.auth.constants;

import java.awt.*;

/**
 * Clase que contiene todas las constantes utilizadas en el módulo de autenticación.
 * Incluye dimensiones, fuentes, textos, mensajes y configuraciones visuales.
 */
public class AuthConstants {
    /** Dimensiones para componentes de la interfaz */
    public static final Dimension WINDOW_SIZE = new Dimension(900, 700);
    public static final Dimension BUTTON_SIZE = new Dimension(150, 40);
    public static final Dimension LOGIN_BUTTON_SIZE = new Dimension(150, 40);
    public static final Dimension INPUT_FIELD_SIZE = new Dimension(400, 30);
    public static final Dimension REGISTER_INPUT_SIZE = new Dimension(300, 40);
    
    /** Tamaños de fuente para diferentes componentes */
    public static final float TITLE_FONT_SIZE = 24f;
    public static final float SUBTITLE_FONT_SIZE = 18f;
    public static final float BUTTON_FONT_SIZE = 14f;
    public static final float LOGIN_BUTTON_FONT_SIZE = 13f;
    public static final float SECONDARY_BUTTON_FONT_SIZE = 12f;
    
    /** Configuraciones de espaciado y márgenes */
    public static final Insets PANEL_PADDING = new Insets(40, 40, 40, 40);
    public static final Insets BUTTON_PADDING = new Insets(10, 20, 10, 20);
    public static final Insets LOGIN_FORM_PADDING = new Insets(10, 0, 10, 0);
    public static final int VERTICAL_STRUT = 10;
    
    /** Textos para títulos y subtítulos */
    public static final String LOGIN_TITLE = "BIENVENIDO";
    public static final String LOGIN_SUBTITLE = "Sistema de Gestión";
    public static final String LOGIN_FORM_TITLE = "Iniciar Sesión";
    public static final String REGISTER_TITLE = "REGISTRO";
    public static final String REGISTER_SUBTITLE = "Crea tu cuenta";
    public static final String REGISTER_FORM_TITLE = "Registrar Usuario";
    
    /** Etiquetas para campos de formulario */
    public static final String EMAIL_LABEL = "Correo electrónico";
    public static final String PASSWORD_LABEL = "Contraseña";
    public static final String CONFIRM_PASSWORD_LABEL = "Confirmar Contraseña";
    public static final String NAME_LABEL = "Nombre";
    public static final String LASTNAME_LABEL = "Apellido";
    public static final String DOCUMENT_TYPE_LABEL = "Tipo Documento";
    public static final String DOCUMENT_NUMBER_LABEL = "Número Documento";
    public static final String ADDRESS_LABEL = "Dirección";
    public static final String PHONE_LABEL = "Teléfono";
    
    /** Textos para botones */
    public static final String LOGIN_BUTTON_TEXT = "INGRESAR";
    public static final String REGISTER_BUTTON_TEXT = "REGISTRAR";
    public static final String REGISTER_LINK_TEXT = "¿No tienes cuenta? Regístrate";
    public static final String BACK_TO_LOGIN_TEXT = "Volver al Login";
    
    /** Mensajes para el usuario */
    public static final String EMPTY_FIELDS = "Por favor, complete todos los campos.";
    public static final String PASSWORD_MISMATCH = "Las contraseñas no coinciden.";
    public static final String EMAIL_EXISTS = "El correo electrónico ya está registrado.";
    public static final String SUCCESS_REGISTER = "¡Registro exitoso! Ahora puedes iniciar sesión.";
    public static final String ERROR_REGISTER = "Error al registrar el usuario. Intente nuevamente.";
    public static final String LOGIN_ERROR = "Correo o contraseña incorrectos";
    public static final String LOGIN_SUCCESS = "Bienvenido %s";
    public static final String EMPTY_FIELDS_LOGIN = "Por favor, ingrese ambos campos.";
    
    /** Tipos de campos de entrada */
    public static final String TEXT_FIELD_TYPE = "texto";
    public static final String PASSWORD_FIELD_TYPE = "password";
    public static final String NUMBER_FIELD_TYPE = "entero";
    
    /** Opciones disponibles para tipo de documento */
    public static final String[] DOCUMENT_TYPES = {"DNI", "Pasaporte", "Cédula"};
}