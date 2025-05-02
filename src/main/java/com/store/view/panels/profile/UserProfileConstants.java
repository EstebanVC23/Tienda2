package com.store.view.panels.profile;

import com.store.utils.Colors;
import com.store.utils.Fonts;

import java.awt.*;

/**
 * Clase que contiene constantes de configuración visual para el panel de perfil de usuario.
 * Define colores, fuentes, espaciados y textos utilizados en la interfaz de perfil.
 * Todas las constantes son públicas y estáticas para su fácil acceso.
 */
public class UserProfileConstants {
    /** Color de fondo principal del panel de perfil */
    public static final Color BACKGROUND = Colors.BACKGROUND;
    
    /** Color de fondo de los subpaneles */
    public static final Color PANEL_BACKGROUND = Color.WHITE;
    
    /** Color utilizado para bordes */
    public static final Color BORDER_COLOR = Colors.BORDER;
    
    /** Color de resaltado para elementos seleccionados */
    public static final Color HIGHLIGHT_COLOR = new Color(230, 245, 255);
    
    /** Color para textos de título */
    public static final Color TITLE_COLOR = Colors.PRIMARY_TEXT;
    
    /** Color para secciones del perfil */
    public static final Color SECTION_COLOR = new Color(30, 136, 229);
    
    /** Color para etiquetas de campos */
    public static final Color LABEL_COLOR = Colors.SECONDARY_TEXT;
    
    /** Color para valores de campos */
    public static final Color VALUE_COLOR = Colors.PRIMARY_TEXT;
    
    /** Fuente para títulos principales */
    public static final Font TITLE_FONT = Fonts.TITLE;
    
    /** Fuente para secciones del perfil */
    public static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 16);
    
    /** Fuente para etiquetas de campos */
    public static final Font LABEL_FONT = Fonts.BOLD_BODY;
    
    /** Fuente para valores de campos */
    public static final Font VALUE_FONT = Fonts.BODY;
    
    /** Padding interno para bordes */
    public static final int BORDER_PADDING = 25;
    
    /** Padding interno para campos */
    public static final int FIELD_PADDING = 8;
    
    /** Márgenes entre campos */
    public static final Insets FIELD_INSETS = new Insets(8, 0, 8, 15);
    
    /** Título principal del panel de perfil */
    public static final String TITLE = "Perfil de Usuario";
    
    /** Título de la sección de información personal */
    public static final String PERSONAL_INFO_TITLE = "Información Personal";
    
    /** Título de la sección de dirección */
    public static final String ADDRESS_TITLE = "Dirección";
    
    /** Título de la sección de información de cuenta */
    public static final String ACCOUNT_INFO_TITLE = "Información de Cuenta";
}