/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils.adder;

import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.entity.pojo.SupplierOrderProductReport;
import com.coderbd.pos.service.CustomerOrderService;
import com.coderbd.pos.service.SupplierReturnProductService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class SupplierOrderProductReportAdder {

    private CustomerOrderService customerOrderService;
    private SupplierReturnProductService supplierReturnProductService;

    public SupplierOrderProductReportAdder(CustomerOrderService cos, SupplierReturnProductService srps) {
        this.customerOrderService = cos;
        this.supplierReturnProductService = srps;
    }

    public List getSupplierOrderProductReports(List<SupplierOrderProduct> sops) {

        List<SupplierOrderProductReport> soprs = new ArrayList<SupplierOrderProductReport>();
        if (sops != null) {
            for (SupplierOrderProduct sop : sops) {
                int soldQuantity = customerOrderService.countSupplierProductSoldQuantity(sop.getSupplierProductId());
                int returnQuantity = supplierReturnProductService.countSupplierReturnProduct(sop.getSupplierOrderId());
                SupplierOrderProductReport sopr = new SupplierOrderProductReport(sop, soldQuantity, returnQuantity);
                soprs.add(sopr);
            }
            return soprs;
        } else {
            return null;
        }
    }

}
