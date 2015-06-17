/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.SupplierReturnProductsDao;
import com.coderbd.pos.entity.SupplierReturnProduct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("supplierReturnProductService")
public class SupplierReturnProductService {

    @Autowired
    private SupplierReturnProductsDao supplierReturnProductsDao;

    public int saveSupplierReturnProduct(SupplierReturnProduct srp) {
        return supplierReturnProductsDao.saveSupplierReturnProduct(srp);
    }

    public int countSupplierReturnProduct(int supplierProductId) {
        return supplierReturnProductsDao.countSupplierReturnProduct(supplierProductId);
    }

    public List<SupplierReturnProduct> getSupplierReturnProducts(int supplierProductId) {
        return supplierReturnProductsDao.getSupplierReturnProducts(supplierProductId);
    }

}
