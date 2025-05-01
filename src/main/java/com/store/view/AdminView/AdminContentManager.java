package com.store.view.AdminView;

import com.store.models.Usuario;
import com.store.services.ProductoServicio;
import com.store.services.UsuarioServicio;
import com.store.view.panels.admin.ProductosPanel;
import com.store.view.panels.admin.UsersPanel;
import com.store.view.panels.profile.UserProfilePanel;

import javax.swing.*;
import java.awt.*;

public class AdminContentManager {
    private final JPanel mainContent;
    private final Usuario usuario;
    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;

    public AdminContentManager(JPanel mainContent, Usuario usuario, 
                             UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        this.mainContent = mainContent;
        this.usuario = usuario;
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;
        this.mainContent.setLayout(new BorderLayout());
        this.mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.mainContent.setBackground(AdminViewStyles.BACKGROUND_COLOR);
    }

    public void showDashboard() {
        mainContent.removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AdminViewStyles.BACKGROUND_COLOR);
        
        JLabel welcomeLabel = AdminViewStyles.createSectionTitle("Bienvenido al Panel de Administración");
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(new UserProfilePanel(usuarioServicio, usuario), BorderLayout.CENTER);
        
        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    public void showUsersPanel() {
        mainContent.removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AdminViewStyles.BACKGROUND_COLOR);
        
        JLabel titleLabel = AdminViewStyles.createSectionTitle("Gestión de Usuarios");
        panel.add(titleLabel, BorderLayout.NORTH);
        
        UsersPanel usersPanel = new UsersPanel(usuarioServicio);
        usersPanel.addRefreshListener(this::refresh); // Nuevo: Agregar listener
        panel.add(usersPanel, BorderLayout.CENTER);

        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    public void showProductsPanel() {
        mainContent.removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AdminViewStyles.BACKGROUND_COLOR);
        
        JLabel titleLabel = AdminViewStyles.createSectionTitle("Gestión de Productos");
        panel.add(titleLabel, BorderLayout.NORTH);
        
        ProductosPanel productosPanel = new ProductosPanel(productoServicio);
        productosPanel.addRefreshListener(this::refresh); // Nuevo: Agregar listener
        panel.add(productosPanel, BorderLayout.CENTER);

        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    private void refresh() {
        mainContent.revalidate();
        mainContent.repaint();
    }
}