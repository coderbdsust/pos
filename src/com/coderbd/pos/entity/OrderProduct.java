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
public class OrderProduct extends Product {

    private int orderProductId;
    private int customerOrderId;
    private int productId;
    private int orderProductQuantity;
    private double orderProductSellRate;
    private double orderProductDiscount;
    private double orderProductVat;

    public OrderProduct() {
    }

    public OrderProduct(int customerOrderId, int productId, int orderProductQuantity, double orderProductSellRate, double orderProductDiscount, double orderProductVat) {

        this.customerOrderId = customerOrderId;
        this.productId = productId;
        this.orderProductQuantity = orderProductQuantity;
        this.orderProductSellRate = orderProductSellRate;
        this.orderProductDiscount = orderProductDiscount;
        this.orderProductVat = orderProductVat;
    }

    public OrderProduct(int orderProductId, int customerOrderId, int productId, int orderProductQuantity, double orderProductSellRate, double orderProductDiscount, double orderProductVat) {
        this.orderProductId = orderProductId;
        this.customerOrderId = customerOrderId;
        this.productId = productId;
        this.orderProductQuantity = orderProductQuantity;
        this.orderProductSellRate = orderProductSellRate;
        this.orderProductDiscount = orderProductDiscount;
        this.orderProductVat = orderProductVat;
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

    @Override
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int getProductId() {
        return this.productId;
    }

    public int getOrderProductQuantity() {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(int orderProductQuantity) {
        this.orderProductQuantity = orderProductQuantity;
    }

    public double getOrderProductSellRate() {
        return orderProductSellRate;
    }

    public void setOrderProductSellRate(double orderProductSellRate) {
        this.orderProductSellRate = orderProductSellRate;
    }

    public double getOrderProductDiscount() {
        return orderProductDiscount;
    }

    public void setOrderProductDiscount(double orderProductDiscount) {
        this.orderProductDiscount = orderProductDiscount;
    }

    public double getOrderProductVat() {
        return orderProductVat;
    }

    public void setOrderProductVat(double orderProductVat) {
        this.orderProductVat = orderProductVat;
    }

    @Override
    public String toString() {
        return "OrderProduct{" + "orderProductId=" + orderProductId + ", customerOrderId=" + customerOrderId + ", productId=" + productId + ", orderProductQuantity=" + orderProductQuantity + ", orderProductSellRate=" + orderProductSellRate + ", orderProductDiscount=" + orderProductDiscount + ", orderProductVat=" + orderProductVat + '}';
    }

}
