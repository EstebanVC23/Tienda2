package com.store.view.UserView;

import com.store.models.Usuario;
import com.store.services.ProductoServicioImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.auth.Login;
import com.store.view.components.NavBar.*;
import com.store.view.panels.profile.UserProfilePanel;
import com.store.view.panels.users.ProductosClientePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación para usuarios regulares del sistema de tienda.
 * Proporciona navegación entre las diferentes secciones (productos y perfil)
 * y maneja la gestión de la sesión del usuario.
 * 
 * <p>Características principales:
 * <ul>
 *   <li>Catálogo de productos</li>
 *   <li>Gestión del perfil de usuario</li>
 *   <li>Función de cierre de sesión</li>
 * </ul>
 * 
 * <p>Utiliza un diseño responsive con barra de navegación, área de contenido principal
 * y estilos consistentes mediante constantes de la aplicación.
 */
public class UserView extends JFrame {
    /** Usuario actual con sesión iniciada */
    private Usuario usuario;
    /** Servicio para operaciones con usuarios */
    private UsuarioServicioImpl usuarioServicio;
    /** Servicio para operaciones con productos */
    private ProductoServicioImpl productoServicio;
    /** Servicio para operaciones de ventas */
    private SaleServiceImpl saleServicio;
    /** Panel principal de contenido */
    private JPanel mainContent;
    
    /**
     * Construye la vista principal del usuario.
     * @param usuario Usuario con sesión iniciada
     * @param productoServicio Servicio para gestión de productos
     * @param usuarioServicio Servicio para gestión de usuarios
     */
    public UserView(Usuario usuario, ProductoServicioImpl productoServicio, UsuarioServicioImpl usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        this.usuario = usuario;
        
        configurarVentana();
        inicializarComponentes();
    }
    
    /**
     * Maneja la navegación entre las diferentes secciones de la aplicación.
     * @param option Opción seleccionada en la barra de navegación
     */
    private void handleNavigation(String option) {
        mainContent.removeAll();
        
        switch (option) {
            case "Dashboard":
                mostrarPanelDashboard();
                break;
            case "Productos":
                mostrarPanelProductos();
                break;
        }
        
        mainContent.revalidate();
        mainContent.repaint();
    }
    
    /**
     * Muestra el panel principal con el perfil de usuario.
     */
    private void mostrarPanelDashboard() {
        JPanel panel = crearPanelBase("Bienvenido al Panel de Usuario");
        panel.add(new UserProfilePanel(usuarioServicio, usuario), BorderLayout.CENTER);
        mainContent.add(panel, BorderLayout.CENTER);
    }

    /**
     * Muestra el panel con el catálogo de productos.
     */
    private void mostrarPanelProductos() {
        JPanel panel = crearPanelBase("Catálogo de Productos");
        panel.add(new ProductosClientePanel(productoServicio), BorderLayout.CENTER);
        mainContent.add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Maneja el cierre de sesión mostrando la ventana de login
     * y cerrando la ventana actual.
     */
    private void handleLogout() {
        new Login(usuarioServicio, productoServicio, saleServicio).setVisible(true);
        dispose();
    }
    
    /**
     * Configura las propiedades básicas de la ventana.
     */
    private void configurarVentana() {
        setTitle("Panel de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }
    
    /**
     * Inicializa los componentes principales de la interfaz.
     */
    private void inicializarComponentes() {
        Navbar navBar = new Navbar(
            e -> handleNavigation(e.getActionCommand()),
            new String[]{"Productos", "Dashboard"}
        );
        
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContent.setBackground(Colors.BACKGROUND);
        
        JPanel logoutPanel = crearPanelLogout();
        
        mostrarPanelDashboard();
        
        add(navBar, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea un panel base con título para las secciones.
     * @param titulo Título a mostrar en el panel
     * @return JPanel configurado
     */
    private JPanel crearPanelBase(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.BACKGROUND);
        
        JLabel label = new JLabel(titulo);
        label.setFont(Fonts.TITLE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }
    
    /**
     * Crea el panel con el botón de cierre de sesión.
     * @return JPanel configurado
     */
    private JPanel crearPanelLogout() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Colors.BACKGROUND);
        
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(Fonts.BUTTON);
        btnLogout.setBackground(Colors.LOGOUT_RED);
        btnLogout.setForeground(Colors.ACTIVE_TEXT);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(_ -> handleLogout());
        
        panel.add(btnLogout);
        return panel;
    }
}