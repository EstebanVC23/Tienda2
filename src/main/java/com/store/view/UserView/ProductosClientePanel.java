package com.store.view.UserView;

import com.store.models.Producto;
import com.store.services.ProductoServicio;
import com.store.view.Components.ContentCard;
import com.store.utils.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que muestra los productos en una vista de cuadrícula para los clientes.
 * Permite filtrar por categoría y buscar productos.
 */
public class ProductosClientePanel extends JPanel {
    private ProductoServicio productoServicio;
    private JPanel productosContainer;
    private JTextField searchField;
    private JComboBox<String> categoriaCombo;
    private List<Producto> productos;
    private List<Producto> productosFiltrados;
    
    /**
     * Constructor del panel de productos para clientes.
     * 
     * @param productoServicio Servicio para manejar los productos
     */
    public ProductosClientePanel(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
        this.productos = new ArrayList<>();
        this.productosFiltrados = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel superior con título y filtros
        JPanel topPanel = createTopPanel();
        
        // Panel central con scroll para los productos
        JPanel centerPanel = createCenterPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        // Cargar datos iniciales
        cargarProductos();
    }
    
    /**
     * Crea el panel superior con título, búsqueda y filtros.
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Panel de título
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 247, 250));
        
        JLabel titleLabel = new JLabel("Nuestros Productos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Colors.DARK_BLUE);
        
        JLabel subtitleLabel = new JLabel("Explora nuestra selección de productos de calidad");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Colors.INACTIVE_TEXT);
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Panel de búsqueda y filtros
        JPanel searchFilterPanel = new JPanel(new BorderLayout(15, 0));
        searchFilterPanel.setBackground(new Color(245, 247, 250));
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(245, 247, 250));
        
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchIcon.setBorder(new EmptyBorder(0, 5, 0, 5));
        
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarProductos();
            }
        });
        
        JPanel searchInputPanel = new JPanel(new BorderLayout());
        searchInputPanel.setBackground(Color.WHITE);
        searchInputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        searchInputPanel.add(searchIcon, BorderLayout.WEST);
        searchInputPanel.add(searchField, BorderLayout.CENTER);
        
        JLabel searchLabel = new JLabel("Buscar productos:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLabel.setForeground(Colors.DARK_BLUE);
        
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchInputPanel, BorderLayout.CENTER);
        
        // Panel de filtro por categoría
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(new Color(245, 247, 250));
        
        JLabel categoriaLabel = new JLabel("Filtrar por categoría:");
        categoriaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        categoriaLabel.setForeground(Colors.DARK_BLUE);
        
        categoriaCombo = new JComboBox<>();
        categoriaCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoriaCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        categoriaCombo.addItem("Todas las categorías");
        
        // Obtener y añadir categorías disponibles
        List<String> categorias = productoServicio.obtenerCategorias();
        for (String categoria : categorias) {
            categoriaCombo.addItem(categoria);
        }
        
        categoriaCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarProductos();
            }
        });
        
        filterPanel.add(categoriaLabel, BorderLayout.NORTH);
        filterPanel.add(categoriaCombo, BorderLayout.CENTER);
        
        // Organizar paneles de búsqueda y filtro
        JPanel filterControlsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        filterControlsPanel.setBackground(new Color(245, 247, 250));
        filterControlsPanel.add(searchPanel);
        filterControlsPanel.add(filterPanel);
        
        searchFilterPanel.add(filterControlsPanel, BorderLayout.CENTER);
        
        // Agregar todo al panel superior
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(searchFilterPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel central con el contenedor de productos y scroll.
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        
        // Contenedor de productos con WrapLayout
        productosContainer = new JPanel();
        productosContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        productosContainer.setBackground(new Color(245, 247, 250));
        
        // Scroll pane para el contenedor de productos
        JScrollPane scrollPane = new JScrollPane(productosContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(245, 247, 250));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Carga los productos desde el servicio.
     */
    private void cargarProductos() {
        productos = productoServicio.listarProductos();
        productosFiltrados = new ArrayList<>(productos);
        mostrarProductos();
    }
    
    /**
     * Filtra los productos según los criterios de búsqueda y categoría.
     */
    private void filtrarProductos() {
        String textoBusqueda = searchField.getText().toLowerCase().trim();
        String categoriaSeleccionada = categoriaCombo.getSelectedItem().toString();
        
        productosFiltrados = new ArrayList<>();
        
        for (Producto producto : productos) {
            boolean coincideTexto = producto.getNombre().toLowerCase().contains(textoBusqueda) ||
                                   producto.getDescripcion().toLowerCase().contains(textoBusqueda);
            
            boolean coincideCategoria = categoriaSeleccionada.equals("Todas las categorías") ||
                                       producto.getCategoria().equals(categoriaSeleccionada);
            
            if (coincideTexto && coincideCategoria) {
                productosFiltrados.add(producto);
            }
        }
        
        mostrarProductos();
    }
    
    /**
     * Muestra los productos filtrados en el contenedor.
     */
    private void mostrarProductos() {
        productosContainer.removeAll();
        
        if (productosFiltrados.isEmpty()) {
            JLabel noProductsLabel = new JLabel("No se encontraron productos con los criterios seleccionados");
            noProductsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            noProductsLabel.setForeground(Colors.INACTIVE_TEXT);
            productosContainer.add(noProductsLabel);
        } else {
            for (Producto producto : productosFiltrados) {
                ContentCard card = new ContentCard(producto);
                productosContainer.add(card);
            }
        }
        
        // Actualizar UI
        productosContainer.revalidate();
        productosContainer.repaint();
    }
    
    /**
     * Clase auxiliar para crear un layout que envuelva los componentes
     * similar a un FlowLayout pero con comportamiento de wrap.
     */
    private class WrapLayout extends FlowLayout {
        private static final long serialVersionUID = 1L;

        // Eliminados los constructores no utilizados

        public WrapLayout(int align, int hgap, int vgap) {
            super(align, hgap, vgap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                
                if (targetWidth == 0) {
                    targetWidth = Integer.MAX_VALUE;
                }

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
                int maxWidth = targetWidth - horizontalInsetsAndGap;

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                int nmembers = target.getComponentCount();

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);

                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                        if (rowWidth + d.width > maxWidth) {
                            addRow(dim, rowWidth, rowHeight);
                            rowWidth = 0;
                            rowHeight = 0;
                        }

                        if (rowWidth != 0) {
                            rowWidth += hgap;
                        }

                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }

                addRow(dim, rowWidth, rowHeight);

                dim.width += horizontalInsetsAndGap;
                dim.height += insets.top + insets.bottom + vgap * 2;

                Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
                if (scrollPane != null && target.isValid()) {
                    dim.width = targetWidth;
                }

                return dim;
            }
        }

        private void addRow(Dimension dim, int rowWidth, int rowHeight) {
            dim.width = Math.max(dim.width, rowWidth);

            if (dim.height > 0) {
                dim.height += getVgap();
            }

            dim.height += rowHeight;
        }
    }
    
    /**
     * Método para actualizar el panel (puede ser llamado desde fuera).
     */
    public void refreshPanel() {
        cargarProductos();
    }
}