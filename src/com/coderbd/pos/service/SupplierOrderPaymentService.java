/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.SupplierOrderPaymentsDao;
import com.coderbd.pos.entity.SupplierOrderPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("supplierOrderPaymentService")
public class SupplierOrderPaymentService {

    @Autowired
    private SupplierOrderPaymentsDao supplierOrderPaymentsDao;

    public int saveSupplierOrderPayment(SupplierOrderPayment sop) {
        return supplierOrderPaymentsDao.saveSupplierOrderPayment(sop);
    }

    public boolean deleteSupplierOrderPayment(SupplierOrderPayment sop) {
        return supplierOrderPaymentsDao.deleteSupplierOrderPayment(sop);
    }

}
