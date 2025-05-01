package com.store.view.AdminView;

import com.store.models.Usuario;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.view.Auth.Login;
import com.store.view.components.NavBar.Navbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminView extends JFrame {

    private final Usuario usuario;
    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;
    private final JPanel mainContent;
    private final AdminContentManager contentManager;

    private enum AdminPanel {
        DASHBOARD("Dashboard"), 
        USERS("Usuarios"), 
        PRODUCTS("Productos");
        
        private final String label;
        
        AdminPanel(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
    }

    public AdminView(Usuario usuario, UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        if (!usuario.getRol().equals("ADMIN")) {
            throw new IllegalArgumentException("El usuario no tiene permisos de administrador");
        }

        this.usuario = usuario;
        this.productoServicio = productoServicio;
        this.usuarioServicio = usuarioServicio;
        this.mainContent = new JPanel(new BorderLayout());
        this.contentManager = new AdminContentManager(
            mainContent, 
            usuario, 
            usuarioServicio, 
            productoServicio
        );

        configureWindow();
        initUI();
    }
    
    private void configureWindow() {
        setTitle("Panel de Administración - " + usuario.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }
    
    private void initUI() {
        String[] navOptions = {
            AdminPanel.DASHBOARD.getLabel(),
            AdminPanel.USERS.getLabel(),
            AdminPanel.PRODUCTS.getLabel()
        };
        
        Navbar navBar = new Navbar(this::handleNavigation, navOptions);
        
        LogoutPanel logoutPanel = new LogoutPanel(this::handleLogout);
        
        contentManager.showDashboard();
        
        add(navBar, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);
    }
    
    private void handleNavigation(ActionEvent e) {
        String option = e.getActionCommand();
        switch (option) {
            case "Dashboard":
                contentManager.showDashboard();
                break;
            case "Usuarios":
                contentManager.showUsersPanel();
                break;
            case "Productos":
                contentManager.showProductsPanel();
                break;
        }
    }
    
    private void handleLogout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            new Login(usuarioServicio, productoServicio).setVisible(true);
            dispose();
        }
    }
}