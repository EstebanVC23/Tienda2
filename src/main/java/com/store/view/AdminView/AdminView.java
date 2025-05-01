package com.store.view.AdminView;

import com.store.models.Usuario;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.view.UserProfilePanel;
import com.store.view.AdminView.panels.*;
import com.store.view.components.NavBar.*;
import com.store.Auth.Login;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JFrame {
    private Usuario usuario;
    private UsuarioServicio usuarioServicio;
    private ProductoServicio productoServicio;
    private JPanel mainContent;
    
    public AdminView(Usuario usuario, UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
        this.usuario = usuario;
        this.usuarioServicio = usuarioServicio;
        
        setTitle("Panel de Administración");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        String[] navOptions = {"Dashboard", "Usuarios", "Productos"};
        
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
        
        showDashboardPanel();
        
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
            case "Usuarios":
                showUsersPanel();
                break;
            case "Productos":
                showProductPanel();
                break;
        }
        
        mainContent.revalidate();
        mainContent.repaint();
    }
    
    private void showDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(new Color(240, 245, 249));
        
        JLabel welcomeLabel = new JLabel("Bienvenido al Panel de Administración");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        dashboardPanel.add(welcomeLabel, BorderLayout.NORTH);
        dashboardPanel.add(new UserProfilePanel(usuario), BorderLayout.CENTER);
        
        mainContent.add(dashboardPanel, BorderLayout.CENTER);
    }
    
    private void showUsersPanel() {
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.setBackground(new Color(240, 245, 249));
        
        JLabel titleLabel = new JLabel("Gestión de Usuarios");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        usersPanel.add(titleLabel, BorderLayout.NORTH);
        usersPanel.add(new UsersPanel(usuarioServicio), BorderLayout.CENTER);

        mainContent.add(usersPanel, BorderLayout.CENTER);
    }

    private void showProductPanel() {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBackground(new Color(240, 245, 249));
        
        JLabel titleLabel = new JLabel("Gestión de Productos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        productPanel.add(titleLabel, BorderLayout.NORTH);
        productPanel.add(new ProductosPanel(productoServicio), BorderLayout.CENTER);

        mainContent.add(productPanel, BorderLayout.CENTER);
    }
    
    private void handleLogout() {
        new Login(usuarioServicio, productoServicio).setVisible(true);
        dispose();
    }
}