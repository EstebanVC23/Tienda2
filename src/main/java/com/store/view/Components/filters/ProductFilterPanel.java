package com.store.view.components.filters;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel de filtros para productos que combina un campo de búsqueda
 * y un selector de categorías.
 */
public class ProductFilterPanel extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> categoryCombo;

    /**
     * Crea un panel de filtros para productos con las categorías especificadas.
     * 
     * @param categories Lista de categorías disponibles para filtrar
     */
    public ProductFilterPanel(List<String> categories) {
        // Inicialización directa de los campos final
        this.searchField = new JTextField(20);
        this.categoryCombo = new JComboBox<>();
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        add(createSearchPanel());
        add(createCategoryPanel(categories));
    }

    /**
     * Crea el panel de búsqueda con un campo de texto.
     * 
     * @return JPanel con el campo de búsqueda
     */
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(Fonts.SECTION_TITLE);
        searchLabel.setForeground(Colors.PRIMARY_TEXT);

        searchField.setFont(Fonts.BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        return searchPanel;
    }

    /**
     * Crea el panel de selección de categorías con un combobox.
     * 
     * @param categories Lista de categorías disponibles
     * @return JPanel con el combobox de categorías
     */
    private JPanel createCategoryPanel(List<String> categories) {
        JPanel categoryPanel = new JPanel(new BorderLayout(10, 0));
        categoryPanel.setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Categoría:");
        categoryLabel.setFont(Fonts.SECTION_TITLE);
        categoryLabel.setForeground(Colors.PRIMARY_TEXT);

        categoryCombo.setFont(Fonts.BODY);
        categoryCombo.setBackground(Color.WHITE);
        categoryCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        categoryCombo.addItem("Todas");
        categories.forEach(categoryCombo::addItem);

        categoryPanel.add(categoryLabel, BorderLayout.WEST);
        categoryPanel.add(categoryCombo, BorderLayout.CENTER);

        return categoryPanel;
    }

    /**
     * Obtiene el campo de texto para búsqueda.
     * 
     * @return Referencia al JTextField de búsqueda
     */
    public JTextField getSearchField() {
        return searchField;
    }

    /**
     * Obtiene el combobox de selección de categorías.
     * 
     * @return Referencia al JComboBox de categorías
     */
    public JComboBox<String> getCategoryCombo() {
        return categoryCombo;
    }
}