/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.ProductsDao;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.Shop;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("productService")
public class ProductService {

    @Autowired
    private ProductsDao productsDao;

    public Product getProduct(Shop shop, String barcode) {
        return productsDao.getProduct(shop, barcode);
    }

    public boolean createProduct(Product product) {
        return productsDao.createProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productsDao.updateProduct(product);
    }

    public List<Product> getProducts(Shop shop) {
        return productsDao.getProducts(shop);
    }

    public boolean deleteProductById(int productId) {
        return productsDao.deleteProductById(productId);
    }

}
