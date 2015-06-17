/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.SupplierOrdersDao;
import com.coderbd.pos.entity.SupplierOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("supplierOrderService")
public class SupplierOrderService {

    @Autowired
    private SupplierOrdersDao supplierOrdersDao;

    public int createSupplierOrder(SupplierOrder so) {
        return supplierOrdersDao.createSupplierOrder(so);
    }

    public boolean updateSupplierOrder(SupplierOrder so) {
        return supplierOrdersDao.updateSupplierOrder(so);
    }

}
