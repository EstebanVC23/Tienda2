package com.store.view;

import com.store.view.Components.UserInfoCard;

import javax.swing.*;

import com.store.models.Usuario;

import java.awt.*;

public class UserProfilePanel extends JPanel {
    public UserProfilePanel(Usuario usuario) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 249));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(240, 245, 249));
        
        JLabel welcomeLabel = new JLabel("Bienvenido");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        
        JLabel nameLabel = new JLabel(usuario.getNombre() + " " + usuario.getApellido());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(new Color(30, 136, 229));
        
        headerPanel.add(welcomeLabel);
        headerPanel.add(nameLabel);
        
        // Panel de información
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 5, 5));
        infoPanel.setBackground(new Color(240, 245, 249));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        infoPanel.add(new UserInfoCard("Correo electrónico", usuario.getEmail()));
        infoPanel.add(new UserInfoCard("Tipo de documento", usuario.getTipoDocumento()));
        infoPanel.add(new UserInfoCard("Número de documento", usuario.getNumeroDocumento()));
        infoPanel.add(new UserInfoCard("Dirección", usuario.getDireccion()));
        infoPanel.add(new UserInfoCard("Teléfono", usuario.getTelefono()));
        infoPanel.add(new UserInfoCard("Estado", usuario.isEstadoActivo() ? "Activo" : "Inactivo"));
        
        add(headerPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }
}