/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import com.coderbd.pos.entity.Product;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Biswajit Debnath
 */
public class ProductAdder {

    private ResetTable resetter;

    public ProductAdder() {
        resetter = new ResetTable();
    }

    public void addProductsInStock(List<Product> products, JTable table) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (Product p : products) {
            Object object[] = {p.getProductBarcode(), p.getProductName(), p.getProductStock(), p.getProductRate()};
            tableModel.addRow(object);
        }
    }

    public void addProductInStock(Product p, JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        Object object[] = {p.getProductBarcode(), p.getProductName(), p.getProductStock(), p.getProductRate()};
        tableModel.addRow(object);
    }

}
