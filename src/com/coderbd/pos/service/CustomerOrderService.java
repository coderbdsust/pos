/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.CustomerOrdersDao;
import com.coderbd.pos.dao.OrderProductsDao;
import com.coderbd.pos.dao.ProductsDao;
import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.OrderProduct;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.User;
import com.coderbd.pos.entity.pojo.ShopOrder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Biswajit Debnath
 */
@Service("customerOrderService")
public class CustomerOrderService {

    @Autowired
    private CustomerOrdersDao customerOrdersDao;
    @Autowired
    private OrderProductsDao orderProductsDao;
    @Autowired
    private ProductsDao productsDao;

    @Transactional
    public boolean completeShopOrder(ShopOrder shopOrder) {
        boolean complete = true;
        CustomerOrder customerOrder = shopOrder.getCustomerOrder();
        complete = complete & customerOrdersDao.saveCustomerOrder(customerOrder);
        if (complete == true) {
            Integer customerOrderId = customerOrdersDao.getCustomerOrderId(customerOrder.getOrderBarcode());
            List<OrderProduct> orderProducts = shopOrder.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts) {
                orderProduct.setCustomerOrderId(customerOrderId);
                complete = complete & productsDao.updateProductStock(orderProduct.getProductId(), orderProduct.getOrderProductQuantity());
                complete = complete & orderProductsDao.saveOrderProduct(orderProduct);
            }
        }
        return complete;
    }

    public List<ShopOrder> getShopOrders(Shop shop, User user) {

        List<ShopOrder> shopOrders = new ArrayList<ShopOrder>();

        List<CustomerOrder> customerOrders = customerOrdersDao.getCustomerOrders(shop, user);

        for (CustomerOrder customerOrder : customerOrders) {
            List<OrderProduct> orderProducts = orderProductsDao.getCustomerOrders(customerOrder);
            ShopOrder shopOrder = new ShopOrder(customerOrder, orderProducts);
            shopOrders.add(shopOrder);
        }
        return shopOrders;
    }

    @Transactional
    public void cancelShopOrder(ShopOrder shopOrder) {
        List<OrderProduct> orderProducts = shopOrder.getOrderProducts();
        for (OrderProduct orderProduct : orderProducts) {
            productsDao.updateProductStock(orderProduct.getProductId(), -1 * orderProduct.getOrderProductQuantity());
        }
        customerOrdersDao.deleteCustomerOrder(shopOrder.getCustomerOrder());

    }

    public void getAllShopSellReport(List<Shop> shop, Timestamp fromDate, Timestamp toDate) {

    }

    public boolean updateCustomerOrder(CustomerOrder dummyOrder) {
        return customerOrdersDao.updateCustomerOrderAmount(dummyOrder);
    }

    public int countSupplierProductSoldQuantity(int supplierProductId) {
        return orderProductsDao.countProductSoldQuantity(supplierProductId);
    }
}
