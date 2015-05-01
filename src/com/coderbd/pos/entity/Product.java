/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coderbd.pos.entity;

import java.sql.Timestamp;

/**
 *
 * @author Biswajit Debnath
 */
public class Product {
    
    private int productId;
    private int shopId;
    private String productBarcode;
    private String productName;
    private double productRate;
    private Timestamp productInfoUpdated;
    private int productStock;
    
    public Product(){
    }

    public Product(int productId, int shopId, String productBarcode, String productName, double productRate, Timestamp productInfoUpdated, int productStock) {
        this.productId = productId;
        this.shopId = shopId;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productRate = productRate;
        this.productInfoUpdated = productInfoUpdated;
        this.productStock = productStock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductRate() {
        return productRate;
    }

    public void setProductRate(double productRate) {
        this.productRate = productRate;
    }

    public Timestamp getProductInfoUpdated() {
        return productInfoUpdated;
    }

    public void setProductInfoUpdated(Timestamp productInfoUpdated) {
        this.productInfoUpdated = productInfoUpdated;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", shopId=" + shopId + ", productBarcode=" + productBarcode + ", productName=" + productName + ", productRate=" + productRate + ", productInfoUpdated=" + productInfoUpdated + ", productStock=" + productStock + '}';
    }
    
    
    
    
    
}
