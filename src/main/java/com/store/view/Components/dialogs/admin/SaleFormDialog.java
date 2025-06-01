package com.store.view.components.dialogs.admin;

import com.store.models.Sale;
import com.store.models.SaleStatus;
import com.store.services.SaleServiceImpl;
import com.store.utils.Colors;
import com.store.view.components.dialogs.FormStyler;
import com.store.view.components.shared.FormInputComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

/**
 * Diálogo para creación o edición de ventas.
 * 
 * Permite:
 * <ul>
 *   <li>Crear nuevas ventas</li>
 *   <li>Editar ventas existentes</li>
 *   <li>Modificar estado de la venta</li>
 * </ul>
 */
public class SaleFormDialog extends BaseEntityFormDialog {
    private final SaleServiceImpl saleService;
    private final Sale sale;

    private JSpinner dateSpinner;
    private JSpinner customerIdSpinner;
    private JComboBox<SaleStatus> statusCombo;
    private JTextField totalField;

    /**
     * Constructor del diálogo de venta.
     * @param parent ventana padre (puede ser JFrame o JDialog)
     * @param sale Venta a editar (null para creación)
     * @param saleService Servicio para manejar operaciones de venta
     */
    public SaleFormDialog(Window parent, Sale sale, SaleServiceImpl saleService) {
        super(parent, sale == null ? "Nueva Venta" : "Editar Venta");

        this.saleService = saleService;
        this.sale = sale == null ? new Sale() : sale;

        setSize(500, 400);
        setupLayout();
        setLocationRelativeTo(parent);
    }

    @Override
    protected void setupLayout() {
        setupCommonLayout();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Colors.BACKGROUND);

        if (sale.getId() != 0) {
            addReadOnlyField("ID:", String.valueOf(sale.getId()));
        }

        dateSpinner = FormInputComponents.createDateSpinner(
            sale.getDate() != null ? sale.getDate() : new Date()
        );
        addCustomField("Fecha:", dateSpinner);

        customerIdSpinner = FormInputComponents.createIntegerSpinner(
            sale.getCustomerId(), 1, 1, 999999
        );
        addCustomField("ID Cliente:", customerIdSpinner);

        totalField = FormStyler.createFormTextField();
        totalField.setText(String.format("%.2f", sale.getTotal()));
        totalField.setEditable(false);
        addCustomField("Total:", totalField);

        statusCombo = new JComboBox<>(SaleStatus.values());
        statusCombo.setSelectedItem(sale.getStatus());
        addCustomField("Estado:", statusCombo);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(this::saveForm), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    @Override
    protected void saveForm() {
        try {
            updateSaleData();

            boolean success = sale.getId() == 0 ?
                saleService.crearVenta(sale) :
                saleService.actualizarVenta(sale);

            handleSaveResult(success);
        } catch (Exception ex) {
            showError("Error al guardar la venta: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void addReadOnlyField(String label, String value) {
        formPanel.add(FormStyler.createFormLabel(label));
        JTextField field = FormStyler.createFormTextField();
        field.setText(value);
        field.setEditable(false);
        formPanel.add(field);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    private void updateSaleData() {
        sale.setDate((Date) dateSpinner.getValue());
        sale.setCustomerId((Integer) customerIdSpinner.getValue());
        sale.setStatus((SaleStatus) statusCombo.getSelectedItem());
        sale.recalculateTotal();
        totalField.setText(String.format("%.2f", sale.getTotal()));
    }

    private void handleSaveResult(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this,
                sale.getId() == 0 ?
                    "Venta registrada correctamente" :
                    "Venta actualizada correctamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError("Error al guardar la venta. Verifique los datos.");
        }
    }
}
