package com.store.view.UserView;

import com.store.models.Usuario;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.view.Auth.Login;
import com.store.view.components.NavBar.*;
import com.store.view.panels.profile.UserProfilePanel;
import com.store.view.panels.users.ProductosClientePanel;

import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {
    private Usuario usuario;
    private UsuarioServicio usuarioServicio;
    private ProductoServicio productoServicio;
    private JPanel mainContent;
    
    public UserView(Usuario usuario, ProductoServicio productoServicio, UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        this.usuario = usuario;
        
        setTitle("Panel de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        
        String[] navOptions = {"Productos", "Dashboard"};
        
        Navbar navBar = new Navbar(
            e -> handleNavigation(e.getActionCommand()),
            navOptions
        );
        
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContent.setBackground(new Color(240, 245, 249));
        
        // Panel para el botón de cerrar sesión
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(240, 245, 249));
        
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(_ -> handleLogout());
        
        logoutPanel.add(logoutButton);
        
        showProductsPanel();
        
        add(navBar, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);
    }
    
    private void handleNavigation(String option) {
        mainContent.removeAll();
        
        switch (option) {
            case "Dashboard":
                showDashboardPanel();
                break;
            case "Productos":
                showProductsPanel();
                break;
        }
        
        mainContent.revalidate();
        mainContent.repaint();
    }
    
    private void showDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(new Color(240, 245, 249));
        
        JLabel welcomeLabel = new JLabel("Bienvenido al Panel de Usuario");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        dashboardPanel.add(welcomeLabel, BorderLayout.NORTH);
        dashboardPanel.add(new UserProfilePanel(usuarioServicio, usuario), BorderLayout.CENTER);
        
        mainContent.add(dashboardPanel, BorderLayout.CENTER);
    }

    private void showProductsPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(new Color(240, 245, 249));
        
        JLabel welcomeLabel = new JLabel("Catálogo de Productos");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        dashboardPanel.add(welcomeLabel, BorderLayout.NORTH);
        dashboardPanel.add(new ProductosClientePanel(productoServicio), BorderLayout.CENTER);
        
        mainContent.add(dashboardPanel, BorderLayout.CENTER);
    }
    
    private void handleLogout() {
        new Login(usuarioServicio, productoServicio).setVisible(true);
        dispose();
    }
}