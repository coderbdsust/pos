/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.adder;

import com.coderbd.pos.constraints.Message;
import com.coderbd.pos.entity.OrderProduct;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.utils.Reset;
import java.awt.Color;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class ProductAdder {

    private Reset resetter;

    public ProductAdder() {
        resetter = new Reset();
    }

    public void addProductsInStockView(List<Product> products, JTable table) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (Product p : products) {
            Object object[] = {p.getProductBarcode(), p.getProductName(), p.getProductStock(), p.getProductBuyRate(), p.getProductSellRate(), p.getProductInfoUpdated()};
            tableModel.addRow(object);
        }
    }

    public void addDistributedProductsInStockView(List<Product> products, JTable table) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (Product p : products) {
            Object object[] = {p.getProductBarcode(), p.getShop().getShopName(), p.getProductBuyRate(), p.getProductSellRate(), p.getProductStock()};
            tableModel.addRow(object);
        }
    }

    public void addProductInStockView(Product p, JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        Object object[] = {p.getProductBarcode(), p.getProductName(), p.getProductStock(), p.getProductBuyRate(), p.getProductSellRate(), p.getProductInfoUpdated()};
        tableModel.addRow(object);
    }

}
