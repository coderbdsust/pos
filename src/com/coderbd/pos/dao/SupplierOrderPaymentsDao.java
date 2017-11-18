/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.SupplierOrderPayment;
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
@Component("supplierOrderPaymentsDao")
public class SupplierOrderPaymentsDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public SupplierOrderPayment getSupplierOrderPayment(int supplierOrderPaymentId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_payment_id", supplierOrderPaymentId);

        try {
            return jdbc.queryForObject("select * from pos.supplier_order_payments where supplier_order_payment_id=:supplier_order_payment_id", params, new RowMapper<SupplierOrderPayment>() {
                @Override
                public SupplierOrderPayment mapRow(ResultSet rs, int rowNum) throws SQLException {

                    SupplierOrderPayment sop = new SupplierOrderPayment();
                    sop.setSupplierOrderPaymentId(rs.getInt("supplier_order_payment_id"));
                    sop.setSupplierOrderId(rs.getInt("supplier_order_id"));
                    sop.setPaymentDate(rs.getTimestamp("payment_date"));
                    sop.setAmount(rs.getDouble("amount"));
                    sop.setDescription(rs.getString("description"));
                    return sop;
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

    public List<SupplierOrderPayment> getSupplierOrderPayments(SupplierOrder so) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_id", so.getSupplierOrderId());

        try {
            return jdbc.query("select * from pos.supplier_order_payments where supplier_order_id=:supplier_order_id", params, new RowMapper<SupplierOrderPayment>() {
                @Override
                public SupplierOrderPayment mapRow(ResultSet rs, int rowNum) throws SQLException {

                    SupplierOrderPayment sop = new SupplierOrderPayment();
                    sop.setSupplierOrderPaymentId(rs.getInt("supplier_order_payment_id"));
                    sop.setSupplierOrderId(rs.getInt("supplier_order_id"));
                    sop.setPaymentDate(rs.getTimestamp("payment_date"));
                    sop.setAmount(rs.getDouble("amount"));
                    sop.setDescription(rs.getString("description"));
                    return sop;
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

    public int saveSupplierOrderPayment(SupplierOrderPayment sop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_id", sop.getSupplierOrderId());
        params.addValue("payment_date", sop.getPaymentDate());
        params.addValue("amount", sop.getAmount());
        params.addValue("description", sop.getDescription());

        try {
            jdbc.update("insert into pos.supplier_order_payments (supplier_order_id, payment_date, amount, description) values(:supplier_order_id, :payment_date, :amount, :description)", params);
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

    public boolean updateSupplierOrderPayment(SupplierOrderPayment sop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_payment_id", sop.getSupplierOrderPaymentId());
        params.addValue("supplier_order_id", sop.getSupplierOrderId());
        params.addValue("payment_date", sop.getPaymentDate());
        params.addValue("amount", sop.getAmount());
        params.addValue("description", sop.getDescription());

        try {
            jdbc.update("update pos.supplier_order_payments  set payment_date=:payment_date, "
                    + "amount=:amount, description=:description  where supplier_order_payment_id=:supplier_order_payment_id", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }

    }

    public boolean deleteSupplierOrderPayment(SupplierOrderPayment sop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_payment_id", sop.getSupplierOrderPaymentId());

        try {
            jdbc.update("delete from pos.supplier_order_payments where supplier_order_payment_id=:supplier_order_payment_id", params);
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
