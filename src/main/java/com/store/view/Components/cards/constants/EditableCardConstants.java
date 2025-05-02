package com.store.view.components.cards.constants;

/**
 * Constantes específicas para tarjetas editables en la aplicación.
 * Extiende las constantes base de tarjetas y añade configuraciones
 * para funcionalidad de edición.
 */
public class EditableCardConstants extends BaseCardConstants {
    /**
     * Número de clics requeridos para activar el modo edición.
     * Valor por defecto: 2 (doble clic).
     */
    public final int CLICKS_TO_EDIT = 2;

    /**
     * Prefijo de texto para los prompts de edición.
     * Se utiliza como prefijo en los diálogos de edición.
     */
    public final String EDIT_PROMPT_PREFIX = "Editar ";
}