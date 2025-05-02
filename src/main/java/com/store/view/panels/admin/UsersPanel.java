package com.store.view.panels.admin;

import com.store.models.Usuario;
import com.store.services.UsuarioServicioImpl;
import com.store.utils.Colors;
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

/**
 * Panel de administración para la gestión de usuarios.
 * Proporciona funcionalidades CRUD completas (Crear, Leer, Actualizar, Eliminar)
 * para usuarios, con capacidades de filtrado y búsqueda avanzada.
 */
public class UsersPanel extends CrudPanel<Usuario> {
    private final UsuarioServicioImpl usuarioServicio;
    private String currentSearchText = "";
    private String currentFilterOption = "Todos";
    private TableRowSorter<DefaultTableModel> sorter;
    
    private static final String[] COLUMN_NAMES = {
        "ID", "Nombre", "Apellido", "Email", "Documento", "Teléfono", "Rol", "Estado"
    };
    
    private static final Color NEW_BTN_COLOR = Colors.SUCCESS_GREEN;
    private static final Color EDIT_BTN_COLOR = Colors.PRIMARY_BLUE;
    private static final Color DELETE_BTN_COLOR = Colors.ERROR_RED;
    
    private static final String[] FILTER_OPTIONS = {"Todos", "Activo", "Inactivo"};

    /**
     * Constructor del panel de gestión de usuarios.
     * @param usuarioServicio Servicio para operaciones con usuarios
     */
    public UsersPanel(UsuarioServicioImpl usuarioServicio) {
        super("Gestión de Usuarios");
        this.usuarioServicio = usuarioServicio;
        initializeSorter();
        refreshTable();
    }

