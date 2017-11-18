/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.Supplier;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.User;
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
@Component("suppliersDao")
public class SuppliersDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Supplier> getSuppliers(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", user.getUserId());

        try {
            return jdbc.query("select * from pos.suppliers where user_id=:user_id order by supplier_id", params, new RowMapper<Supplier>() {
                @Override
                public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Supplier supplier = new Supplier();
                    supplier.setSupplierId(rs.getInt("supplier_id"));
                    supplier.setSupplierName(rs.getString("supplier_name"));
                    supplier.setSupplierAddress(rs.getString("supplier_address"));
                    supplier.setSupplierMobile(rs.getString("supplier_mobile"));
                    supplier.setUser(user);
                    return supplier;
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

    public Supplier getSupplier(int supplierId, User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_id", params);
        params.addValue("user_id", user.getUserId());

        try {
            return jdbc.queryForObject("select * from pos.suppliers where supplier_id=:supplier_id and user_id=:user_id order by supplier_id", params, new RowMapper<Supplier>() {
                @Override
                public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Supplier supplier = new Supplier();
                    supplier.setSupplierId(rs.getInt("supplier_id"));
                    supplier.setSupplierName(rs.getString("supplier_name"));
                    supplier.setSupplierAddress(rs.getString("supplier_address"));
                    supplier.setSupplierMobile(rs.getString("supplier_mobile"));
                    supplier.setUser(user);
                    return supplier;
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

    public int createSupplier(Supplier supplier) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("supplier_name", supplier.getSupplierName());
        params.addValue("supplier_address", supplier.getSupplierAddress());
        params.addValue("supplier_mobile", supplier.getSupplierMobile());
        params.addValue("user_id", supplier.getUser().getUserId());

        try {
            jdbc.update("insert into pos.suppliers (supplier_name, supplier_address, supplier_mobile, user_id) values(:supplier_name, :supplier_address, :supplier_mobile, :user_id)", params);

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

    public boolean deleteSupplierOrder(SupplierOrder so) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_id", so.getSupplierOrderId());

        try {
            jdbc.update("delete from pos.supplier_orders where supplier_order_id=:supplier_order_id", params);
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
