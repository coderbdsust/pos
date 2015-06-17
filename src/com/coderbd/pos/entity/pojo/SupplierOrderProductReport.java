/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity.pojo;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.SupplierOrderProduct;

/**
 *
 * @author Biswajit Debnath
 */
public class SupplierOrderProductReport {

    private SupplierOrderProduct supplierOrderProduct;
    private int soldQuantity;
    private int supplierReturnQuantity;

    public SupplierOrderProductReport(SupplierOrderProduct supplierOrderProduct) {
        this.supplierOrderProduct = supplierOrderProduct;
        this.soldQuantity = 0;
        this.supplierReturnQuantity = 0;
    }

    public SupplierOrderProductReport(SupplierOrderProduct supplierOrderProduct, int soldQuantity) {
        this.supplierOrderProduct = supplierOrderProduct;
        this.soldQuantity = (soldQuantity == -1) ? 0 : soldQuantity;
        this.supplierReturnQuantity = 0;
    }

    public SupplierOrderProductReport(SupplierOrderProduct supplierOrderProduct, int soldQuantity, int returnQuantity) {
        this.supplierOrderProduct = supplierOrderProduct;
        this.soldQuantity = (soldQuantity == -1) ? 0 : soldQuantity;
        this.supplierReturnQuantity = (returnQuantity == -1) ? 0 : returnQuantity;
    }

    public SupplierOrderProduct getSupplierOrderProduct() {
        return supplierOrderProduct;
    }

    public void setSupplierOrderProduct(SupplierOrderProduct supplierOrderProduct) {
        this.supplierOrderProduct = supplierOrderProduct;
    }

    public int getSupplierReturnQuantity() {
        return supplierReturnQuantity;
    }

    public double getSupplierReturnQuantityAmount() {
        return supplierReturnQuantity * supplierOrderProduct.getSupplierRate();
    }

    public void setSupplierReturnQuantity(int supplierReturnQuantity) {
        this.supplierReturnQuantity = supplierReturnQuantity;
    }

    public void setSoldProductQuantity(int soldQuantity) {
        if (soldQuantity != Enum.invalidIndex) {
            this.soldQuantity = soldQuantity;
        }
    }

    public int getSoldProductQuantity() {
        return this.soldQuantity;
    }

    public Double getSoldProductAmount() {
        return this.getSoldProductQuantity() * supplierOrderProduct.getSupplierRate();
    }

    public int getUnSoldProductQuantity() {
        return supplierOrderProduct.getSupplierProductQuantity() - this.soldQuantity;
    }

    public double getUnSoldProductAmount() {
        return getUnSoldProductQuantity() * supplierOrderProduct.getSupplierRate();
    }

    public int getCurrentStock() {
        return getUnSoldProductQuantity() - getSupplierReturnQuantity();
    }

    @Override
    public String toString() {
        return "SupplierOrderProductReport{" + "SupplierOrderProduct=" + supplierOrderProduct + ", soldQuantity=" + soldQuantity + ", supplierReturnQuantity=" + supplierReturnQuantity + '}';
    }

}
