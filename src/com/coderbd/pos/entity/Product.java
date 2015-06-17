/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author Biswajit Debnath
 */
public class Product {

    private int productId;
    private int supplierProductId;
    private int shopId;
    private Shop shop;
    private String productBarcode;
    private String productName;
    private double productBuyRate;
    private double productSellRate;
    private Timestamp productInfoUpdated;
    private int productStock;

    public Product() {
    }

    public Product(int shopId, String productBarcode, String productName, double productBuyRate, double productSellRate, int productStock) {

        this.shopId = shopId;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productBuyRate = productBuyRate;
        this.productSellRate = productSellRate;
        this.productStock = productStock;
    }

    public Product(int shopId, String productBarcode, String productName, double productBuyRate, double productSellRate, Timestamp productInfoUpdated, int productStock) {

        this.shopId = shopId;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productBuyRate = productBuyRate;
        this.productSellRate = productSellRate;
        this.productInfoUpdated = productInfoUpdated;
        this.productStock = productStock;
    }

    public Product(int productId, int shopId, String productBarcode, String productName, double productBuyRate, double productSellRate, Timestamp productInfoUpdated, int productStock) {
        this.productId = productId;
        this.shopId = shopId;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productBuyRate = productBuyRate;
        this.productSellRate = productSellRate;
        this.productInfoUpdated = productInfoUpdated;
        this.productStock = productStock;
    }

    public Product(int productId, int supplierProductId, int shopId, String productBarcode, String productName, double productBuyRate, double productSellRate, Timestamp productInfoUpdated, int productStock) {
        this.productId = productId;
        this.supplierProductId = supplierProductId;
        this.shopId = shopId;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productBuyRate = productBuyRate;
        this.productSellRate = productSellRate;
        this.productInfoUpdated = productInfoUpdated;
        this.productStock = productStock;
    }

    public Product(int productId, int supplierProductId, int shopId, Shop shop, String productBarcode, String productName, double productBuyRate, double productSellRate, Timestamp productInfoUpdated, int productStock) {
        this.productId = productId;
        this.supplierProductId = supplierProductId;
        this.shopId = shopId;
        this.shop = shop;
        this.productBarcode = productBarcode;
        this.productName = productName;
        this.productBuyRate = productBuyRate;
        this.productSellRate = productSellRate;
        this.productInfoUpdated = productInfoUpdated;
        this.productStock = productStock;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(int supplierProductId) {
        this.supplierProductId = supplierProductId;
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

    public double getProductBuyRate() {
        return productBuyRate;
    }

    public void setProductBuyRate(double productBuyRate) {
        this.productBuyRate = productBuyRate;
    }

    public double getProductSellRate() {
        return productSellRate;
    }

    public void setProductSellRate(double productSellRate) {
        this.productSellRate = productSellRate;
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
        return "Product{" + "productId=" + productId + ", supplierProductId=" + supplierProductId + ", shopId=" + shopId + ", shop=" + shop + ", productBarcode=" + productBarcode + ", productName=" + productName + ", productBuyRate=" + productBuyRate + ", productSellRate=" + productSellRate + ", productInfoUpdated=" + productInfoUpdated + ", productStock=" + productStock + '}';
    }

}
