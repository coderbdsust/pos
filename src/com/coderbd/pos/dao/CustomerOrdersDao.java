/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.CustomerOrder;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.User;
import com.coderbd.pos.entity.pojo.ShopOrder;
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
@Component("customerOrderDao")
public class CustomerOrdersDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean completeOrder(ShopOrder shopOrder) {
        return false;

    }

    public List<CustomerOrder> getCustomerOrders(Shop shop, User user) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shop.getShopId());

        try {
            return jdbc.query("select * from pos.customer_orders where shop_id=:shop_id order by order_time desc", params, new RowMapper<CustomerOrder>() {
                public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerOrder customerOrder = new CustomerOrder();
                    customerOrder.setCustomerOrderId(rs.getInt("customer_order_id"));
                    customerOrder.setOrderBarcode(rs.getString("order_barcode"));
                    customerOrder.setOrderTime(rs.getTimestamp("order_time"));
                    customerOrder.setUser(user);
                    customerOrder.setShop(shop);
                    customerOrder.setBuyerName(rs.getString("buyer_name"));
                    customerOrder.setBuyerMobile(rs.getString("buyer_mobile"));
                    customerOrder.setTotalAmount(rs.getDouble("total_amount"));
                    customerOrder.setTotalPaid(rs.getDouble("total_paid"));
                    customerOrder.setTotalDue(rs.getDouble("total_due"));
                    return customerOrder;
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

    public boolean saveCustomerOrder(CustomerOrder customerOrder) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_barcode", customerOrder.getOrderBarcode());
        params.addValue("order_time", customerOrder.getOrderTime());
        params.addValue("user_id", customerOrder.getUser().getUserId());
        params.addValue("shop_id", customerOrder.getShop().getShopId());
        params.addValue("buyer_name", customerOrder.getBuyerName());
        params.addValue("buyer_mobile", customerOrder.getBuyerMobile());
        params.addValue("total_amount", customerOrder.getTotalAmount());
        params.addValue("total_paid", customerOrder.getTotalPaid());
        params.addValue("total_due", customerOrder.getTotalDue());

        try {
            jdbc.update("insert into pos.customer_orders (order_barcode, order_time, user_id, shop_id, buyer_name, buyer_mobile, total_amount, total_paid, total_due) "
                    + "values (:order_barcode, :order_time, :user_id, :shop_id, :buyer_name, :buyer_mobile, :total_amount, :total_paid, :total_due)", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean updateCustomerOrderAmount(CustomerOrder customerOrder) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customer_order_id", customerOrder.getCustomerOrderId());
        params.addValue("total_paid", customerOrder.getTotalPaid());
        params.addValue("total_due", customerOrder.getTotalDue());

        try {
            jdbc.update("update pos.customer_orders set total_paid=:total_paid, total_due=:total_due where customer_order_id=:customer_order_id", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public Integer getCustomerOrderId(String orderBarcode) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_barcode", orderBarcode);

        try {
            return jdbc.queryForObject("select * from pos.customer_orders where order_barcode=:order_barcode", params, new RowMapper<Integer>() {
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return (Integer) (rs.getInt("customer_order_id"));
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

    public boolean deleteCustomerOrder(CustomerOrder customerOrder) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customer_order_id", customerOrder.getCustomerOrderId());
        try {
            jdbc.update("delete from  pos.customer_orders where customer_order_id=:customer_order_id", params);
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
