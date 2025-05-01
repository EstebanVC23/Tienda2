package com.store.view.panels.profile;

import com.store.utils.Colors;
import com.store.utils.Fonts;

import java.awt.*;

public class UserProfileConstants {
    // Panel constants
    public static final Color BACKGROUND = Colors.BACKGROUND;
    public static final Color PANEL_BACKGROUND = Color.WHITE;
    public static final Color BORDER_COLOR = Colors.BORDER;
    public static final Color HIGHLIGHT_COLOR = new Color(230, 245, 255);
    
    // Text colors
    public static final Color TITLE_COLOR = Colors.PRIMARY_TEXT;
    public static final Color SECTION_COLOR = new Color(30, 136, 229);
    public static final Color LABEL_COLOR = Colors.SECONDARY_TEXT;
    public static final Color VALUE_COLOR = Colors.PRIMARY_TEXT;
    
    // Fonts
    public static final Font TITLE_FONT = Fonts.TITLE;
    public static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font LABEL_FONT = Fonts.BOLD_BODY;
    public static final Font VALUE_FONT = Fonts.BODY;
    
    // Spacing and padding
    public static final int BORDER_PADDING = 25;
    public static final int FIELD_PADDING = 8;
    public static final Insets FIELD_INSETS = new Insets(8, 0, 8, 15);
    
    // Section titles
    public static final String TITLE = "Perfil de Usuario";
    public static final String PERSONAL_INFO_TITLE = "Información Personal";
    public static final String ADDRESS_TITLE = "Dirección";
    public static final String ACCOUNT_INFO_TITLE = "Información de Cuenta";
}