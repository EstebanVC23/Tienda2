package com.store.view.panels.profile.fields;

import com.store.models.Usuario;
import com.store.view.panels.profile.ProfileFieldFactory;
import com.store.view.panels.profile.UserProfileConstants;

import javax.swing.*;
import java.awt.*;

public class AddressSectionPanel extends JPanel {
    private JTextArea direccionField;

    public AddressSectionPanel(Usuario usuario) {
        setLayout(new GridBagLayout());
        setBackground(UserProfileConstants.PANEL_BACKGROUND);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UserProfileConstants.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UserProfileConstants.BORDER_PADDING, 
                UserProfileConstants.BORDER_PADDING, 
                UserProfileConstants.BORDER_PADDING, 
                UserProfileConstants.BORDER_PADDING)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = UserProfileConstants.FIELD_INSETS;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        addSectionTitle(gbc, UserProfileConstants.ADDRESS_TITLE);
        direccionField = addEditableTextArea(gbc, "Dirección:", usuario.getDireccion());
    }

    private void addSectionTitle(GridBagConstraints gbc, String title) {
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(UserProfileConstants.SECTION_FONT);
        sectionLabel.setForeground(UserProfileConstants.SECTION_COLOR);
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        gbc.gridwidth = 2;
        add(sectionLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
    }

    private JTextArea addEditableTextArea(GridBagConstraints gbc, String label, String value) {
        add(ProfileFieldFactory.createLabel(label), gbc);
        gbc.gridx++;
        
        JTextArea textArea = ProfileFieldFactory.createEditableTextArea(value);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(textArea.getBorder());
        scrollPane.setPreferredSize(new Dimension(textArea.getPreferredSize().width, 80));
        
        add(scrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        
        return textArea;
    }

    public String getDireccion() {
        return direccionField.getText();
    }
}