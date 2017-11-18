/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity.pojo;

import com.coderbd.pos.entity.SupplierOrder;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class SupplierOrderReport extends SupplierOrder {
    
    private List<SupplierOrderProductReport> supplierOrderProductReports;
    
    public SupplierOrderReport(List<SupplierOrderProductReport> supplierOrderProductReports, SupplierOrder so) {
        super(so);
        this.supplierOrderProductReports = supplierOrderProductReports;
    }
    
    public List<SupplierOrderProductReport> getSupplierOrderProductReports() {
        return supplierOrderProductReports;
    }
    
    public void setSupplierOrderProductReports(List<SupplierOrderProductReport> supplierOrderProductReports) {
        this.supplierOrderProductReports = supplierOrderProductReports;
    }
    
    public Double getOrderTotalUnsoldAmount() {
        double value = 0.0;
        for (SupplierOrderProductReport sopr : supplierOrderProductReports) {
            value += sopr.getUnSoldProductAmount();
        }
        return value;
    }
    
    public Double getOrderTotalSoldAmount() {
        double value = 0.0;
        for (SupplierOrderProductReport sopr : supplierOrderProductReports) {
            value += sopr.getSoldProductAmount();
        }
        return value;
    }
    
    @Override
    public String toString() {
        return "SupplierOrderReport{" + "supplierOrderProductReports=" + supplierOrderProductReports + '}';
    }
    
}
