package com.store.view.AdminView;

import com.store.utils.Colors;
import com.store.view.components.buttons.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LogoutPanel extends JPanel {
    public LogoutPanel(ActionListener logoutAction) {
        super(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        setBackground(Colors.BACKGROUND);
        
        CustomButton logoutButton = new CustomButton(
            "Cerrar Sesión", 
            Colors.LOGOUT_RED, 
            Color.WHITE
        );
        logoutButton.addActionListener(logoutAction);
        
        add(logoutButton);
    }
}