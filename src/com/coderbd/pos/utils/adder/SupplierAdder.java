/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.adder;

import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.Supplier;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.SupplierOrderPayment;
import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.utils.Reset;
import com.coderbd.pos.utils.index.SOPIndex;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class SupplierAdder {

    public void refreshSupplierLists(JComboBox cb, List<Supplier> suppliers) {
        cb.removeAllItems();

        if (suppliers != null) {
            for (Supplier s : suppliers) {
                cb.addItem(s.getSupplierName());
            }
        }
    }

    public List<SOPIndex> refreshSupplierProductList(JComboBox cb, Supplier supplier, int supplierIndex) {
        cb.removeAllItems();
        List<SupplierOrder> supplierOrders = supplier.getSupplierOrders();
        List<SOPIndex> sdpis = new ArrayList<SOPIndex>();

        if (supplierOrders != null) {
            int supplierOrderIndex = 0;
            for (SupplierOrder so : supplierOrders) {
                int orderProductIndex = 0;
                List<SupplierOrderProduct> supplierOrderProducts = so.getSupplierProducts();
                if (supplierOrderProducts != null) {
                    for (SupplierOrderProduct sop : supplierOrderProducts) {
                        String productName = sop.getSupplierProductName();
                        cb.addItem(productName);
                        SOPIndex sdpi = new SOPIndex(supplierIndex, supplierOrderIndex, orderProductIndex);
                        sdpis.add(sdpi);
                        orderProductIndex++;
                    }
                }
                supplierOrderIndex++;
            }
        }
        return sdpis;
    }

    public void refreshQuantityList(JComboBox cb, int quantity) {
        cb.removeAllItems();
        for (int i = 0; i < quantity; i++) {
            cb.addItem(i);
        }

    }

    public void refreshShopsList(JComboBox cb, List<Shop> shops) {
        cb.removeAllItems();
        for (Shop shop : shops) {
            cb.addItem(shop.getShopName());
        }
    }

    public void refreshSupplierTable(Supplier supplier, JTable table, JLabel billL, JLabel paidL, JLabel dueL) {

        Reset.resetTable(table);

        if (supplier != null) {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            List<SupplierOrder> supplierOrders = supplier.getSupplierOrders();

            Double bill = 0.0;
            Double paid = 0.0;

            if (supplierOrders != null) {
                for (SupplierOrder so : supplierOrders) {
                    bill += so.getTotalBill();
                    paid += so.getTotalPaid();
                    Object[] row = {so.getOrderTime(), so.getSupplierOrderId(), so.getTotalBill(), so.getTotalPaid(), so.getTotalBill() - so.getTotalPaid()};
                    tableModel.addRow(row);
                }
            }
            Double due = bill - paid;
            billL.setText(bill.toString());
            paidL.setText(paid.toString());
            dueL.setText(due.toString());
        }
    }
}
