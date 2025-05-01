package com.store.view.components.cards.spaces;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import com.store.view.components.cards.constants.SearchHeaderConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class ProductSearchHeader extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> categoriaCombo;
    private final SearchHeaderConstants constants;

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

    public ProductSearchHeader(List<String> categorias) {
        this(categorias, new SearchHeaderConstants());
    }

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

    private JPanel createSearchFilterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 
            constants.FILTER_HGAP, constants.FILTER_VGAP));
        panel.setBackground(Color.WHITE);
        panel.add(createSearchPanel());
        panel.add(createFilterPanel());
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(
            constants.SEARCH_HGAP, constants.SEARCH_VGAP));
        panel.setBackground(Color.WHITE);
        panel.add(createLabel(constants.SEARCH_LABEL, Fonts.SECTION_TITLE, Colors.PRIMARY_TEXT), 
                BorderLayout.NORTH);
        panel.add(createInputContainer(searchField), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout(
            constants.FILTER_INNER_HGAP, constants.FILTER_INNER_VGAP));
        panel.setBackground(Color.WHITE);
        panel.add(createLabel(constants.FILTER_LABEL, Fonts.SECTION_TITLE, Colors.PRIMARY_TEXT), 
                BorderLayout.NORTH);
        panel.add(createInputContainer(categoriaCombo), BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

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

    private JTextField createSearchField() {
        JTextField field = new JTextField();
        field.setFont(Fonts.BODY);
        field.setBorder(new EmptyBorder(
            constants.FIELD_PADDING, constants.FIELD_PADDING, 
            constants.FIELD_PADDING, constants.FIELD_PADDING));
        return field;
    }

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

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getCategoriaCombo() {
        return categoriaCombo;
    }
}