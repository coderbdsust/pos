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
public class ShopCustomerOrder {

    private int customerOrderId;
    private Timestamp orderTime;
    private int shopId;
    private String buyerName;
    private String buyerMobile;

    public ShopCustomerOrder() {
    }

    public ShopCustomerOrder(int customerOrderId, Timestamp orderTime, int shopId, String buyerName) {
        this.customerOrderId = customerOrderId;
        this.orderTime = orderTime;
        this.shopId = shopId;
        this.buyerName = buyerName;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    @Override
    public String toString() {
        return "ShopCustomerOrder{" + "customerOrderId=" + customerOrderId + ", orderTime=" + orderTime + ", shopId=" + shopId + ", buyerName=" + buyerName + ", buyerMobile=" + buyerMobile + '}';
    }

}
