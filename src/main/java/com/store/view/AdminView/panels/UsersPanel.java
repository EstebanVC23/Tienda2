package com.store.view.AdminView.panels;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class UsersPanel extends JPanel {
    private UsuarioServicio usuarioServicio;
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public UsersPanel(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
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
        JLabel titleLabel = new JLabel("Gestión de Usuarios");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(240, 245, 249));
        
        // Combobox para filtrar por estado
        JLabel stateLabel = new JLabel("Estado:");
        stateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        String[] estados = {"Todos", "Activo", "Inactivo"};
        JComboBox<String> estadoCombo = new JComboBox<>(estados);
        estadoCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        estadoCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters(searchField.getText(), estadoCombo.getSelectedItem().toString());
            }
        });
        
        // Campo de búsqueda por texto
        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilters(searchField.getText(), estadoCombo.getSelectedItem().toString());
            }
        });
        
        searchPanel.add(stateLabel);
        searchPanel.add(estadoCombo);
        searchPanel.add(Box.createHorizontalStrut(15));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(searchPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    // Método auxiliar para aplicar los filtros combinados
    private void applyFilters(String searchText, String estadoSeleccionado) {
        RowFilter<DefaultTableModel, Object> textFilter = null;
        RowFilter<DefaultTableModel, Object> estadoFilter = null;
        
        // Crear filtro de texto si hay texto en el campo de búsqueda
        if (searchText != null && !searchText.trim().isEmpty()) {
            textFilter = RowFilter.regexFilter("(?i)" + searchText);
        }
        
        // Crear filtro de estado si no está seleccionado "Todos"
        if (!estadoSeleccionado.equals("Todos")) {
            estadoFilter = RowFilter.regexFilter("^" + estadoSeleccionado + "$", 7); // La columna 7 es "Estado"
        }
        
        // Aplicar filtros combinados
        if (textFilter != null && estadoFilter != null) {
            // Ambos filtros activos
            List<RowFilter<DefaultTableModel, Object>> filters = List.of(textFilter, estadoFilter);
            sorter.setRowFilter(RowFilter.andFilter(filters));
        } else if (textFilter != null) {
            // Solo filtro de texto
            sorter.setRowFilter(textFilter);
        } else if (estadoFilter != null) {
            // Solo filtro de estado
            sorter.setRowFilter(estadoFilter);
        } else {
            // Sin filtros
            sorter.setRowFilter(null);
        }
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.setBackground(new Color(240, 245, 249));
        
        // Crear modelo de tabla
        String[] columnNames = {"ID", "Nombre", "Apellido", "Email", "Documento", "Teléfono", "Rol", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla sea no editable
            }
        };
        
        // Crear tabla
        usersTable = new JTable(tableModel);
        usersTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        usersTable.setRowHeight(25);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersTable.setAutoCreateRowSorter(true);
        
        // Configurar el ordenador de filas
        sorter = new TableRowSorter<>(tableModel);
        usersTable.setRowSorter(sorter);
        
        // Cargar datos de usuarios
        loadUserData();
        
        // Panel de desplazamiento para la tabla
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(240, 245, 249));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Botones de acción
        JButton newButton = createButton("Nuevo Usuario", new Color(76, 175, 80));
        JButton editButton = createButton("Editar", new Color(33, 150, 243));
        JButton deleteButton = createButton("Eliminar", new Color(244, 67, 54));
        
        // Acciones de los botones
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserFormDialog(null);
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = usersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = usersTable.convertRowIndexToModel(selectedRow);
                    int userId = (Integer) tableModel.getValueAt(modelRow, 0);
                    Usuario usuario = usuarioServicio.obtenerUsuarioPorId(userId);
                    if (usuario != null) {
                        showUserFormDialog(usuario);
                    }
                } else {
                    JOptionPane.showMessageDialog(UsersPanel.this, 
                                                "Por favor, seleccione un usuario para editar.", 
                                                "Editar Usuario", 
                                                JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = usersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = usersTable.convertRowIndexToModel(selectedRow);
                    int userId = (Integer) tableModel.getValueAt(modelRow, 0);
                    
                    int option = JOptionPane.showConfirmDialog(UsersPanel.this, 
                                                             "¿Está seguro de eliminar este usuario?", 
                                                             "Eliminar Usuario", 
                                                             JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        boolean success = usuarioServicio.eliminarUsuario(userId);
                        if (success) {
                            JOptionPane.showMessageDialog(UsersPanel.this, 
                                                        "Usuario eliminado con éxito.", 
                                                        "Eliminar Usuario", 
                                                        JOptionPane.INFORMATION_MESSAGE);
                            loadUserData(); // Recargar datos
                        } else {
                            JOptionPane.showMessageDialog(UsersPanel.this, 
                                                        "Error al eliminar el usuario.", 
                                                        "Error", 
                                                        JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(UsersPanel.this, 
                                                "Por favor, seleccione un usuario para eliminar.", 
                                                "Eliminar Usuario", 
                                                JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        panel.add(newButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(editButton);
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
    
    private void loadUserData() {
        // Limpiar tabla
        tableModel.setRowCount(0);
        
        // Obtener lista de usuarios
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        
        // Agregar usuarios a la tabla
        for (Usuario usuario : usuarios) {
            Object[] rowData = {
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTipoDocumento() + " " + usuario.getNumeroDocumento(),
                usuario.getTelefono(),
                usuario.getRol(),
                usuario.isEstadoActivo() ? "Activo" : "Inactivo"
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showUserFormDialog(Usuario usuario) {
        // Crear formulario para nuevo usuario o editar existente
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                    usuario == null ? "Nuevo Usuario" : "Editar Usuario", 
                                    true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Si es null, creamos un nuevo usuario, sino editamos el existente
        final Usuario userToEdit = usuario == null ? new Usuario() : usuario;
        
        // Campos del formulario
        JTextField nombreField = createFormField("Nombre:", userToEdit.getNombre(), formPanel, gbc, 0);
        JTextField apellidoField = createFormField("Apellido:", userToEdit.getApellido(), formPanel, gbc, 1);
        JTextField emailField = createFormField("Email:", userToEdit.getEmail(), formPanel, gbc, 2);
        
        String[] tiposDocumento = {"DNI", "Pasaporte", "Cédula"};
        JComboBox<String> tipoDocumentoCombo = new JComboBox<>(tiposDocumento);
        tipoDocumentoCombo.setSelectedItem(userToEdit.getTipoDocumento().isEmpty() ? 
                                          tiposDocumento[0] : userToEdit.getTipoDocumento());
        createFormField("Tipo Documento:", tipoDocumentoCombo, formPanel, gbc, 3);
        
        JTextField documentoField = createFormField("Número Documento:", userToEdit.getNumeroDocumento(), formPanel, gbc, 4);
        JTextField direccionField = createFormField("Dirección:", userToEdit.getDireccion(), formPanel, gbc, 5);
        JTextField telefonoField = createFormField("Teléfono:", userToEdit.getTelefono(), formPanel, gbc, 6);
        
        // Campo de contraseña - siempre vacío al editar
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Si estamos editando un usuario existente, agregar nota sobre la contraseña
        String passwordLabel = "Contraseña:";
        if (usuario != null) {
            passwordLabel = "Nueva contraseña:";
        }
        createFormField(passwordLabel, passwordField, formPanel, gbc, 7);
        
            gbc.gridy = 8;
        
        String[] roles = {"ADMIN", "USER"};
        JComboBox<String> rolCombo = new JComboBox<>(roles);
        rolCombo.setSelectedItem(userToEdit.getRol().isEmpty() ? roles[1] : userToEdit.getRol());
        createFormField("Rol:", rolCombo, formPanel, gbc, gbc.gridy);
        
        JCheckBox activoCheck = new JCheckBox("Usuario Activo", userToEdit.isEstadoActivo());
        activoCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        formPanel.add(activoCheck, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(_ -> dialog.dispose());
        
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(_ -> {
            // Actualizar objeto usuario con datos del formulario
            userToEdit.setNombre(nombreField.getText());
            userToEdit.setApellido(apellidoField.getText());
            userToEdit.setEmail(emailField.getText());
            userToEdit.setTipoDocumento((String) tipoDocumentoCombo.getSelectedItem());
            userToEdit.setNumeroDocumento(documentoField.getText());
            userToEdit.setDireccion(direccionField.getText());
            userToEdit.setTelefono(telefonoField.getText());
            
            // Actualizar contraseña solo si se ingresó una nueva
            String newPassword = new String(passwordField.getPassword());
            if (!newPassword.isEmpty()) {
                userToEdit.setPassword(newPassword);
            }
            
            userToEdit.setRol((String) rolCombo.getSelectedItem());
            userToEdit.setEstadoActivo(activoCheck.isSelected());
            
            boolean success;
            if (usuario == null) {
                // Crear nuevo usuario - contraseña obligatoria
                if (newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                                                "Debe ingresar una contraseña para el nuevo usuario.", 
                                                "Error", 
                                                JOptionPane.ERROR_MESSAGE);
                    return;
                }
                success = usuarioServicio.crearUsuario(userToEdit);
            } else {
                // Actualizar usuario existente
                success = usuarioServicio.actualizarUsuario(userToEdit);
            }
            
            if (success) {
                JOptionPane.showMessageDialog(dialog, 
                                            "Usuario guardado con éxito.", 
                                            "Éxito", 
                                            JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                loadUserData(); // Recargar datos
            } else {
                JOptionPane.showMessageDialog(dialog, 
                                            "Error al guardar usuario.", 
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
        loadUserData();
    }
}
