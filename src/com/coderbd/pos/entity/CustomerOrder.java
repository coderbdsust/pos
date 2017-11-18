/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Biswajit Debnath
 */
public class CustomerOrder {

    private int customerOrderId;
    private String orderBarcode;
    private Timestamp orderTime;
    private Shop shop;
    private User user;
    private String buyerName;
    private String buyerMobile;
    private double totalAmount;
    private double totalPaid;
    private double totalDue;

    public CustomerOrder() {
    }

    public CustomerOrder(String orderBarcode, Timestamp orderTime, Shop shop, User user) {
        this.orderBarcode = orderBarcode;
        this.orderTime = orderTime;
        this.shop = shop;
        this.user = user;
    }

    public CustomerOrder(int customerOrderId, String orderBarcode, Timestamp orderTime, Shop shop, User user, String buyerName, String buyerMobile, double totalAmount, double totalPaid, double totalDue) {
        this.customerOrderId = customerOrderId;
        this.orderBarcode = orderBarcode;
        this.orderTime = orderTime;
        this.shop = shop;
        this.user = user;
        this.buyerName = buyerName;
        this.buyerMobile = buyerMobile;
        this.totalAmount = totalAmount;
        this.totalPaid = totalPaid;
        this.totalDue = totalDue;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public String getOrderBarcode() {
        return orderBarcode;
    }

    public void setOrderBarcode(String orderBarcode) {
        this.orderBarcode = orderBarcode;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" + "customerOrderId=" + customerOrderId + ", orderBarcode=" + orderBarcode + ", orderTime=" + orderTime + ", shop=" + shop + ", user=" + user + ", buyerName=" + buyerName + ", buyerMobile=" + buyerMobile + ", totalAmount=" + totalAmount + ", totalPaid=" + totalPaid + ", totalDue=" + totalDue + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.customerOrderId;
        hash = 67 * hash + Objects.hashCode(this.orderBarcode);
        hash = 67 * hash + Objects.hashCode(this.orderTime);
        hash = 67 * hash + Objects.hashCode(this.shop);
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.buyerName);
        hash = 67 * hash + Objects.hashCode(this.buyerMobile);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.totalAmount) ^ (Double.doubleToLongBits(this.totalAmount) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.totalPaid) ^ (Double.doubleToLongBits(this.totalPaid) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.totalDue) ^ (Double.doubleToLongBits(this.totalDue) >>> 32));
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
        final CustomerOrder other = (CustomerOrder) obj;
        if (this.customerOrderId != other.customerOrderId) {
            return false;
        }
        if (!Objects.equals(this.orderBarcode, other.orderBarcode)) {
            return false;
        }
        if (!Objects.equals(this.orderTime, other.orderTime)) {
            return false;
        }
        if (!Objects.equals(this.shop, other.shop)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.buyerName, other.buyerName)) {
            return false;
        }
        if (!Objects.equals(this.buyerMobile, other.buyerMobile)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalAmount) != Double.doubleToLongBits(other.totalAmount)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalPaid) != Double.doubleToLongBits(other.totalPaid)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalDue) != Double.doubleToLongBits(other.totalDue)) {
            return false;
        }
        return true;
    }

    public static CustomerOrder copy(CustomerOrder co) {

        CustomerOrder newCO = new CustomerOrder();

        newCO.setBuyerMobile(co.getBuyerMobile());
        newCO.setBuyerName(co.getBuyerName());
        newCO.setCustomerOrderId(co.getCustomerOrderId());
        newCO.setOrderBarcode(co.getOrderBarcode());
        newCO.setOrderTime(co.getOrderTime());
        newCO.setShop(co.getShop());
        newCO.setTotalAmount(co.getTotalAmount());
        newCO.setTotalDue(co.getTotalDue());
        newCO.setTotalPaid(co.getTotalPaid());
        newCO.setUser(co.getUser());

        return newCO;
    }

}
