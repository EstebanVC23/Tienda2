package com.store.view.auth;

import com.store.services.ProductoServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.utils.Colors;
import com.store.view.components.forauth.PanelIzquierdoInicio;
import javax.swing.*;
import java.awt.*;

/**
 * Clase base abstracta para las ventanas de autenticación (login y registro).
 * Proporciona la estructura común y funcionalidad básica para las ventanas de autenticación.
 */
public abstract class AuthBaseFrame extends JFrame {
    protected final UsuarioServicioImpl usuarioServicio;
    protected final ProductoServicioImpl productoServicio;
    protected final SaleServiceImpl saleServicio;
    
    /**
     * Constructor base para las ventanas de autenticación.
     * @param usuarioServicio Servicio para gestión de usuarios
     * @param productoServicio Servicio para gestión de productos
     * @param title Título de la ventana
     */
    public AuthBaseFrame(UsuarioServicioImpl usuarioServicio, ProductoServicioImpl productoServicio, SaleServiceImpl saleServicio, String title) {
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        this.saleServicio = saleServicio;
        
        setTitle(title);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        getContentPane().setBackground(Colors.PANEL_BACKGROUND);
    }
    
    /**
     * Añade el panel izquierdo común a la ventana de autenticación.
     * @param title Título principal del panel
     * @param subtitle Subtítulo del panel
     */
    protected void addLeftPanel(String title, String subtitle) {
        PanelIzquierdoInicio panelIzquierdo = new PanelIzquierdoInicio(title, subtitle);
        add(panelIzquierdo);
    }
    
    /**
     * Crea y configura el panel derecho donde irán los formularios.
     * @return JPanel configurado para el lado derecho de la ventana
     */
    protected JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Colors.PANEL_BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        return panel;
    }
}