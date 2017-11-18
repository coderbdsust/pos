/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.index;

/**
 *
 * @author Biswajit Debnath
 */
public class SOPIndex {

    private int supplierIndex;
    private int supplierOrderIndex;
    private int productIndex;

    public SOPIndex(int supplierOrderIndex, int productIndex) {
        this.supplierOrderIndex = supplierOrderIndex;
        this.productIndex = productIndex;
    }

    public SOPIndex(int supplierIndex, int supplierOrderIndex, int productIndex) {
        this.supplierIndex = supplierIndex;
        this.supplierOrderIndex = supplierOrderIndex;
        this.productIndex = productIndex;
    }

    public int getSupplierIndex() {
        return supplierIndex;
    }

    public void setSupplierIndex(int supplierIndex) {
        this.supplierIndex = supplierIndex;
    }

    public int getSupplierOrderIndex() {
        return supplierOrderIndex;
    }

    public void setSupplierOrderIndex(int supplierOrderIndex) {
        this.supplierOrderIndex = supplierOrderIndex;
    }

    public int getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(int productIndex) {
        this.productIndex = productIndex;
    }

    @Override
    public String toString() {
        return "SupplierDistributionProductIndex{" + "supplierIndex=" + supplierIndex + ", supplierOrderIndex=" + supplierOrderIndex + ", productIndex=" + productIndex + '}';
    }

}
