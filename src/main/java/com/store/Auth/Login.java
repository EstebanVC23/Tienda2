package com.store.Auth;

import javax.swing.*;
import com.store.models.*;
import com.store.services.*;
import com.store.view.AdminView.AdminView;
import com.store.view.UserView.UserView;

import java.awt.*;
import java.awt.event.*;

/**
 * Clase que representa la interfaz gráfica de inicio de sesión.
 */
public class Login extends JFrame {

    private final Color COLOR_FONDO = new Color(236, 240, 241);
    private final Color COLOR_TEXTO = new Color(44, 62, 80);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);

    private CampoEntrada campoCorreo;
    private CampoEntrada campoPassword;
    private Boton botonIngresar;
    private Boton botonRegistrarse;
    private UsuarioServicio usuarioServicio;
    private ProductoServicio productoServicio;

    public Login(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        this.usuarioServicio = usuarioServicio;
        this.productoServicio = productoServicio;

        setTitle("Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        crearPanelIzquierdo();
        crearPanelDerecho();
        configurarEventos();
    }

    private void crearPanelIzquierdo() {
        PanelIzquierdoInicio panelIzquierdo = new PanelIzquierdoInicio("BIENVENIDO", "Sistema de Gestión");
        add(panelIzquierdo);
    }

    private void crearPanelDerecho() {
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(COLOR_FONDO);
        panelDerecho.setLayout(new BorderLayout(20, 20));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTitulo = new JLabel("Iniciar Sesión");
        labelTitulo.setForeground(COLOR_TEXTO);
        labelTitulo.setFont(FUENTE_TITULO);
        panelTitulo.add(labelTitulo);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setOpaque(false);
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        int ancho = 400;
        int alto = 30;

        campoCorreo = new CampoEntrada("texto", "Correo electrónico", "✉", ancho, alto);
        campoPassword = new CampoEntrada("password", "Contraseña", "🔒", ancho, alto);
        botonIngresar = new Boton("INGRESAR", 150, 40, 13, 1);
        botonRegistrarse = new Boton("¿No tienes cuenta? Regístrate", 0, 0, 12, 2);

        panelFormulario.add(campoCorreo, gbc);
        panelFormulario.add(campoPassword, gbc);
        panelFormulario.add(botonIngresar, gbc);
        panelFormulario.add(botonRegistrarse, gbc);

        panelDerecho.add(panelTitulo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);

        add(panelDerecho);
    }

    private void configurarEventos() {
        botonIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String correo = campoCorreo.getText().trim();
                String password = campoPassword.getText().trim();

                if (!correo.isEmpty() && !password.isEmpty()) {
                    Usuario usuarioLogin = new Usuario();
                    usuarioLogin.setEmail(correo);
                    usuarioLogin.setPassword(password);

                    if (usuarioServicio.validarUsuario(usuarioLogin)) {
                        Usuario dataUser = usuarioServicio.obtenerUsuarioPorEmail(correo);

                        JOptionPane.showMessageDialog(Login.this, "Bienvenido " + dataUser.getNombre(), "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);

                        if ("ADMIN".equalsIgnoreCase(dataUser.getRol())) {
                            AdminView adminPage = new AdminView(dataUser, usuarioServicio, productoServicio);
                            adminPage.setVisible(true);
                        } else {
                            UserView userPage = new UserView(dataUser, productoServicio);
                            userPage.setVisible(true);
                        }

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "Correo o contraseña incorrectos", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Por favor, ingrese ambos campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Registro registroWindow = new Registro(usuarioServicio, productoServicio);
                registroWindow.setVisible(true);
                dispose();
            }
        });
    }
}