package com.store.view.adminView;

import com.store.models.Usuario;
import com.store.services.ProductoServicioImpl;
import com.store.services.SaleServiceImpl;
import com.store.services.UsuarioServicioImpl;
import com.store.view.panels.admin.ProductosPanel;
import com.store.view.panels.admin.SalesPanel;
import com.store.view.panels.admin.UsersPanel;
import com.store.view.panels.profile.UserProfilePanel;


import javax.swing.*;
import java.awt.*;

/**
 * Clase que gestiona el contenido principal de la interfaz de administración.
 * Controla la visualización de los diferentes paneles y su actualización.
 * Utiliza los estilos definidos en las clases utilitarias Colors y Fonts.
 */
public class AdminContentManager {
    private final JPanel mainContent;
    private final Usuario usuario;
    private final UsuarioServicioImpl usuarioServicio;
    private final ProductoServicioImpl productoServicio;
    private final SaleServiceImpl saleService;

    /**
     * Constructor del administrador de contenido.
     * @param mainContent Panel principal donde se mostrará el contenido
     * @param usuario Usuario administrador actual
     * @param usuarioServicio Servicio para gestión de usuarios
     * @param productoServicio Servicio para gestión de productos
     */
    public AdminContentManager(JPanel mainContent, Usuario usuario, 
                           UsuarioServicioImpl usuarioServicio, 
                           ProductoServicioImpl productoServicio,
                           SaleServiceImpl saleService) {
    this.mainContent = mainContent;
    this.usuario = usuario;
    this.usuarioServicio = usuarioServicio;
    this.productoServicio = productoServicio;
    this.saleService = saleService;
    this.mainContent.setLayout(new BorderLayout());
    AdminViewStyles.stylePanel(mainContent);
}

    /**
     * Muestra el panel de inicio/dashboard del administrador.
     */
    public void showDashboard() {
        mainContent.removeAll();
        JPanel panel = AdminViewStyles.stylePanel(new JPanel(new BorderLayout()));
        
        JLabel welcomeLabel = AdminViewStyles.createSectionTitle("Bienvenido al Panel de Administración");
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(new UserProfilePanel(usuarioServicio, usuario), BorderLayout.CENTER);
        
        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    /**
     * Muestra el panel de gestión de usuarios.
     */
    public void showUsersPanel() {
        mainContent.removeAll();
        JPanel panel = AdminViewStyles.stylePanel(new JPanel(new BorderLayout()));
        
        JLabel titleLabel = AdminViewStyles.createSectionTitle("Gestión de Usuarios");
        panel.add(titleLabel, BorderLayout.NORTH);
        
        UsersPanel usersPanel = new UsersPanel(usuarioServicio);
        usersPanel.addRefreshListener(this::refresh);
        panel.add(usersPanel, BorderLayout.CENTER);

        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    /**
     * Muestra el panel de gestión de productos.
     */
    public void showProductsPanel() {
        mainContent.removeAll();
        JPanel panel = AdminViewStyles.stylePanel(new JPanel(new BorderLayout()));
        
        JLabel titleLabel = AdminViewStyles.createSectionTitle("Gestión de Productos");
        panel.add(titleLabel, BorderLayout.NORTH);
        
        ProductosPanel productosPanel = new ProductosPanel(productoServicio);
        productosPanel.addRefreshListener(this::refresh);
        panel.add(productosPanel, BorderLayout.CENTER);

        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    public void showSalesPanel() {
        mainContent.removeAll();
        JPanel panel = AdminViewStyles.stylePanel(new JPanel(new BorderLayout()));
        
        JLabel titleLabel = AdminViewStyles.createSectionTitle("Gestión de Ventas");
        panel.add(titleLabel, BorderLayout.NORTH);
        
        SalesPanel salesPanel = new SalesPanel(saleService);
        salesPanel.addRefreshListener(this::refresh);
        panel.add(salesPanel, BorderLayout.CENTER);
        
        mainContent.add(panel, BorderLayout.CENTER);
        refresh();
    }

    /**
     * Actualiza y repinta el contenido principal.
     */
    private void refresh() {
        mainContent.revalidate();
        mainContent.repaint();
    }
}