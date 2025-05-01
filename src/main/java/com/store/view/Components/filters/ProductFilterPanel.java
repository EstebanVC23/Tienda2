package com.store.view.components.filters;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductFilterPanel extends JPanel {
    private final JTextField searchField;
    private final JComboBox<String> categoryCombo;

    public ProductFilterPanel(List<String> categories) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.BORDER),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(Fonts.SECTION_TITLE);
        searchLabel.setForeground(Colors.PRIMARY_TEXT);

        searchField = new JTextField(20);
        searchField.setFont(Fonts.BODY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Panel de categorías
        JPanel categoryPanel = new JPanel(new BorderLayout(10, 0));
        categoryPanel.setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Categoría:");
        categoryLabel.setFont(Fonts.SECTION_TITLE);
        categoryLabel.setForeground(Colors.PRIMARY_TEXT);

        categoryCombo = new JComboBox<>();
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

        add(searchPanel);
        add(categoryPanel);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getCategoryCombo() {
        return categoryCombo;
    }
}