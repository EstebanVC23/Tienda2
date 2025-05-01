package com.store.view.panels.admin;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.tables.CustomTable;
import com.store.view.components.dialogs.admin.UserFormDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UsersPanel extends CrudPanel<Usuario> {
    private final UsuarioServicio usuarioServicio;
    private String currentSearchText = "";
    private String currentFilterOption = "Todos";
    private TableRowSorter<DefaultTableModel> sorter;

    public UsersPanel(UsuarioServicio usuarioServicio) {
        super("Gestión de Usuarios");
        this.usuarioServicio = usuarioServicio;
        initializeSorter();
        refreshTable();
    }

    private void initializeSorter() {
        sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);
    }

    @Override
    protected void setupFilterPanel() {
        filterPanel = new AdminFilterPanel("Buscar:", new String[]{"Todos", "Activo", "Inactivo"}, this::applyFilters);
    }

    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(createActionButton("Nuevo Usuario", new Color(76, 175, 80), () -> showUserFormDialog(null)));
        buttonPanel.add(createActionButton("Editar", new Color(33, 150, 243), this::editSelectedUser));
        buttonPanel.add(createActionButton("Eliminar", new Color(244, 67, 54), this::deleteSelectedUser));
        return buttonPanel;
    }

    @Override
    protected JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        String[] columnNames = {"ID", "Nombre", "Apellido", "Email", "Documento", "Teléfono", "Rol", "Estado"};
        this.table = new CustomTable(columnNames);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    @Override
    public void refreshTable() {
        // Guardar el estado actual de los filtros
        if (filterPanel != null) {
            currentSearchText = filterPanel.getFilterText();
            currentFilterOption = filterPanel.getSelectedFilter();
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        for (Usuario u : usuarios) {
            model.addRow(new Object[]{
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTipoDocumento() + " " + u.getNumeroDocumento(),
                u.getTelefono(),
                u.getRol(),
                u.getEstado() ? "Activo" : "Inactivo"
            });
        }
        
        // Reaplicar los filtros después de actualizar
        applyFilters(currentSearchText);
    }

    public void applyFilters(String filterText) {
        currentSearchText = filterText;
        currentFilterOption = filterPanel.getSelectedFilter();
        
        RowFilter<DefaultTableModel, Object> textFilter = filterText.trim().isEmpty() ? null : 
            RowFilter.regexFilter("(?i)" + filterText);
        
        RowFilter<DefaultTableModel, Object> estadoFilter = "Todos".equals(currentFilterOption) ? null : 
            RowFilter.regexFilter("^" + currentFilterOption + "$", 7);
        
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();
        if (textFilter != null) filters.add(textFilter);
        if (estadoFilter != null) filters.add(estadoFilter);
        
        if (sorter == null) {
            initializeSorter();
        }
        
        if (!filters.isEmpty()) {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        } else {
            sorter.setRowFilter(null);
        }
    }

    private void editSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int id = Integer.parseInt(table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString());
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
        if (usuario != null) showUserFormDialog(usuario);
    }

    private void deleteSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Eliminar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(table.getModel().getValueAt(table.convertRowIndexToModel(selectedRow), 0).toString());
            boolean success = usuarioServicio.eliminarUsuario(id);
            if (success) {
                refreshTable();
                notifyRefresh();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUserFormDialog(Usuario usuario) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow instanceof JFrame) {
            UserFormDialog dialog = new UserFormDialog((JFrame) parentWindow, usuario, usuarioServicio);
            
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                    notifyRefresh();
                    if (filterPanel != null) {
                        filterPanel.setFilterText(currentSearchText);
                        filterPanel.setSelectedFilter(currentFilterOption);
                    }
                    applyFilters(currentSearchText);
                }
            });
            
            dialog.setVisible(true);
        }
    }
}