/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

/**
 *
 * @author Biswajit Debnath
 */
public class OrderProduct {

    private int orderProductId;
    private int customerOrderId;
    private int productId;
    private int productQuantity;
    private double productSellRate;
    private double productDiscount;
    private double productVat;

    public OrderProduct() {
    }

    public OrderProduct(int orderProductId, int customerOrderId, int productId, int productQuantity, double productSellRate, double productDiscount, double productVat) {
        this.orderProductId = orderProductId;
        this.customerOrderId = customerOrderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productSellRate = productSellRate;
        this.productDiscount = productDiscount;
        this.productVat = productVat;
    }

    public OrderProduct(int customerOrderId, int productId, int productQuantity, double productSellRate, double productDiscount, double productVat) {
        this.orderProductId = orderProductId;
        this.customerOrderId = customerOrderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productSellRate = productSellRate;
        this.productDiscount = productDiscount;
        this.productVat = productVat;
    }

    public int getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(int orderProductId) {
        this.orderProductId = orderProductId;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductSellRate() {
        return productSellRate;
    }

    public void setProductSellRate(double productSellRate) {
        this.productSellRate = productSellRate;
    }

    public double getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(double productDiscount) {
        this.productDiscount = productDiscount;
    }

    public double getProductVat() {
        return productVat;
    }

    public void setProductVat(double productVat) {
        this.productVat = productVat;
    }

    @Override
    public String toString() {
        return "CustomerOrderProduct{" + "OrderProductId=" + orderProductId + ", customerOrderId=" + customerOrderId + ", productId=" + productId + ", productQuantity=" + productQuantity + ", productSellRate=" + productSellRate + ", productDiscount=" + productDiscount + ", productVat=" + productVat + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.orderProductId;
        hash = 37 * hash + this.customerOrderId;
        hash = 37 * hash + this.productId;
        hash = 37 * hash + this.productQuantity;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.productSellRate) ^ (Double.doubleToLongBits(this.productSellRate) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.productDiscount) ^ (Double.doubleToLongBits(this.productDiscount) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.productVat) ^ (Double.doubleToLongBits(this.productVat) >>> 32));
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
        final OrderProduct other = (OrderProduct) obj;
        if (this.orderProductId != other.orderProductId) {
            return false;
        }
        if (this.customerOrderId != other.customerOrderId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        if (this.productQuantity != other.productQuantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.productSellRate) != Double.doubleToLongBits(other.productSellRate)) {
            return false;
        }
        if (Double.doubleToLongBits(this.productDiscount) != Double.doubleToLongBits(other.productDiscount)) {
            return false;
        }
        if (Double.doubleToLongBits(this.productVat) != Double.doubleToLongBits(other.productVat)) {
            return false;
        }
        return true;
    }

}
