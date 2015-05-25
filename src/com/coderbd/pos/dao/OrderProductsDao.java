/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.OrderProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Biswajit Debnath
 */
@Component("orderProductDao")
public class OrderProductsDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<OrderProduct> getCustomerOrders(CustomerOrder customerOrder) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customer_order_id", customerOrder.getCustomerOrderId());

        try {
            return jdbc.query("select * from pos.order_products natural join pos.products where customer_order_id=:customer_order_id", params, new RowMapper<OrderProduct>() {
                public OrderProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrderProductId(rs.getInt("order_product_id"));
                    orderProduct.setCustomerOrderId(rs.getInt("customer_order_id"));
                    orderProduct.setProductId(rs.getInt("product_id"));
                    orderProduct.setProductName(rs.getString("product_name"));
                    orderProduct.setOrderProductQuantity(rs.getInt("order_product_quantity"));
                    orderProduct.setOrderProductSellRate(rs.getDouble("order_product_sell_rate"));
                    orderProduct.setOrderProductDiscount(rs.getDouble("order_product_discount"));
                    orderProduct.setOrderProductVat(rs.getDouble("order_product_vat"));
                    return orderProduct;
                }
            });
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return null;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return null;
        }

    }

    public boolean saveOrderProduct(OrderProduct orderProduct) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customer_order_id", orderProduct.getCustomerOrderId());
        params.addValue("product_id", orderProduct.getProductId());
        params.addValue("order_product_quantity", orderProduct.getOrderProductQuantity());
        params.addValue("order_product_sell_rate", orderProduct.getOrderProductSellRate());
        params.addValue("order_product_discount", orderProduct.getOrderProductDiscount());
        params.addValue("order_product_vat", orderProduct.getOrderProductVat());

        try {
            jdbc.update("insert into pos.order_products (customer_order_id, product_id,order_product_quantity, order_product_sell_rate, order_product_discount, order_product_vat) "
                    + "values (:customer_order_id, :product_id, :order_product_quantity, :order_product_sell_rate, :order_product_discount, :order_product_vat)", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }

    }

}
