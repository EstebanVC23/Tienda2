package com.store.view.panels.admin;

import com.store.view.panels.BasePanel;
import com.store.view.components.TitlePanel;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.filters.AdminFilterPanel;
import com.store.view.components.tables.CustomTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel abstracto base para operaciones CRUD (Crear, Leer, Actualizar, Eliminar).
 * Proporciona una estructura común para interfaces de administración con filtros,
 * tabla de datos y botones de acción.
 *
 * @param <T> Tipo de entidad que manejará el panel CRUD
 */
public abstract class CrudPanel<T> extends BasePanel {
    protected final String title;
    protected final List<Runnable> refreshListeners = new ArrayList<>();
    protected JScrollPane scrollPane;
    protected CustomTable table;
    protected AdminFilterPanel filterPanel;
    
    /**
     * Construye un nuevo panel CRUD con el título especificado.
     * @param title Título que se mostrará en la parte superior del panel
     */
    public CrudPanel(String title) {
        this.title = title;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeUI();
    }
    
    /**
     * Inicializa la interfaz de usuario del panel.
     */
    private void initializeUI() {
        add(new TitlePanel(title), BorderLayout.NORTH);
        
        setupFilterPanel();
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Crea el panel de contenido principal que contiene los filtros, tabla y botones.
     * @return Panel de contenido configurado
     */
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        if (filterPanel != null) {
            contentPanel.add(filterPanel, BorderLayout.NORTH);
        }
        
        JPanel tablePanel = createTablePanel();
        configureScrollPane(tablePanel);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        return contentPanel;
    }
    
    /**
     * Configura el JScrollPane que contendrá la tabla de datos.
     * @param tablePanel Panel que contiene la tabla
     */
    private void configureScrollPane(JPanel tablePanel) {
        scrollPane = new JScrollPane(tablePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setBackground(Color.WHITE);
    }
    
    /**
     * Crea un botón de acción con estilo consistente.
     * @param text Texto del botón
     * @param color Color de fondo del botón
     * @param action Acción a ejecutar al hacer clic
     * @return Botón configurado
     */
    protected CustomButton createActionButton(String text, Color color, Runnable action) {
        CustomButton button = new CustomButton(text, color);
        button.addActionListener(_ -> action.run());
        return button;
    }

    /**
     * Agrega un listener para ser notificado cuando se requiera refrescar los datos.
     * @param listener Runnable a ejecutar al refrescar
     */
    public void addRefreshListener(Runnable listener) {
        refreshListeners.add(listener);
    }

    /**
     * Notifica a todos los listeners registrados que deben refrescar sus datos.
     */
    protected void notifyRefresh() {
        refreshListeners.forEach(Runnable::run);
    }

    // Métodos abstractos que deben ser implementados por las clases concretas
    
    /**
     * Configura el panel de filtros específico para la entidad.
     */
    protected abstract void setupFilterPanel();
    
    /**
     * Crea el panel con los botones de acciones CRUD.
     * @return Panel de botones configurado
     */
    protected abstract JPanel createButtonPanel();
    
    /**
     * Crea el panel que contiene la tabla de datos.
     * @return Panel de tabla configurado
     */
    protected abstract JPanel createTablePanel();
    
    /**
     * Actualiza los datos mostrados en la tabla.
     */
    @Override
    public abstract void refreshTable();
}