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
    private int shopId;
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
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.productId;
        hash = 61 * hash + this.shopId;
        hash = 61 * hash + Objects.hashCode(this.productBarcode);
        hash = 61 * hash + Objects.hashCode(this.productName);
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.productBuyRate) ^ (Double.doubleToLongBits(this.productBuyRate) >>> 32));
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.productSellRate) ^ (Double.doubleToLongBits(this.productSellRate) >>> 32));
        hash = 61 * hash + Objects.hashCode(this.productInfoUpdated);
        hash = 61 * hash + this.productStock;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.shopId != other.shopId) {
            return false;
        }
        if (!Objects.equals(this.productBarcode, other.productBarcode)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (Double.doubleToLongBits(this.productBuyRate) != Double.doubleToLongBits(other.productBuyRate)) {
            return false;
        }
        if (Double.doubleToLongBits(this.productSellRate) != Double.doubleToLongBits(other.productSellRate)) {
            return false;
        }
        if (!Objects.equals(this.productInfoUpdated, other.productInfoUpdated)) {
            return false;
        }
        if (this.productStock != other.productStock) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", shopId=" + shopId + ", productBarcode=" + productBarcode + ", productName=" + productName + ", productBuyRate=" + productBuyRate + ", productSellRate=" + productSellRate + ", productInfoUpdated=" + productInfoUpdated + ", productStock=" + productStock + '}';
    }

}
