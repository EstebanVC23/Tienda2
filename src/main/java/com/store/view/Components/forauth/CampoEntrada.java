package com.store.view.components.forauth;

import com.store.utils.Colors;
import com.store.utils.Fonts;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * Componente personalizado para campos de entrada con diferentes tipos (texto, contraseña, numérico).
 * Incluye etiqueta y estilizado consistente para formularios de autenticación.
 */
public class CampoEntrada extends JPanel {
    private JTextField campoTexto;
    private JPasswordField campoPassword;
    private JLabel label;
    private final String tipo;

    /**
     * Crea un nuevo campo de entrada personalizado.
     * @param tipo Tipo de campo ("texto", "password" o "entero")
     * @param labelText Texto para la etiqueta del campo
     * @param ancho Ancho preferido del campo
     * @param alto Alto preferido del campo
     */
    public CampoEntrada(String tipo, String labelText, int ancho, int alto) {
        this.tipo = tipo;
        setOpaque(false);
        setLayout(new BorderLayout());
        setAlignmentX(Component.LEFT_ALIGNMENT);

        crearLabel(labelText);
        crearCampo(tipo, new Dimension(ancho, alto));
    }

    /**
     * Crea y configura la etiqueta del campo.
     * @param labelText Texto a mostrar en la etiqueta
     */
    private void crearLabel(String labelText) {
        label = new JLabel(labelText);
        label.setForeground(Colors.SECONDARY_TEXT);
        label.setFont(Fonts.BOLD_BODY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    }

    /**
     * Crea y configura el componente de entrada según el tipo especificado.
     * @param tipo Tipo de campo a crear
     * @param campoDimension Dimensiones preferidas del campo
     */
    private void crearCampo(String tipo, Dimension campoDimension) {
        JComponent campo = crearComponenteCampo(tipo);
        estilizarCampo(campo, campoDimension);
        agregarComponentes(campo);
    }

    /**
     * Crea el componente de entrada específico según el tipo.
     * @param tipo Tipo de campo a crear
     * @return Componente JTextField o JPasswordField configurado
     */
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

    /**
     * Configura un filtro para permitir solo entrada numérica en campos de tipo "entero".
     */
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

    /**
     * Aplica estilos consistentes al componente de entrada.
     * @param campo Componente a estilizar
     * @param dimension Dimensiones preferidas del campo
     */
    private void estilizarCampo(JComponent campo, Dimension dimension) {
        campo.setFont(Fonts.BODY);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.BORDER),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campo.setBackground(Colors.PANEL_BACKGROUND);
        campo.setPreferredSize(dimension);
    }

    /**
     * Agrega los componentes (etiqueta y campo) al panel principal.
     * @param campo Componente de entrada a agregar
     */
    private void agregarComponentes(JComponent campo) {
        add(label, BorderLayout.NORTH);
        add(campo, BorderLayout.CENTER);
    }

    /**
     * Obtiene el texto ingresado en el campo.
     * @return Texto del campo o contraseña (en texto plano)
     */
    public String getText() {
        if (tipo.equals("texto") || tipo.equals("entero")) {
            return campoTexto.getText();
        } else if (tipo.equals("password")) {
            return new String(campoPassword.getPassword());
        }
        return "";
    }

    /**
     * Establece el texto del campo.
     * @param text Texto a establecer
     */
    public void setText(String text) {
        if (tipo.equals("texto") || tipo.equals("entero")) {
            campoTexto.setText(text);
        } else if (tipo.equals("password")) {
            campoPassword.setText(text);
        }
    }

    /**
     * Obtiene el componente de entrada subyacente.
     * @return Componente JTextField o JPasswordField según el tipo
     */
    public JComponent getCampo() {
        if (tipo.equals("password")) {
            return campoPassword;
        }
        return campoTexto;
    }
}