package com.store.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private Consumer<String> searchCallback;

    public SearchPanel(String labelText, Consumer<String> searchCallback) {
        this(labelText, "", searchCallback);
    }

    public SearchPanel(String labelText, String placeholderText, Consumer<String> searchCallback) {
        this.searchCallback = searchCallback;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 249));

        JLabel searchLabel = new JLabel(labelText);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchIcon.setBorder(new EmptyBorder(0, 5, 0, 5));

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        if (!placeholderText.isEmpty()) {
            UIUtils.setPlaceholder(searchField, placeholderText);
        }
        
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (searchCallback != null) {
                    searchCallback.accept(searchField.getText());
                }
            }
        });

        JPanel searchInputPanel = new JPanel(new BorderLayout());
        searchInputPanel.setBackground(Color.WHITE);
        searchInputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(207, 216, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        searchInputPanel.add(searchIcon, BorderLayout.WEST);
        searchInputPanel.add(searchField, BorderLayout.CENTER);

        add(searchLabel, BorderLayout.NORTH);
        add(searchInputPanel, BorderLayout.CENTER);
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void setSearchText(String text) {
        searchField.setText(text);
    }

    public JTextField getSearchField() {
        return searchField;
    }
}