package com.store.view.panels.admin;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.view.components.CustomTable;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.dialogs.admin.UserFormDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UsersPanel extends CrudPanel<Usuario> {
    private final UsuarioServicio usuarioServicio;
    
    public UsersPanel(UsuarioServicio usuarioServicio) {
        super("Gestión de Usuarios");
        this.usuarioServicio = usuarioServicio;
        refreshTable();
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
        
        // Asegurar que la tabla esté dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void applyFilters(String filterText) {
        RowFilter<DefaultTableModel, Object> textFilter = filterText.trim().isEmpty() ? null : 
            RowFilter.regexFilter("(?i)" + filterText);
        
        String estado = filterPanel.getSelectedFilter();
        RowFilter<DefaultTableModel, Object> estadoFilter = "Todos".equals(estado) ? null : 
            RowFilter.regexFilter("^" + estado + "$", 7);
        
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();
        if (textFilter != null) filters.add(textFilter);
        if (estadoFilter != null) filters.add(estadoFilter);
        
        if (!filters.isEmpty()) {
            table.getSorter().setRowFilter(RowFilter.andFilter(filters));
        } else {
            table.getSorter().setRowFilter(null);
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
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUserFormDialog(Usuario usuario) {
        // Obtener el JFrame padre correctamente
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow instanceof JFrame) {
            UserFormDialog dialog = new UserFormDialog((JFrame) parentWindow, usuario, usuarioServicio);
            
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTable();
                    notifyRefresh();
                }
            });
            
            dialog.setVisible(true);
        }
    }

    @Override
    public void refreshTable() {
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
    }
}