package com.store.view.Auth;

import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.Auth.constants.AuthConstants;
import com.store.view.components.forauth.PanelIzquierdoInicio;

import javax.swing.*;
import java.awt.*;

public abstract class AuthBaseFrame extends JFrame {
    protected final UsuarioServicio usuarioServicio;
    protected final ProductoServicio productoServicio;
    
    public AuthBaseFrame(UsuarioServicio usuarioServicio, ProductoServicio productoServicio, String title) {
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        
        setTitle(title);
        setSize(AuthConstants.WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
    }
    
    protected void addLeftPanel(String title, String subtitle) {
        PanelIzquierdoInicio panelIzquierdo = new PanelIzquierdoInicio(title, subtitle);
        add(panelIzquierdo);
    }
    
    protected JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Colors.PANEL_BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(
            AuthConstants.PANEL_PADDING.top,
            AuthConstants.PANEL_PADDING.left,
            AuthConstants.PANEL_PADDING.bottom,
            AuthConstants.PANEL_PADDING.right
        ));
        return panel;
    }
    
    protected JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Colors.PRIMARY_TEXT);
        label.setFont(Fonts.TITLE.deriveFont(AuthConstants.TITLE_FONT_SIZE));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    protected JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }
    
    protected JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);
        return panel;
    }
}