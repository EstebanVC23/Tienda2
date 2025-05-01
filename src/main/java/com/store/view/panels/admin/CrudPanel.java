package com.store.view.panels.admin;

import com.store.view.panels.BasePanel;
import com.store.view.components.TitlePanel;
import com.store.view.components.buttons.CustomButton;
import com.store.view.components.CustomTable;
import com.store.view.components.filters.AdminFilterPanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CrudPanel<T> extends BasePanel {
    protected final String title;
    protected List<Runnable> refreshListeners = new ArrayList<>();
    protected JScrollPane scrollPane;
    protected CustomTable table;
    protected AdminFilterPanel filterPanel;
    
    public CrudPanel(String title) {
        this.title = title;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeUI();
    }
    
    private void initializeUI() {
        add(new TitlePanel(title), BorderLayout.NORTH);
        
        setupFilterPanel();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        if (filterPanel != null) {
            contentPanel.add(filterPanel, BorderLayout.NORTH);
        }
        
        JPanel tablePanel = createTablePanel();
        scrollPane = new JScrollPane(tablePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setBackground(Color.WHITE);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    protected abstract void setupFilterPanel();
    protected abstract JPanel createButtonPanel();
    protected abstract JPanel createTablePanel();
    
    protected CustomButton createActionButton(String text, Color color, Runnable action) {
        CustomButton button = new CustomButton(text, color);
        button.addActionListener(_ -> action.run());
        return button;
    }

    public void addRefreshListener(Runnable listener) {
        refreshListeners.add(listener);
    }

    protected void notifyRefresh() {
        refreshListeners.forEach(Runnable::run);
    }

    @Override
    public abstract void refreshTable();
}