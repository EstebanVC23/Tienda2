package com.store.view.AdminView;

import com.store.models.Usuario;
import com.store.services.ProductoServicioImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.view.auth.Login;
import com.store.view.components.NavBar.Navbar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Vista principal del panel de administración.
 * Proporciona una interfaz para gestionar usuarios, productos y mostrar el dashboard.
 */
public class AdminView extends JFrame {

    private final Usuario usuario;
    private final UsuarioServicioImpl usuarioServicio;
    private final ProductoServicioImpl productoServicio;
    private final JPanel mainContent;
    private final AdminContentManager contentManager;

    /**
     * Enumeración que representa los paneles disponibles en la interfaz de administración.
     */
    private enum AdminPanel {
        DASHBOARD("Dashboard"), 
        USERS("Usuarios"), 
        PRODUCTS("Productos");
        
        private final String label;
        
        AdminPanel(String label) {
            this.label = label;
        }
        
        /**
         * Obtiene la etiqueta del panel.
         * @return String con el nombre del panel
         */
        public String getLabel() {
            return label;
        }
    }

    /**
     * Constructor de la vista de administración.
     * @param usuario Usuario administrador
     * @param usuarioServicio Servicio para gestión de usuarios
     * @param productoServicio Servicio para gestión de productos
     * @throws IllegalArgumentException Si el usuario no tiene rol de administrador
     */
    public AdminView(Usuario usuario, UsuarioServicioImpl usuarioServicio, ProductoServicioImpl productoServicio) {
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
    
    /**
     * Configura las propiedades básicas de la ventana.
     */
    private void configureWindow() {
        setTitle("Panel de Administración - " + usuario.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }
    
    /**
     * Inicializa los componentes de la interfaz de usuario.
     */
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
    
    /**
     * Maneja la navegación entre los diferentes paneles.
     * @param e Evento de acción que contiene la opción seleccionada
     */
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
    
    /**
     * Maneja el cierre de sesión del administrador.
     * @param e Evento de acción del botón de logout
     */
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