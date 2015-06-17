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
public class SupplierOrderPayment {

    private int supplierOrderPaymentId;
    private int supplierOrderId;
    private Timestamp paymentDate;
    private double amount;
    private String description;

    public SupplierOrderPayment() {
    }

    public SupplierOrderPayment(Timestamp paymentDate, double amount, String description) {
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.description = description;
    }

    public SupplierOrderPayment(int supplierOrderPaymentId, int supplierOrderId, Timestamp paymentDate, double amount, String description) {
        this.supplierOrderPaymentId = supplierOrderPaymentId;
        this.supplierOrderId = supplierOrderId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.description = description;
    }

    public int getSupplierOrderPaymentId() {
        return supplierOrderPaymentId;
    }

    public void setSupplierOrderPaymentId(int supplierOrderPaymentId) {
        this.supplierOrderPaymentId = supplierOrderPaymentId;
    }

    public int getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(int supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SupplierOrderPayment{" + "supplierOrderPaymentId=" + supplierOrderPaymentId + ", supplierOrderId=" + supplierOrderId + ", paymentDate=" + paymentDate + ", amount=" + amount + ", description=" + description + '}';
    }



   

}
