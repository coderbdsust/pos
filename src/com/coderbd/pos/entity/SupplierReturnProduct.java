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
public class SupplierReturnProduct {
    
    private int supplierReturnProductId;
    private int supplierProductId;
    private Timestamp returnTime;
    private int quantity;

    public SupplierReturnProduct(int supplierReturnProductId, int supplierProductId, Timestamp returnTime, int quantity) {
        this.supplierReturnProductId = supplierReturnProductId;
        this.supplierProductId = supplierProductId;
        this.returnTime = returnTime;
        this.quantity = quantity;
    }

    public SupplierReturnProduct() {
    }

    public int getSupplierReturnProductId() {
        return supplierReturnProductId;
    }

    public void setSupplierReturnProductId(int supplierReturnProductId) {
        this.supplierReturnProductId = supplierReturnProductId;
    }

    public int getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(int supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SupplierReturnProduct{" + "supplierReturnProductId=" + supplierReturnProductId + ", supplierProductId=" + supplierProductId + ", returnTime=" + returnTime + ", quantity=" + quantity + '}';
    }
    
    

}
