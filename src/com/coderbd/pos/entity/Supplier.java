/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class Supplier {

    private int supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierMobile;
    private User user;
    private List<SupplierOrder> supplierOrders;

    public Supplier() {
        this.supplierOrders = new ArrayList<SupplierOrder>();
    }

    public Supplier(User user) {
        this.user = user;
        this.supplierOrders = new ArrayList<SupplierOrder>();
    }

    public Supplier(String supplierName, String supplierAddress, String supplierMobile, User user) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierMobile = supplierMobile;
        this.user = user;
        this.supplierOrders = new ArrayList<SupplierOrder>();
    }

    public Supplier(int supplierId, String supplierName, String supplierAddress, String supplierMobile, User user) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierMobile = supplierMobile;
        this.user = user;
        this.supplierOrders = new ArrayList<SupplierOrder>();
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierMobile() {
        return supplierMobile;
    }

    public void setSupplierMobile(String supplierMobile) {
        this.supplierMobile = supplierMobile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SupplierOrder> getSupplierOrders() {
        return supplierOrders;
    }

    public void setSupplierOrders(List<SupplierOrder> supplierOrders) {
        this.supplierOrders = supplierOrders;
    }

    @Override
    public String toString() {
        return "Supplier{" + "supplierId=" + supplierId + ", supplierName=" + supplierName + ", supplierAddress=" + supplierAddress + ", supplierMobile=" + supplierMobile + ", user=" + user + '}';
    }

}
