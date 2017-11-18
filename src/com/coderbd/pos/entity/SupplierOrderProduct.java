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
public class SupplierOrderProduct {

    private int supplierProductId;
    private int supplierOrderId;
    private String supplierProductName;
    private double supplierRate;
    private int supplierProductQuantity;

    public SupplierOrderProduct() {
    }

    public SupplierOrderProduct(int supplierProductId, int supplierOrderId, String supplierProductName, double supplierRate, int supplierProductQuantity) {
        this.supplierProductId = supplierProductId;
        this.supplierOrderId = supplierOrderId;
        this.supplierProductName = supplierProductName;
        this.supplierRate = supplierRate;
        this.supplierProductQuantity = supplierProductQuantity;
    }

    public int getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(int supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    public int getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(int supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public String getSupplierProductName() {
        return supplierProductName;
    }

    public void setSupplierProductName(String supplierProductName) {
        this.supplierProductName = supplierProductName;
    }

    public double getSupplierRate() {
        return supplierRate;
    }

    public void setSupplierRate(double supplierRate) {
        this.supplierRate = supplierRate;
    }

    public int getSupplierProductQuantity() {
        return supplierProductQuantity;
    }

    public void setSupplierProductQuantity(int supplierProductQuantity) {
        this.supplierProductQuantity = supplierProductQuantity;
    }

    @Override
    public String toString() {
        return "SupplierOrderProduct{" + "supplierProductId=" + supplierProductId + ", supplierOrderId=" + supplierOrderId + ", supplierProductName=" + supplierProductName + ", supplierRate=" + supplierRate + ", supplierProductQuantity=" + supplierProductQuantity + '}';
    }

    
}
