/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.SupplierOrderPaymentsDao;
import com.coderbd.pos.dao.SupplierOrdersDao;
import com.coderbd.pos.dao.SupplierProductsDao;
import com.coderbd.pos.dao.SuppliersDao;
import com.coderbd.pos.entity.Supplier;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.SupplierOrderPayment;
import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("supplierService")
public class SupplierService {

    @Autowired
    private SuppliersDao suppliersDao;

    @Autowired
    private SupplierOrdersDao supplierOrdersDao;

    @Autowired
    private SupplierOrderPaymentsDao supplierOrderPaymentsDao;

    @Autowired
    private SupplierProductsDao supplierProductsDao;

    public int createSupplier(Supplier supplier) {
        return suppliersDao.createSupplier(supplier);
    }

    public List<Supplier> getSuppliers(User user) {

        List<Supplier> suppliers = suppliersDao.getSuppliers(user);
        System.out.println("S: " + suppliers);
        for (Supplier supplier : suppliers) {
            List<SupplierOrder> supplierOrders = supplierOrdersDao.getSupplierOrders(supplier);
            System.out.println("SO: " +supplierOrders);
            for (SupplierOrder so : supplierOrders) {
                List<SupplierOrderPayment> supplierOrderPayments = supplierOrderPaymentsDao.getSupplierOrderPayments(so);
                System.out.println("payments:" + supplierOrderPayments);
                List<SupplierOrderProduct> supplierOrderProducts = supplierProductsDao.getSupplierOrderProducts(so);
                System.out.println("products:" + supplierOrderProducts);
                so.setSupplierOrderPayments(supplierOrderPayments);
                so.setSupplierProducts(supplierOrderProducts);
            }
            supplier.setSupplierOrders(supplierOrders);
        }
        return suppliers;
    }
    
    public boolean deleteSupplierOrder(SupplierOrder so){
        return suppliersDao.deleteSupplierOrder(so);
    }
}
