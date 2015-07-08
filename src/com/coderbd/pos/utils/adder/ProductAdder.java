/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.adder;

import com.coderbd.pos.constraints.Message;
import com.coderbd.pos.entity.OrderProduct;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.utils.DateUtil;
import com.coderbd.pos.utils.Reset;
import java.awt.Color;
import java.util.Date;
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
        
        Double shopTotalProductAmount=0.0;
        
        for (Product p : products) {
            Double productAmount = p.getProductStock()*p.getProductBuyRate();
            
            shopTotalProductAmount+=productAmount;
            
            Date date = DateUtil.convertTimestampToDate(p.getProductInfoUpdated());
            String standardDate = " "+DateUtil.getStandardDate(date);
            
            Object object[] = {p.getProductBarcode(), p.getProductName(), p.getProductStock(),productAmount, p.getProductBuyRate(), p.getProductSellRate(), standardDate};

            tableModel.addRow(object);
        }
    }
    public void addProductsInStockView(List<Product> products, JTable table, JLabel label) {
        resetter.resetTable(table);
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        
        Double shopTotalProductAmount=0.0;
        
        for (Product p : products) {
            Double productAmount = p.getProductStock()*p.getProductBuyRate();
            
            shopTotalProductAmount+=productAmount;
            
            Date date = DateUtil.convertTimestampToDate(p.getProductInfoUpdated());
            String standardDate = " "+DateUtil.getStandardDate(date);
            
            Object object[] = {p.getProductBarcode(), p.getProductName(), p.getProductStock(),productAmount, p.getProductBuyRate(), p.getProductSellRate(), standardDate};

            tableModel.addRow(object);
        }
        
        label.setText(shopTotalProductAmount.toString());
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