    /**
     * Inicializa el ordenador de filas para la tabla.
     */
    private void initializeSorter() {
        sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);
    }

    /**
     * Configura el panel de filtros para la búsqueda de usuarios.
     * Se utiliza un panel de filtro personalizado que permite buscar usuarios
     * por nombre, apellido, email, etc.
     */
    @Override
    protected void setupFilterPanel() {
        filterPanel = new AdminFilterPanel("Buscar usuarios:", FILTER_OPTIONS, this::applyFilters);
    }

    /**
     * Crea el panel de botones de acción para el CRUD de usuarios.
     * Incluye botones para crear, editar y eliminar usuarios.
     * @return Panel con los botones de acción
     */
    @Override
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Colors.PANEL_BACKGROUND);
        
        buttonPanel.add(createActionButton("Nuevo Usuario", NEW_BTN_COLOR, 
            () -> showUserFormDialog(null)));
        buttonPanel.add(createActionButton("Editar", EDIT_BTN_COLOR, 
            this::editSelectedUser));
        buttonPanel.add(createActionButton("Eliminar", DELETE_BTN_COLOR, 
            this::deleteSelectedUser));
        
        return buttonPanel;
    }

    /**
     * Crea el panel de la tabla de usuarios.
     * Configura la tabla con los nombres de las columnas y el modelo de datos.
     * @return Panel con la tabla de usuarios
     */
    @Override
    protected JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.PANEL_BACKGROUND);
        
        this.table = new CustomTable(COLUMN_NAMES);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Refresca la tabla de usuarios con los datos actuales del servicio.
     * Limpia la tabla y vuelve a cargar los datos desde el servicio.
     */
    @Override
    public void refreshTable() {
        saveCurrentFilterState();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        for (Usuario u : usuarios) {
            model.addRow(createUserRowData(u));
        }
        
        reapplyFilters();
    }

    /**
     * Guarda el estado actual de los filtros.
     */
    private void saveCurrentFilterState() {
        if (filterPanel != null) {
            currentSearchText = filterPanel.getFilterText();
            currentFilterOption = filterPanel.getSelectedFilter();
        }
    }

    /**
     * Crea los datos de fila para un usuario.
     * @param usuario El usuario a mostrar en la fila
     * @return Array de objetos con los datos del usuario
     */
    private Object[] createUserRowData(Usuario usuario) {
        return new Object[]{
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            usuario.getTipoDocumento() + " " + usuario.getNumeroDocumento(),
            usuario.getTelefono(),
            usuario.getRol(),
            usuario.getEstado() ? "Activo" : "Inactivo"
        };
    }

    /**
     * Reaplica los filtros después de actualizar la tabla.
     */
    private void reapplyFilters() {
        applyFilters(currentSearchText);
    }

    /**
     * Aplica los filtros de búsqueda y estado a la tabla.
     * @param filterText Texto para filtrar los usuarios
     */
    public void applyFilters(String filterText) {
        currentSearchText = filterText;
        currentFilterOption = filterPanel.getSelectedFilter();
        
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();
        
        RowFilter<DefaultTableModel, Object> textFilter = createTextFilter(filterText);
        if (textFilter != null) filters.add(textFilter);
        
        RowFilter<DefaultTableModel, Object> estadoFilter = createEstadoFilter(currentFilterOption);
        if (estadoFilter != null) filters.add(estadoFilter);
        
        ensureSorterInitialized();
        applyCombinedFilters(filters);
    }

    /**
     * Crea el filtro de texto para búsqueda.
     */
    private RowFilter<DefaultTableModel, Object> createTextFilter(String filterText) {
        return filterText.trim().isEmpty() ? null : 
            RowFilter.regexFilter("(?i)" + filterText);
    }

    /**
     * Crea el filtro de estado (Activo/Inactivo).
     */
    private RowFilter<DefaultTableModel, Object> createEstadoFilter(String filterOption) {
        return "Todos".equals(filterOption) ? null : 
            RowFilter.regexFilter("^" + filterOption + "$", 7);
    }

    /**
     * Asegura que el ordenador de filas esté inicializado.
     */
    private void ensureSorterInitialized() {
        if (sorter == null) {
            initializeSorter();
        }
    }

    /**
     * Aplica los filtros combinados a la tabla.
     */
    private void applyCombinedFilters(List<RowFilter<DefaultTableModel, Object>> filters) {
        if (!filters.isEmpty()) {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        } else {
            sorter.setRowFilter(null);
        }
    }

    /**
     * Edita el usuario seleccionado en la tabla.
     */
    private void editSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorMessage("Seleccione un usuario");
            return;
        }
        
        int id = getSelectedUserId(selectedRow);
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
        if (usuario != null) {
            showUserFormDialog(usuario);
        }
    }

    /**
     * Elimina el usuario seleccionado previa confirmación.
     */
    private void deleteSelectedUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showErrorMessage("Seleccione un usuario");
            return;
        }
        
        if (confirmUserDeletion()) {
            int id = getSelectedUserId(selectedRow);
            boolean success = usuarioServicio.eliminarUsuario(id);
            if (success) {
                refreshTable();
                notifyRefresh();
            } else {
                showErrorMessage("Error al eliminar el usuario");
            }
        }
    }

    /**
     * Obtiene el ID del usuario seleccionado.
     */
    private int getSelectedUserId(int selectedRow) {
        return Integer.parseInt(table.getModel().getValueAt(
            table.convertRowIndexToModel(selectedRow), 0).toString());
    }

    /**
     * Muestra diálogo de confirmación para eliminar usuario.
     */
    private boolean confirmUserDeletion() {
        return JOptionPane.showConfirmDialog(this, 
            "¿Eliminar este usuario?", "Confirmar", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra el diálogo de formulario para crear/editar usuarios.
     * @param usuario Usuario a editar (null para crear nuevo)
     */
    private void showUserFormDialog(Usuario usuario) {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow instanceof JFrame) {
            UserFormDialog dialog = new UserFormDialog(
                (JFrame) parentWindow, usuario, usuarioServicio);
            
            configureDialogListeners(dialog);
            dialog.setVisible(true);
        }
    }

    /**
     * Configura los listeners para el diálogo de usuario.
     */
    private void configureDialogListeners(UserFormDialog dialog) {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTable();
                notifyRefresh();
                restoreFilterState();
            }
        });
    }

    /**
     * Restaura el estado de los filtros después de operaciones.
     */
    private void restoreFilterState() {
        if (filterPanel != null) {
            filterPanel.setFilterText(currentSearchText);
            filterPanel.setSelectedFilter(currentFilterOption);
        }
        applyFilters(currentSearchText);
    }

    /**
     * Muestra un mensaje de error al usuario.
     * @param message Mensaje de error a mostrar
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}