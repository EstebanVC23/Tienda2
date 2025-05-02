package com.store.view.components.filters;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * Panel de filtros para interfaces de administración con campo de búsqueda
 * y selector de filtros opcional.
 */
public class AdminFilterPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> filterCombo;

    /**
     * Crea un panel de filtros para administración.
     * @param label Texto para la etiqueta del campo de búsqueda
     * @param filterOptions Opciones para el combobox de filtrado (puede ser null)
     * @param onFilterChange Callback que se ejecuta al cambiar cualquier filtro
     */
    public AdminFilterPanel(String label, String[] filterOptions, Consumer<String> onFilterChange) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setBackground(Color.WHITE);

        JPanel searchContainer = new JPanel(new BorderLayout(5, 0));
        searchContainer.setBackground(Color.WHITE);
        
        JLabel searchLabel = new JLabel(label);
        searchLabel.setFont(Fonts.SECTION_TITLE);
        searchLabel.setForeground(Colors.SECONDARY_TEXT);
        
        searchField = new JTextField(20);
        searchField.setFont(Fonts.BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SwingUtilities.invokeLater(() -> onFilterChange.accept(getFilterText()));
            }
        });
        
        searchContainer.add(searchLabel, BorderLayout.WEST);
        searchContainer.add(searchField, BorderLayout.CENTER);
        
        if (filterOptions != null) {
            JPanel filterContainer = new JPanel(new BorderLayout(5, 0));
            filterContainer.setBackground(Color.WHITE);
            
            JLabel filterLabel = new JLabel("Filtrar por:");
            filterLabel.setFont(Fonts.SECTION_TITLE);
            filterLabel.setForeground(Colors.SECONDARY_TEXT);
            
            filterCombo = new JComboBox<>(filterOptions);
            filterCombo.setFont(Fonts.BODY);
            filterCombo.setBackground(Color.WHITE);
            filterCombo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
            ));
            filterCombo.addActionListener(_ -> 
                SwingUtilities.invokeLater(() -> onFilterChange.accept(getFilterText())));
            
            filterContainer.add(filterLabel, BorderLayout.WEST);
            filterContainer.add(filterCombo, BorderLayout.CENTER);
            
            searchPanel.add(filterContainer);
            searchPanel.add(Box.createHorizontalStrut(20));
        }

        searchPanel.add(searchContainer);
        add(searchPanel, BorderLayout.EAST);
    }

    /**
     * Obtiene el texto actual del campo de búsqueda.
     * @return Texto ingresado en el campo de búsqueda
     */
    public String getFilterText() {
        return searchField != null ? searchField.getText() : "";
    }

    /**
     * Obtiene el filtro seleccionado actualmente.
     * @return Opción seleccionada en el combobox o "Todos" si no hay combobox
     */
    public String getSelectedFilter() {
        return filterCombo != null ? (String) filterCombo.getSelectedItem() : "Todos";
    }

    /**
     * Establece el texto del campo de búsqueda.
     * @param text Texto a establecer en el campo de búsqueda
     */
    public void setFilterText(String text) {
        if (searchField != null) {
            searchField.setText(text);
        }
    }

    /**
     * Establece el filtro seleccionado en el combobox.
     * @param filter Opción a seleccionar en el combobox
     */
    public void setSelectedFilter(String filter) {
        if (filterCombo != null) {
            filterCombo.setSelectedItem(filter);
        }
    }
}