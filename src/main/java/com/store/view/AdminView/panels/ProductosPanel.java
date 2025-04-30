package com.store.view.AdminView.panels;

import com.store.models.Producto;
import com.store.services.ProductoServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ProductosPanel extends JPanel {
    private ProductoServicio productoServicio;
    private JTable productosTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public ProductosPanel(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 249));
        
        // Panel superior para el título y búsqueda
        JPanel topPanel = createTopPanel();
        
        // Panel central para la tabla
        JPanel tablePanel = createTablePanel();
        
        // Panel inferior para botones
        JPanel bottomPanel = createBottomPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 245, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Título
        JLabel titleLabel = new JLabel("Gestión de Productos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(240, 245, 249));
        
        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(searchPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.setBackground(new Color(240, 245, 249));
        
        // Crear modelo de tabla
        String[] columnNames = {"Código", "Nombre", "Descripción", "Precio", "Stock", "Categoría", "Proveedor"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla sea no editable
            }
        };
        
        // Crear tabla
        productosTable = new JTable(tableModel);
        productosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        productosTable.setRowHeight(25);
        productosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productosTable.setAutoCreateRowSorter(true);
        
        // Configurar el ordenador de filas
        sorter = new TableRowSorter<>(tableModel);
        productosTable.setRowSorter(sorter);
        
        // Cargar datos de productos
        loadProductData();
        
        // Panel de desplazamiento para la tabla
        JScrollPane scrollPane = new JScrollPane(productosTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(240, 245, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Botones de acción
        JButton newButton = createButton("Nuevo Producto", new Color(76, 175, 80));
        JButton editButton = createButton("Editar", new Color(33, 150, 243));
        JButton deleteButton = createButton("Eliminar", new Color(244, 67, 54));
        JButton stockButton = createButton("Gestionar Stock", new Color(255, 152, 0));
        
        // Acciones de los botones
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProductFormDialog(null);
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productosTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = productosTable.convertRowIndexToModel(selectedRow);
                    String codigo = (String) tableModel.getValueAt(modelRow, 0);
                    Producto producto = productoServicio.obtenerProductoPorCodigo(codigo);
                    if (producto != null) {
                        showProductFormDialog(producto);
                    }
                } else {
                    JOptionPane.showMessageDialog(ProductosPanel.this, 
                                                "Por favor, seleccione un producto para editar.", 
                                                "Editar Producto", 
                                                JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productosTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = productosTable.convertRowIndexToModel(selectedRow);
                    String codigo = (String) tableModel.getValueAt(modelRow, 0);
                    
                    int option = JOptionPane.showConfirmDialog(ProductosPanel.this, 
                                                             "¿Está seguro de eliminar este producto?", 
                                                             "Eliminar Producto", 
                                                             JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        boolean success = productoServicio.eliminarProducto(codigo);
                        if (success) {
                            JOptionPane.showMessageDialog(ProductosPanel.this, 
                                                        "Producto eliminado con éxito.", 
                                                        "Eliminar Producto", 
                                                        JOptionPane.INFORMATION_MESSAGE);
                            loadProductData(); // Recargar datos
                        } else {
                            JOptionPane.showMessageDialog(ProductosPanel.this, 
                                                        "Error al eliminar el producto.", 
                                                        "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(ProductosPanel.this, 
                                                "Por favor, seleccione un producto para eliminar.", 
                                                "Eliminar Producto", 
                                                JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productosTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = productosTable.convertRowIndexToModel(selectedRow);
                    String codigo = (String) tableModel.getValueAt(modelRow, 0);
                    Producto producto = productoServicio.obtenerProductoPorCodigo(codigo);
                    if (producto != null) {
                        showStockDialog(producto);
                    }
                } else {
                    JOptionPane.showMessageDialog(ProductosPanel.this, 
                                                "Por favor, seleccione un producto para gestionar su stock.", 
                                                "Gestionar Stock", 
                                                JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        panel.add(newButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(editButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(stockButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(deleteButton);
        
        return panel;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void loadProductData() {
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        // Obtener lista de productos
        List<Producto> productos = productoServicio.listarProductos();
        
        // Agregar productos a la tabla
        for (Producto producto : productos) {
            Object[] rowData = {
                producto.getCodigo(),
                producto.getNombre(),
                producto.getDescripcion(),
                String.format("%.2f", producto.getPrecio()),
                producto.getStock(),
                producto.getCategoria(),
                producto.getProveedor()
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showProductFormDialog(Producto producto) {
        // Crear formulario para nuevo producto o editar existente
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                    producto == null ? "Nuevo Producto" : "Editar Producto", 
                                    true);
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Si es null, creamos un nuevo producto, sino editamos el existente
        final Producto productToEdit = producto == null ? new Producto() : producto;
        
        // Si es un nuevo producto, no mostrar campo de código
        // Declarar codigoField como final para poder usarlo en la expresión lambda
        final JTextField codigoField;
        if (producto != null) {
            codigoField = createFormField("Código:", productToEdit.getCodigo(), formPanel, gbc, 0);
            codigoField.setEditable(false); // No permitir editar el código
        } else {
            codigoField = null; // Inicializar a null si el producto es nuevo
        }
        
        // Campos del formulario
        JTextField nombreField = createFormField("Nombre:", productToEdit.getNombre(), formPanel, gbc, 1);
        
        JTextArea descripcionArea = new JTextArea(productToEdit.getDescripcion(), 3, 20);
        descripcionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        JScrollPane descripcionScroll = new JScrollPane(descripcionArea);
        createFormField("Descripción:", descripcionScroll, formPanel, gbc, 2);
        
        JTextField precioField = createFormField("Precio:", 
                                              productToEdit.getPrecio() > 0 ? 
                                              String.valueOf(productToEdit.getPrecio()) : "", 
                                              formPanel, gbc, 3);
        
        JTextField stockField = createFormField("Stock:", 
                                              String.valueOf(productToEdit.getStock()), 
                                              formPanel, gbc, 4);
        
        // Obtener categorías disponibles
        List<String> categorias = productoServicio.obtenerCategorias();
        JComboBox<String> categoriaCombo = new JComboBox<>();
        categoriaCombo.setEditable(true); // Permitir agregar nuevas categorías
        for (String categoria : categorias) {
            categoriaCombo.addItem(categoria);
        }
        if (productToEdit.getCategoria() != null && !productToEdit.getCategoria().isEmpty()) {
            categoriaCombo.setSelectedItem(productToEdit.getCategoria());
        }
        createFormField("Categoría:", categoriaCombo, formPanel, gbc, 5);
        
        // Obtener proveedores disponibles
        List<String> proveedores = productoServicio.obtenerProveedores();
        JComboBox<String> proveedorCombo = new JComboBox<>();
        proveedorCombo.setEditable(true); // Permitir agregar nuevos proveedores
        for (String proveedor : proveedores) {
            proveedorCombo.addItem(proveedor);
        }
        if (productToEdit.getProveedor() != null && !productToEdit.getProveedor().isEmpty()) {
            proveedorCombo.setSelectedItem(productToEdit.getProveedor());
        }
        createFormField("Proveedor:", proveedorCombo, formPanel, gbc, 6);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(_ -> dialog.dispose());
        
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(_ -> {
            try {
                // Actualizar objeto producto con datos del formulario
                if (codigoField != null) {
                    productToEdit.setCodigo(codigoField.getText());
                }
                productToEdit.setNombre(nombreField.getText());
                productToEdit.setDescripcion(descripcionArea.getText());
                
                double precio = 0;
                try {
                    precio = Double.parseDouble(precioField.getText().replace(",", "."));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                                                "El precio debe ser un número válido.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                    return;
                }
                productToEdit.setPrecio(precio);
                
                int stock = 0;
                try {
                    stock = Integer.parseInt(stockField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                                                "El stock debe ser un número entero.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                    return;
                }
                productToEdit.setStock(stock);
                
                productToEdit.setCategoria((String) categoriaCombo.getSelectedItem());
                productToEdit.setProveedor((String) proveedorCombo.getSelectedItem());
                
                boolean success;
                if (producto == null) {
                    // Crear nuevo producto
                    success = productoServicio.crearProducto(productToEdit);
                } else {
                    // Actualizar producto existente
                    success = productoServicio.actualizarProducto(productToEdit);
                }
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog, 
                                                "Producto guardado con éxito.", 
                                                "Éxito", 
                                                JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadProductData(); // Recargar datos
                } else {
                    JOptionPane.showMessageDialog(dialog, 
                                                "Error al guardar producto.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                                            "Error al procesar los datos: " + ex.getMessage(), 
                                            "Error", 
                                            JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Añadir componentes al diálogo
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showStockDialog(Producto producto) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                    "Gestionar Stock", 
                                    true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Mostrar información del producto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("Producto: " + producto.getNombre() + " (Stock actual: " + producto.getStock() + ")");
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(infoLabel, gbc);
        
        // Campo para la cantidad
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel cantidadLabel = new JLabel("Cantidad a ajustar:");
        cantidadLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(cantidadLabel, gbc);
        
        gbc.gridx = 1;
        JTextField cantidadField = new JTextField(10);
        cantidadField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(cantidadField, gbc);
        
        // Opciones de ajuste
        gbc.gridx = 0;
        gbc.gridy = 2;
        JRadioButton addButton = new JRadioButton("Agregar al stock", true);
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(addButton, gbc);
        
        gbc.gridx = 1;
        JRadioButton removeButton = new JRadioButton("Quitar del stock", false);
        removeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(removeButton, gbc);
        
        // Agrupar botones radio
        ButtonGroup group = new ButtonGroup();
        group.add(addButton);
        group.add(removeButton);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(_ -> dialog.dispose());
        
        JButton saveButton = new JButton("Actualizar Stock");
        saveButton.addActionListener(_ -> {
            try {
                int cantidad = Integer.parseInt(cantidadField.getText());
                
                // Si es quitar, convertir a negativo
                if (removeButton.isSelected()) {
                    cantidad = -cantidad;
                }
                
                boolean success = productoServicio.actualizarStock(producto.getCodigo(), cantidad);
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog, 
                                                "Stock actualizado con éxito.", 
                                                "Éxito", 
                                                JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadProductData(); // Recargar datos
                } else {
                    JOptionPane.showMessageDialog(dialog, 
                                                "Error al actualizar el stock. Verifique que haya suficiente stock disponible.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                                            "Por favor, ingrese un número válido.", 
                                            "Error", 
                                            JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private JTextField createFormField(String label, String value, JPanel panel, GridBagConstraints gbc, int row) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(jLabel, gbc);
        
        JTextField textField = new JTextField(value);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(textField, gbc);
        
        return textField;
    }
    
    private void createFormField(String label, JComponent component, JPanel panel, GridBagConstraints gbc, int row) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(jLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(component, gbc);
    }
    
    // Método para actualizar la tabla (puede ser llamado desde fuera)
    public void refreshTable() {
        loadProductData();
    }
}
