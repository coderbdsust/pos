/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.SupplierOrderPayment;
import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.service.SupplierOrderPaymentService;
import com.coderbd.pos.service.SupplierOrderService;
import com.coderbd.pos.service.SupplierProductService;
import com.coderbd.pos.utils.DateUtil;
import com.coderbd.pos.utils.Reset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class SupplierOrderFormPanel extends javax.swing.JPanel {

    Double totalBill = 0.0;
    Double totalPaid = 0.0;
    Double totalDue = 0.0;
    private SupplierOrder supplierOrder;

    private SupplierOrderService orderService;
    private SupplierProductService productService;
    private SupplierOrderPaymentService paymentService;

    /**
     * Creates new form SupplierOrderFormPanel
     */
    public SupplierOrderFormPanel(SupplierOrder supplierOrder, SupplierOrderService orderService, SupplierProductService productService, SupplierOrderPaymentService paymentService) {

        initComponents();
        this.supplierOrder = supplierOrder;
        this.orderService = orderService;
        this.productService = productService;
        this.paymentService = paymentService;
        Reset.resetTable(psoTable);
        Reset.resetTable(paymentTable);
        panelTitle.setText(supplierOrder.getSupplier().getSupplierName() + ", " + supplierOrder.getSupplier().getSupplierMobile());
        updateAllForm();
        updateBillingInfo();
    }

    private void updateAllForm() {
        addAllOrderProductItemInTable();
        addAllPaymentItemInTable();
        totalBill = supplierOrder.getTotalBill();
        totalPaid = supplierOrder.getTotalPaid();
        totalDue = totalBill - totalPaid;
    }

    private void updateBillingInfo() {
        totalBillLabel.setText(totalBill.toString());
        totalPaidLabel.setText(totalPaid.toString());
        totalDueLabel.setText(totalDue.toString());
    }

    private void addAllOrderProductItemInTable() {
        DefaultTableModel tableModel = (DefaultTableModel) psoTable.getModel();

        List<SupplierOrderProduct> supplierOrderProducts = supplierOrder.getSupplierProducts();
        if (supplierOrderProducts != null) {
            for (SupplierOrderProduct sop : supplierOrderProducts) {
                Double amount = sop.getSupplierRate() * sop.getSupplierProductQuantity();
                Object object[] = {
                    sop.getSupplierProductName(),
                    sop.getSupplierProductQuantity(),
                    sop.getSupplierRate(),
                    amount
                };
                tableModel.addRow(object);
            }
        }
    }

    private void addOrderProductItemInTable(SupplierOrderProduct sop) {

        DefaultTableModel tableModel = (DefaultTableModel) psoTable.getModel();
        Double amount = sop.getSupplierRate() * sop.getSupplierProductQuantity();
        Object object[] = {
            sop.getSupplierProductName(),
            sop.getSupplierProductQuantity(),
            sop.getSupplierRate(),
            amount
        };
        tableModel.addRow(object);
    }

    /**
     * This is for payment table modification
     */
    private void addAllPaymentItemInTable() {
        DefaultTableModel tableModel = (DefaultTableModel) paymentTable.getModel();

        List<SupplierOrderPayment> supplierOrderPayments = supplierOrder.getSupplierOrderPayments();

        if (supplierOrderPayments != null) {
            for (SupplierOrderPayment sop : supplierOrderPayments) {

                Object object[] = {
                    sop.getPaymentDate(),
                    sop.getAmount(),
                    sop.getDescription()
                };
                tableModel.addRow(object);
            }
        }

    }

    private void addPaymentItemInTable(SupplierOrderPayment sop) {

        DefaultTableModel tableModel = (DefaultTableModel) paymentTable.getModel();
        Object object[] = {
            sop.getPaymentDate(),
            sop.getAmount(),
            sop.getDescription()
        };
        tableModel.addRow(object);
    }

    private void removeItemFromTable(JTable table, int index) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.removeRow(index);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        supplierOrderItemInputPanel = new javax.swing.JPanel();
        productInputPanel = new javax.swing.JPanel();
        pNameField = new javax.swing.JTextField();
        pQuantityField = new javax.swing.JTextField();
        pRateField = new javax.swing.JTextField();
        addPItemButton = new javax.swing.JButton();
        removePItemButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelTitle = new javax.swing.JLabel();
        paymentInputPanel = new javax.swing.JPanel();
        paymentDateC = new com.toedter.calendar.JDateChooser();
        paymentField = new javax.swing.JTextField();
        descField = new javax.swing.JTextField();
        addPaymentB = new javax.swing.JButton();
        removePaymentB = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        productPanel = new javax.swing.JPanel();
        totalBillLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        totalDueLabel = new javax.swing.JLabel();
        totalPaidLabel = new javax.swing.JLabel();
        paymentPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        psoTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(850, 520));
        setLayout(new java.awt.BorderLayout());

        supplierOrderItemInputPanel.setLayout(new java.awt.GridLayout(1, 0));

        productInputPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        addPItemButton.setText("Add Item");
        addPItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPItemButtonActionPerformed(evt);
            }
        });

        removePItemButton.setText("Remove Item");
        removePItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePItemButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Product Name");

        jLabel2.setText("Quantity");

        jLabel3.setText("Rate");

        panelTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        panelTitle.setText("Fashion Park");

        javax.swing.GroupLayout productInputPanelLayout = new javax.swing.GroupLayout(productInputPanel);
        productInputPanel.setLayout(productInputPanelLayout);
        productInputPanelLayout.setHorizontalGroup(
            productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(removePItemButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pNameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productInputPanelLayout.createSequentialGroup()
                        .addComponent(pQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pRateField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productInputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addPItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(productInputPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        productInputPanelLayout.setVerticalGroup(
            productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productInputPanelLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removePItemButton)
                    .addComponent(addPItemButton))
                .addContainerGap())
            .addGroup(productInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(productInputPanelLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(panelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(100, Short.MAX_VALUE)))
        );

        supplierOrderItemInputPanel.add(productInputPanel);

        addPaymentB.setText("Add Payment");
        addPaymentB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPaymentBActionPerformed(evt);
            }
        });

        removePaymentB.setText("Remove Payment");
        removePaymentB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePaymentBActionPerformed(evt);
            }
        });

        jLabel4.setText("Payment Date");

        jLabel7.setText("Amont");

        jLabel9.setText("Description");

        javax.swing.GroupLayout paymentInputPanelLayout = new javax.swing.GroupLayout(paymentInputPanel);
        paymentInputPanel.setLayout(paymentInputPanelLayout);
        paymentInputPanelLayout.setHorizontalGroup(
            paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(paymentInputPanelLayout.createSequentialGroup()
                        .addComponent(paymentDateC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(paymentField)
                            .addComponent(removePaymentB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(paymentInputPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addPaymentB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descField)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        paymentInputPanelLayout.setVerticalGroup(
            paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentInputPanelLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(paymentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(descField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(paymentDateC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paymentInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPaymentB)
                    .addComponent(removePaymentB))
                .addGap(12, 12, 12))
        );

        supplierOrderItemInputPanel.add(paymentInputPanel);

        add(supplierOrderItemInputPanel, java.awt.BorderLayout.PAGE_START);

        productPanel.setPreferredSize(new java.awt.Dimension(492, 70));

        totalBillLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalBillLabel.setText("0.0");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Bill");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Paid");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Due");

        totalDueLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalDueLabel.setText("0.0");

        totalPaidLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalPaidLabel.setText("0.0");

        javax.swing.GroupLayout productPanelLayout = new javax.swing.GroupLayout(productPanel);
        productPanel.setLayout(productPanelLayout);
        productPanelLayout.setHorizontalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalPaidLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(totalBillLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalDueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(257, 257, 257))
        );
        productPanelLayout.setVerticalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totalBillLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addComponent(totalDueLabel))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalPaidLabel)
                    .addComponent(jLabel6))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        add(productPanel, java.awt.BorderLayout.PAGE_END);

        paymentPanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(350, 355));
        jPanel3.setLayout(new java.awt.CardLayout());

        psoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product Name", "Quantity", "Rate", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        psoTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(psoTable);

        jPanel3.add(jScrollPane2, "card2");

        paymentPanel.add(jPanel3);

        jPanel4.setLayout(new java.awt.CardLayout());

        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Amount", "Desc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(paymentTable);

        jPanel4.add(jScrollPane1, "card2");

        paymentPanel.add(jPanel4);

        add(paymentPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addPItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPItemButtonActionPerformed
        // TODO add your handling code here:
        SupplierOrderProduct sop = new SupplierOrderProduct();

        String productName = supplierOrder.getSupplier().getSupplierName() + "_O" + supplierOrder.getSupplierOrderId() + "_P" + (supplierOrder.getSupplierProducts().size() + 1);
        //   pNameField.setText(productName)

        if (pNameField.getText().replace(" ", "").length() != 0 && !pNameField.getText().equals("")) {
            String userDefinedName = pNameField.getText();
            productName = productName + "_" + userDefinedName;
        }
        if (!pQuantityField.getText().equals("") && !pRateField.getText().equals("")) {
            try {
                sop.setSupplierOrderId(supplierOrder.getSupplierOrderId());
                sop.setSupplierProductName(productName);
                sop.setSupplierProductQuantity(Integer.parseInt(pQuantityField.getText()));
                sop.setSupplierRate(Double.parseDouble(pRateField.getText()));

                int supplierProductId = productService.saveSupplierProduct(sop);

                if (supplierProductId != Enum.invalidIndex) {
                    sop.setSupplierProductId(supplierProductId);
                    addOrderProductItemInTable(sop);
                    supplierOrder.getSupplierProducts().add(sop);

                    totalBill = supplierOrder.getSupplierProductTotalBill();
                    totalDue = totalBill - totalPaid;
                    updateBillingInfo();

                    supplierOrder.setTotalBill(totalBill);
                    supplierOrder.setTotalPaid(totalPaid);
                    boolean status = orderService.updateSupplierOrder(supplierOrder);
                    System.out.println("Order Service Update: " + status);
                } else {
                    System.out.println("Product Couldn't Saved");
                }
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.getMessage());
            }
        }
        pNameField.setText("");
        pQuantityField.setText("");
        pRateField.setText("");


    }//GEN-LAST:event_addPItemButtonActionPerformed

    private void removePItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePItemButtonActionPerformed
        // TODO add your handling code here:
        int rowSelected = psoTable.getSelectedRow();
        SupplierOrderProduct sop = supplierOrder.getSupplierProducts().get(rowSelected);
        boolean status = productService.deleteSupplierProduct(sop);
        if (status) {
            supplierOrder.getSupplierProducts().remove(rowSelected);
            removeItemFromTable(psoTable, rowSelected);
            totalBill = supplierOrder.getSupplierProductTotalBill();
            totalBillLabel.setText(totalBill.toString());
            totalDue = totalBill - totalPaid;
            totalDueLabel.setText(totalDue.toString());

            supplierOrder.setTotalBill(totalBill);
            supplierOrder.setTotalPaid(totalPaid);

            status = orderService.updateSupplierOrder(supplierOrder);
            System.out.println("Order Service Update: " + status);

        } else {
            System.out.println("Product couldn't remove!");
        }


    }//GEN-LAST:event_removePItemButtonActionPerformed

    private void addPaymentBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPaymentBActionPerformed
        // TODO add your handling code here:
        try {
            Date date = paymentDateC.getDate();
            if (date == null) {
                date = new Date();
            }

            Timestamp sqlDate = DateUtil.convertDateToTimestamp(date);
            Double payment = Double.parseDouble(paymentField.getText());
            String desc = descField.getText();
            SupplierOrderPayment sop = new SupplierOrderPayment(sqlDate, payment, desc);
            System.out.println("Payment: " + sop);

            sop.setSupplierOrderId(supplierOrder.getSupplierOrderId());
            System.out.println("ADD PAYMNENT:" + sop);
            int supplierOrderPaidId = paymentService.saveSupplierOrderPayment(sop);
            if (supplierOrderPaidId != Enum.invalidIndex) {
                sop.setSupplierOrderPaymentId(supplierOrderPaidId);
                addPaymentItemInTable(sop);
                supplierOrder.getSupplierOrderPayments().add(sop);

                totalPaid = supplierOrder.getSupplierOrderTotalPayment();
                totalDue = totalBill - totalPaid;

                updateBillingInfo();

                paymentField.setText("");
                descField.setText("");

                supplierOrder.setTotalBill(totalBill);
                supplierOrder.setTotalPaid(totalPaid);

                boolean status = orderService.updateSupplierOrder(supplierOrder);
                System.out.println("Order Service Update: " + status);
            } else {
                System.out.println("Payment Couldn't Saved!");
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }
    }//GEN-LAST:event_addPaymentBActionPerformed

    private void removePaymentBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePaymentBActionPerformed
        // TODO add your handling code here:
        int rowSelected = paymentTable.getSelectedRow();
        SupplierOrderPayment sop = supplierOrder.getSupplierOrderPayments().get(rowSelected);
        boolean status = paymentService.deleteSupplierOrderPayment(sop);
        if (status) {

            supplierOrder.getSupplierOrderPayments().remove(rowSelected);
            removeItemFromTable(paymentTable, rowSelected);

            totalPaid = supplierOrder.getSupplierOrderTotalPayment();
            totalDue = totalBill - totalPaid;

            updateBillingInfo();

            supplierOrder.setTotalBill(totalBill);
            supplierOrder.setTotalPaid(totalPaid);

            status = orderService.updateSupplierOrder(supplierOrder);
            System.out.println("Order Service Update: " + status);

        } else {
            System.out.println("Payment Cound't Deleted!");
        }
    }//GEN-LAST:event_removePaymentBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPItemButton;
    private javax.swing.JButton addPaymentB;
    private javax.swing.JTextField descField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pNameField;
    private javax.swing.JTextField pQuantityField;
    private javax.swing.JTextField pRateField;
    private javax.swing.JLabel panelTitle;
    private com.toedter.calendar.JDateChooser paymentDateC;
    private javax.swing.JTextField paymentField;
    private javax.swing.JPanel paymentInputPanel;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JTable paymentTable;
    private javax.swing.JPanel productInputPanel;
    private javax.swing.JPanel productPanel;
    private javax.swing.JTable psoTable;
    private javax.swing.JButton removePItemButton;
    private javax.swing.JButton removePaymentB;
    private javax.swing.JPanel supplierOrderItemInputPanel;
    private javax.swing.JLabel totalBillLabel;
    private javax.swing.JLabel totalDueLabel;
    private javax.swing.JLabel totalPaidLabel;
    // End of variables declaration//GEN-END:variables
}