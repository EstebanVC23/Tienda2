package com.store.Auth;

import javax.swing.*;

import com.store.models.Usuario;
import com.store.services.UsuarioServicio;
import com.store.utils.PasswordUtils;
import com.store.services.ProductoServicio;

import java.awt.*;
import java.awt.event.*;

/**
 * Clase que representa la ventana de registro de usuario.
 * Permite a los usuarios ingresar su información personal para registrarse.
 */
public class Registro extends JFrame {
    // Definición de colores y fuentes utilizadas en la interfaz
    private final Color COLOR_FONDO = new Color(236, 240, 241);
    private final Color COLOR_TEXTO = new Color(44, 62, 80);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);

    // Campos de entrada de datos
    private CampoEntrada campoId;
    private CampoEntrada campoNombre;
    private CampoEntrada campoApellido;
    private CampoEntrada campoEmail;
    private CampoEntrada campoTipoDocumento;
    private CampoEntrada campoNumeroDocumento;
    private CampoEntrada campoDireccion;
    private CampoEntrada campoTelefono;
    private CampoEntrada campoPassword;
    private CampoEntrada campoConfirmarPassword;

    // Botones de acción
    private Boton botonRegistrar;
    private Boton botonVolver;
    private UsuarioServicio usuarioServicio;
    private ProductoServicio productoServicio;

    /**
     * Constructor de la clase Registro.
     * 
     * @param usuarioServicio Servicio para gestionar usuarios.
     */
    public Registro(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
        this.usuarioServicio = usuarioServicio;
        setTitle("Registro de Usuario");
        setSize(1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        crearPanelIzquierdo();
        crearPanelDerecho();
        configurarEventos();
    }

    /**
     * Crea el panel izquierdo de la interfaz.
     */
    private void crearPanelIzquierdo() {
        PanelIzquierdoInicio panelIzquierdo = new PanelIzquierdoInicio("REGISTRATE", "Crea tu cuenta");
        add(panelIzquierdo);
    }

    /**
     * Crea el panel derecho de la interfaz con el formulario de registro.
     */
    private void crearPanelDerecho() {
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(COLOR_FONDO);
        panelDerecho.setLayout(new BorderLayout(20, 20));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Configuración del título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTitulo = new JLabel("Registrar Usuario");
        labelTitulo.setForeground(COLOR_TEXTO);
        labelTitulo.setFont(FUENTE_TITULO);
        panelTitulo.add(labelTitulo);

        // Configuración del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setOpaque(false);
        panelFormulario.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ancho = 50;
        int alto = 30;

        // Inicialización de los campos con todos los datos del usuario
        campoId = new CampoEntrada("entero", "ID", "🆔", ancho, alto);
        campoNombre = new CampoEntrada("texto", "Nombre", "👤", ancho, alto);
        campoApellido = new CampoEntrada("texto", "Apellido", "👤", ancho, alto);
        campoEmail = new CampoEntrada("texto", "Correo electrónico", "✉", ancho, alto);
        campoTipoDocumento = new CampoEntrada("texto", "Tipo de Documento", "📄", ancho, alto);
        campoNumeroDocumento = new CampoEntrada("entero", "Número de Documento", "📄", ancho, alto);
        campoDireccion = new CampoEntrada("texto", "Dirección", "🏠", ancho, alto);
        campoTelefono = new CampoEntrada("texto", "Teléfono", "📞", ancho, alto);
        campoPassword = new CampoEntrada("password", "Contraseña", "🔒", ancho, alto);
        campoConfirmarPassword = new CampoEntrada("password", "Confirmar Contraseña", "🔒", ancho, alto);

        panelFormulario.add(campoId, gbc);
        panelFormulario.add(campoNombre, gbc);
        panelFormulario.add(campoApellido, gbc);
        panelFormulario.add(campoEmail, gbc);
        panelFormulario.add(campoTipoDocumento, gbc);
        panelFormulario.add(campoNumeroDocumento, gbc);
        panelFormulario.add(campoDireccion, gbc);
        panelFormulario.add(campoTelefono, gbc);
        panelFormulario.add(campoPassword, gbc);
        panelFormulario.add(campoConfirmarPassword, gbc);

        // Configuración de botones
        botonRegistrar = new Boton("REGISTRAR", 150, 40, 13, 1);
        botonVolver = new Boton("Volver al Login", 0, 0, 13, 2);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonVolver);

        panelDerecho.add(panelTitulo, BorderLayout.NORTH);
        panelDerecho.add(panelFormulario, BorderLayout.CENTER);
        panelDerecho.add(panelBotones, BorderLayout.SOUTH);

        add(panelDerecho);
    }

    /**
     * Configura los eventos de los botones de la interfaz.
     */
    private void configurarEventos() {
        botonRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = campoId.getText().trim();
                String nombre = campoNombre.getText().trim();
                String apellido = campoApellido.getText().trim();
                String email = campoEmail.getText().trim();
                String tipoDocumento = campoTipoDocumento.getText().trim();
                String numeroDocumento = campoNumeroDocumento.getText().trim();
                String direccion = campoDireccion.getText().trim();
                String telefono = campoTelefono.getText().trim();
                String password = campoPassword.getText().trim();
                String confirmarPassword = campoConfirmarPassword.getText().trim();

                // Verificar que todos los campos estén llenos
                if (!idStr.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty()
                        && !tipoDocumento.isEmpty() && !numeroDocumento.isEmpty() && !direccion.isEmpty()
                        && !telefono.isEmpty() && !password.isEmpty() && !confirmarPassword.isEmpty()) {

                    // Verificar que las contraseñas coincidan
                    if (password.equals(confirmarPassword)) {
                        if (usuarioServicio.obtenerUsuarioPorEmail(email) != null) {
                            JOptionPane.showMessageDialog(Registro.this, "El correo ya está registrado.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int id = Integer.parseInt(idStr);
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setId(id);
                        nuevoUsuario.setNombre(nombre);
                        nuevoUsuario.setApellido(apellido);
                        nuevoUsuario.setEmail(email);
                        nuevoUsuario.setTipoDocumento(tipoDocumento);
                        nuevoUsuario.setNumeroDocumento(numeroDocumento);
                        nuevoUsuario.setDireccion(direccion);
                        nuevoUsuario.setTelefono(telefono);
                        nuevoUsuario.setPassword(PasswordUtils.encrypt(password)); // Encriptar contraseña
                        nuevoUsuario.setRol("USER"); // Asignar rol por defecto
                        nuevoUsuario.setEstadoActivo(true);

                        boolean registroExitoso = usuarioServicio.registrarUsuario(nuevoUsuario);
                        if (registroExitoso) {
                            JOptionPane.showMessageDialog(Registro.this, "Usuario registrado exitosamente.", "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                            abrirLogin(); // Redirigir al login después del registro exitoso
                        } else {
                            JOptionPane.showMessageDialog(Registro.this, "El usuario ya existe.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(Registro.this, "Las contraseñas no coinciden.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Registro.this, "Por favor, llene todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonVolver.addActionListener(_ -> abrirLogin());
    }

    private void abrirLogin() {
        new Login(usuarioServicio, productoServicio).setVisible(true);
        dispose();
    }
}
