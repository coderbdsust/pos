/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.SupplierProductsDao;
import com.coderbd.pos.entity.SupplierOrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("supplierProductService")
public class SupplierProductService {

    @Autowired
    private SupplierProductsDao supplierProductsDao;

    public int saveSupplierProduct(SupplierOrderProduct sop) {
        return supplierProductsDao.saveSupplierOrderProduct(sop);
    }

    public boolean deleteSupplierProduct(SupplierOrderProduct sop) {
        return supplierProductsDao.deleteSupplierOrderProduct(sop);
    }

    public int countProductSoldQuantity(int supplierProductId) {
        return supplierProductsDao.countSupplierProductSoldQuantity(supplierProductId);
    }

}
