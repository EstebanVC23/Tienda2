package com.store.view.components.dialogs.constants;

/**
 * Clase que contiene constantes específicas para el diálogo de gestión de stock.
 * Extiende {@link FormDialogConstants} para reutilizar constantes comunes
 * y añade configuraciones específicas para el manejo de inventario.
 */
public class StockDialogConstants extends FormDialogConstants {
    /** Umbral mínimo de stock para considerar bajo inventario */
    public int LOW_STOCK_THRESHOLD = 10;
    
    /** Espaciado entre campos del formulario de stock */
    public int FIELD_SPACING = 15;
    
    /** Mensaje de éxito al actualizar el stock */
    public String SUCCESS_MESSAGE = "Stock actualizado exitosamente";
    
    /** Mensaje de error cuando no hay suficiente stock */
    public String ERROR_MESSAGE = "No hay suficiente stock disponible";
    
    /** Mensaje de error para cantidades inválidas */
    public String INVALID_NUMBER_MESSAGE = "Ingrese una cantidad válida";
    
    /**
     * Constructor que establece dimensiones específicas para el diálogo de stock.
     * Sobrescribe las dimensiones heredadas de {@link FormDialogConstants}.
     */
    public StockDialogConstants() {
        WIDTH = 400;
        HEIGHT = 350;
    }
}