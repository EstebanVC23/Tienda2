package com.store.view.components.dialogs.constants;

public class StockDialogConstants extends FormDialogConstants {
    public int LOW_STOCK_THRESHOLD = 10;
    public int FIELD_SPACING = 15;
    public String SUCCESS_MESSAGE = "Stock actualizado exitosamente";
    public String ERROR_MESSAGE = "No hay suficiente stock disponible";
    public String INVALID_NUMBER_MESSAGE = "Ingrese una cantidad válida";
    
    public StockDialogConstants() {
        WIDTH = 400;
        HEIGHT = 300;
    }
}