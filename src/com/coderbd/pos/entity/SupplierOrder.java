/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class SupplierOrder {

    private int supplierOrderId;
    private Supplier supplier;
    private Timestamp orderTime;
    private double totalBill;
    private double totalPaid;
    private List<SupplierOrderPayment> supplierOrderPayments;
    private List<SupplierOrderProduct> supplierProducts;

    public SupplierOrder() {
        this.supplierProducts = new ArrayList<SupplierOrderProduct>();
        this.supplierOrderPayments = new ArrayList<SupplierOrderPayment>();
    }

    public SupplierOrder(Supplier supplier, Timestamp orderTime) {

        this.supplier = supplier;
        this.orderTime = orderTime;
        this.supplierProducts = new ArrayList<SupplierOrderProduct>();
        this.supplierOrderPayments = new ArrayList<SupplierOrderPayment>();
    }

    public SupplierOrder(int supplierOrderId, Supplier supplier, Timestamp orderTime, double totalBill, double totalPaid, List<SupplierOrderPayment> supplierOrderPayments, List<SupplierOrderProduct> supplierProducts) {
        this.supplierOrderId = supplierOrderId;
        this.supplier = supplier;
        this.orderTime = orderTime;
        this.totalBill = totalBill;
        this.totalPaid = totalPaid;
        this.supplierOrderPayments = supplierOrderPayments;
        this.supplierProducts = supplierProducts;
    }

    public SupplierOrder(SupplierOrder so) {
        this.supplierOrderId = so.getSupplierOrderId();
        this.supplier = so.getSupplier();
        this.orderTime = so.getOrderTime();
        this.totalBill = so.getTotalBill();
        this.totalPaid = so.getTotalPaid();
        this.supplierOrderPayments = so.getSupplierOrderPayments();
        this.supplierProducts = so.getSupplierProducts();
    }

    public int getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(int supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public List<SupplierOrderPayment> getSupplierOrderPayments() {
        return supplierOrderPayments;
    }

    public void setSupplierOrderPayments(List<SupplierOrderPayment> supplierOrderPayments) {
        this.supplierOrderPayments = supplierOrderPayments;
    }

    public List<SupplierOrderProduct> getSupplierProducts() {
        return supplierProducts;
    }

    public void setSupplierProducts(List<SupplierOrderProduct> supplierProducts) {
        this.supplierProducts = supplierProducts;
    }

    public Double getSupplierOrderTotalPayment() {
        Double value = 0.0;

        if (supplierOrderPayments == null || supplierOrderPayments.isEmpty()) {
            return value;
        }

        for (SupplierOrderPayment sop : supplierOrderPayments) {
            value += sop.getAmount();
        }
        return value;
    }

    public Double getSupplierProductTotalBill() {
        Double value = 0.0;

        if (supplierProducts == null || supplierProducts.isEmpty()) {
            return value;
        }

        for (SupplierOrderProduct sp : supplierProducts) {
            value += (sp.getSupplierRate() * sp.getSupplierProductQuantity());
        }
        return value;
    }

    @Override
    public String toString() {
        return "SupplierOrder{" + "supplierOrderId=" + supplierOrderId + ", supplier=" + supplier + ", orderTime=" + orderTime + ", totalBill=" + totalBill + ", totalPaid=" + totalPaid + ", supplierOrderPayments=" + supplierOrderPayments + ", supplierProducts=" + supplierProducts + '}';
    }

}
