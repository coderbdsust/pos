/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.entity.pojo;

import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.OrderProduct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Biswajit Debnath
 */
public class ShopOrder {

    private CustomerOrder customerOrder;
    private List<OrderProduct> orderProducts;

    public ShopOrder() {
        this.customerOrder = new CustomerOrder();
        this.orderProducts = new ArrayList<OrderProduct>();
    }

    public ShopOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
        this.orderProducts = new ArrayList<OrderProduct>();
    }

    public ShopOrder(CustomerOrder customerOrder, List<OrderProduct> orderProduct) {
        this.customerOrder = customerOrder;
        this.orderProducts = orderProduct;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public String toString() {
        return "ShopOrder{" + "customerOrder=" + customerOrder + ", orderProducts=" + orderProducts + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.customerOrder);
        hash = 11 * hash + Objects.hashCode(this.orderProducts);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShopOrder other = (ShopOrder) obj;
        if (!Objects.equals(this.customerOrder, other.customerOrder)) {
            return false;
        }
        if (!Objects.equals(this.orderProducts, other.orderProducts)) {
            return false;
        }
        return true;
    }
    
    

}
