/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.Supplier;
import com.coderbd.pos.entity.SupplierOrder;
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

@Component("supplierOrdersDao")
public class SupplierOrdersDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<SupplierOrder> getSupplierOrders(Supplier sup) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_id", sup.getSupplierId());

        try {
            return jdbc.query("select * from pos.supplier_orders where supplier_id=:supplier_id order by supplier_order_id", params, new RowMapper<SupplierOrder>() {
                @Override
                public SupplierOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SupplierOrder so = new SupplierOrder();
                    so.setSupplierOrderId(rs.getInt("supplier_order_id"));
                    so.setSupplier(sup);
                    so.setTotalBill(rs.getDouble("total_bill"));
                    so.setTotalPaid(rs.getDouble("total_paid"));
                    so.setOrderTime(rs.getTimestamp("order_time"));
                    return so;
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

    public int createSupplierOrder(SupplierOrder so) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_id", so.getSupplier().getSupplierId());
        params.addValue("total_bill", so.getTotalBill());
        params.addValue("total_paid", so.getTotalPaid());
        params.addValue("order_time", so.getOrderTime());

        try {

            jdbc.update("insert into pos.supplier_orders (supplier_id, total_bill, total_paid, order_time) "
                    + "values(:supplier_id, :total_bill, :total_paid, :order_time)", params);

            MapSqlParameterSource param = new MapSqlParameterSource();
            return jdbc.queryForObject("select last_insert_id()", param, Integer.class);

        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return Enum.invalidIndex;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return Enum.invalidIndex;
        }
    }

    public boolean updateSupplierOrder(SupplierOrder so) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_id", so.getSupplierOrderId());
        params.addValue("supplier_id", so.getSupplier().getSupplierId());
        params.addValue("total_bill", so.getTotalBill());
        params.addValue("total_paid", so.getTotalPaid());
        params.addValue("order_time", so.getOrderTime());
        try {
            jdbc.update("update pos.supplier_orders set total_bill=:total_bill, total_paid=:total_paid, order_time=:order_time where supplier_order_id=:supplier_order_id", params);
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
