package com.store.view.components.forauth;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class CampoEntrada extends JPanel {
    private JTextField campoTexto;
    private JPasswordField campoPassword;
    private JLabel label;
    private String tipo;

    public CampoEntrada(String tipo, String labelText, int ancho, int alto) {
        this.tipo = tipo;
        setOpaque(false);
        setLayout(new BorderLayout());
        setAlignmentX(Component.LEFT_ALIGNMENT);

        crearLabel(labelText);
        crearCampo(tipo, new Dimension(ancho, alto));
    }

    private void crearLabel(String labelText) {
        label = new JLabel(labelText);
        label.setForeground(Colors.SECONDARY_TEXT);
        label.setFont(Fonts.BOLD_BODY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    }

    private void crearCampo(String tipo, Dimension campoDimension) {
        JComponent campo = crearComponenteCampo(tipo);
        estilizarCampo(campo, campoDimension);
        agregarComponentes(campo);
    }

    private JComponent crearComponenteCampo(String tipo) {
        if (tipo.equals("password")) {
            campoPassword = new JPasswordField(20);
            return campoPassword;
        } else {
            campoTexto = new JTextField(20);
            if (tipo.equals("entero")) {
                configurarFiltroNumerico();
            }
            return campoTexto;
        }
    }

    private void configurarFiltroNumerico() {
        ((AbstractDocument) campoTexto.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void estilizarCampo(JComponent campo, Dimension dimension) {
        campo.setFont(Fonts.BODY);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.BORDER),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campo.setBackground(Colors.PANEL_BACKGROUND);
        campo.setPreferredSize(dimension);
    }

    private void agregarComponentes(JComponent campo) {
        add(label, BorderLayout.NORTH);
        add(campo, BorderLayout.CENTER);
    }

    public String getText() {
        if (tipo.equals("texto") || tipo.equals("entero")) {
            return campoTexto.getText();
        } else if (tipo.equals("password")) {
            return new String(campoPassword.getPassword());
        }
        return "";
    }

    public void setText(String text) {
        if (tipo.equals("texto") || tipo.equals("entero")) {
            campoTexto.setText(text);
        } else if (tipo.equals("password")) {
            campoPassword.setText(text);
        }
    }

    public JComponent getCampo() {
        if (tipo.equals("password")) {
            return campoPassword;
        }
        return campoTexto;
    }
}