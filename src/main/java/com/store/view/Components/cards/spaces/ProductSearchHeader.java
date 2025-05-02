package com.store.view.components.cards.spaces;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.cards.constants.SearchHeaderConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

/**
 * Componente de encabezado para búsqueda y filtrado de productos.
 * Proporciona campos para búsqueda por texto y filtrado por categoría.
 */
public class ProductSearchHeader extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> categoriaCombo;
    private final SearchHeaderConstants constants;

    /**
     * Constructor principal que inicializa el componente con constantes personalizadas.
     * @param categorias Lista de categorías disponibles para filtrado
     * @param constants Constantes de configuración del componente
     */
    public ProductSearchHeader(List<String> categorias, SearchHeaderConstants constants) {
        this.constants = constants;
        setLayout(new BorderLayout(constants.LAYOUT_HGAP, constants.LAYOUT_VGAP));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            new EmptyBorder(constants.PADDING_TOP, constants.PADDING_LEFT, 
                          constants.PADDING_BOTTOM, constants.PADDING_RIGHT)
        ));

        this.searchField = createSearchField();
        this.categoriaCombo = createCategoryCombo(categorias);
        
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createSearchFilterPanel(), BorderLayout.CENTER);
    }

    /**
     * Constructor alternativo que usa constantes predeterminadas.
     * @param categorias Lista de categorías disponibles para filtrado
     */
    public ProductSearchHeader(List<String> categorias) {
        this(categorias, new SearchHeaderConstants());
    }

    /**
     * Crea el panel de título con texto principal y secundario.
     * @return JPanel configurado con los títulos
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(constants.TITLE_TEXT);
        titleLabel.setFont(Fonts.TITLE);
        titleLabel.setForeground(Colors.PRIMARY_TEXT);

        JLabel subtitleLabel = new JLabel(constants.SUBTITLE_TEXT);
        subtitleLabel.setFont(Fonts.SMALL);
        subtitleLabel.setForeground(Colors.SECONDARY_TEXT);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return panel;
    }

    /**
     * Crea el panel principal de búsqueda y filtrado.
     * @return JPanel que contiene ambos componentes
     */
    private JPanel createSearchFilterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 
            constants.FILTER_HGAP, constants.FILTER_VGAP));
        panel.setBackground(Color.WHITE);
        panel.add(createSearchPanel());
        panel.add(createFilterPanel());
        return panel;
    }

    /**
     * Crea el panel de búsqueda con campo de texto.
     * @return JPanel configurado para búsqueda
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(
            constants.SEARCH_HGAP, constants.SEARCH_VGAP));
        panel.setBackground(Color.WHITE);
        panel.add(createLabel(constants.SEARCH_LABEL, Fonts.SECTION_TITLE, Colors.PRIMARY_TEXT), 
                BorderLayout.NORTH);
        panel.add(createInputContainer(searchField), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de filtrado con combo box de categorías.
     * @return JPanel configurado para filtrado
     */
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout(
            constants.FILTER_INNER_HGAP, constants.FILTER_INNER_VGAP));
        panel.setBackground(Color.WHITE);
        panel.add(createLabel(constants.FILTER_LABEL, Fonts.SECTION_TITLE, Colors.PRIMARY_TEXT), 
                BorderLayout.NORTH);
        panel.add(createInputContainer(categoriaCombo), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea una etiqueta con estilo consistente.
     * @param text Texto a mostrar
     * @param font Fuente a utilizar
     * @param color Color del texto
     * @return JLabel configurado
     */
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Crea un contenedor para componentes de entrada.
     * @param component Componente a contener
     * @return JPanel con el componente y estilo aplicado
     */
    private JPanel createInputContainer(JComponent component) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            new EmptyBorder(0, 0, 0, 0)
        ));
        container.add(component, BorderLayout.CENTER);
        return container;
    }

    /**
     * Crea el campo de búsqueda con estilo consistente.
     * @return JTextField configurado
     */
    private JTextField createSearchField() {
        JTextField field = new JTextField();
        field.setFont(Fonts.BODY);
        field.setBorder(new EmptyBorder(
            constants.FIELD_PADDING, constants.FIELD_PADDING, 
            constants.FIELD_PADDING, constants.FIELD_PADDING));
        return field;
    }

    /**
     * Crea el combo box de categorías con estilo consistente.
     * @param categorias Lista de categorías disponibles
     * @return JComboBox configurado
     */
    private JComboBox<String> createCategoryCombo(List<String> categorias) {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(Fonts.BODY);
        combo.setBackground(Color.WHITE);
        combo.setBorder(new EmptyBorder(
            constants.FIELD_PADDING, constants.FIELD_PADDING, 
            constants.FIELD_PADDING, constants.FIELD_PADDING));
        combo.addItem(constants.DEFAULT_CATEGORY);
        categorias.forEach(combo::addItem);
        return combo;
    }

    /**
     * Obtiene el campo de búsqueda.
     * @return JTextField del campo de búsqueda
     */
    public JTextField getSearchField() {
        return searchField;
    }

    /**
     * Obtiene el combo box de categorías.
     * @return JComboBox de categorías
     */
    public JComboBox<String> getCategoriaCombo() {
        return categoriaCombo;
    }
}