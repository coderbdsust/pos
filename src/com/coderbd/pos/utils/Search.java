/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.utils;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.OrderProduct;
import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.Supplier;
import com.coderbd.pos.entity.SupplierOrderProduct;
import com.coderbd.pos.entity.pojo.ShopOrder;
import com.coderbd.pos.utils.index.SOPIndex;
import java.util.List;

/**
 *
 * @author Biswajit Debnath
 */
public class Search {

    public int searchProduct(List<Product> products, String productBarcode) {
        int searchIndex = 0;
        for (Product product : products) {
            if (product.getProductBarcode().equals(productBarcode)) {
                return searchIndex;
            }
            searchIndex++;
        }
        return Enum.invalidIndex;
    }

    public int searchOrderProduct(List<OrderProduct> orderProducts, String productBarcode) {
        int searchIndex = 0;
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getProductBarcode().equals(productBarcode)) {
                return searchIndex;
            }
            searchIndex++;
        }
        return Enum.invalidIndex;
    }

    public int searchCustomerOrderFromShopOrder(List<ShopOrder> shopOrders, String orderCode) {
        int searchIndex = 0;

        for (ShopOrder shopOrder : shopOrders) {
            if (shopOrder.getCustomerOrder().getOrderBarcode().equals(orderCode)) {
                return searchIndex;
            }
            searchIndex++;
        }
        return Enum.invalidIndex;
    }

    public SupplierOrderProduct searchSupplierOrderProduct(List<Supplier> suppliers, SOPIndex sopIndex) {

        SupplierOrderProduct sop = suppliers.get(sopIndex.getSupplierIndex())
                .getSupplierOrders()
                .get(sopIndex.getSupplierOrderIndex())
                .getSupplierProducts()
                .get(sopIndex.getProductIndex());

        return sop;
    }

}
