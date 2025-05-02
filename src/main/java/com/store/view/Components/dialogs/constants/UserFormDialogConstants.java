package com.store.view.components.dialogs.constants;

/**
 * Clase que contiene constantes específicas para el formulario de usuario.
 * Extiende {@link FormDialogConstants} para reutilizar constantes comunes
 * y define configuraciones particulares para el formulario de gestión de usuarios.
 */
public class UserFormDialogConstants extends FormDialogConstants {
    /** Padding horizontal interno del formulario */
    public int FORM_HORIZONTAL_PADDING = 30;
    
    /** Espaciado vertical entre campos del formulario */
    public int FIELD_SPACING = 15;
    
    /** Espacio horizontal entre campos de documento (tipo y número) */
    public int DOCUMENT_FIELD_GAP = 5;
    
    /** Altura uniforme para los campos de documento */
    public int DOCUMENT_FIELD_HEIGHT = 35;
    
    /** Texto para el checkbox de estado de usuario */
    public String ACTIVE_USER_TEXT = "Usuario activo";
    
    /** Mensaje de éxito al guardar usuario */
    public String SUCCESS_MESSAGE = "Usuario guardado con éxito.";
    
    /** Mensaje de error al guardar usuario */
    public String ERROR_MESSAGE = "Error al guardar usuario.";
    
    /** Mensaje de validación para datos incompletos */
    public String INVALID_DATA_MESSAGE = "Por favor, complete todos los campos correctamente.";
    
    /** Altura del diálogo para edición de usuario */
    public int HEIGHT_EDIT = 650;
    
    /** Altura del diálogo para creación de usuario (incluye campos adicionales) */
    public int HEIGHT_CREATE = 800;
}