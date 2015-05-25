/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import com.coderbd.pos.entity.pojo.ShopOrder;
import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.Product;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class ShopOrderAdder {

    private ResetTable resetter;

    public ShopOrderAdder() {
        resetter = new ResetTable();
    }

    public void addCustomerOrderInOrderView(List<ShopOrder> shopOrders, JTable table) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (ShopOrder so : shopOrders) {
            CustomerOrder co = so.getCustomerOrder();
            Object object[] = {
                co.getOrderBarcode(),
                co.getOrderTime(),
                co.getBuyerName(),
                co.getBuyerMobile(),
                co.getTotalAmount(),
                co.getTotalPaid(),
                co.getTotalDue()
            };
            tableModel.addRow(object);
        }

    }

}
