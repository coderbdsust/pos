/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.SupplierReturnProduct;
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

@Component("supplierReturnProductsDao")
public class SupplierReturnProductsDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public int saveSupplierReturnProduct(SupplierReturnProduct srp) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_product_id", srp.getSupplierProductId());
        params.addValue("return_time", srp.getReturnTime());
        params.addValue("quantity", srp.getQuantity());

        try {
            jdbc.update("insert into pos.supplier_return_products (supplier_product_id, return_time, quantity) values"
                    + "(:supplier_product_id, :return_time, :quantity)", params);

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

    public List<SupplierReturnProduct> getSupplierReturnProducts(int supplierProductId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_product_id", supplierProductId);
        try {
            return jdbc.query("select * from pos.supplier_return_products where supplier_product_id=:supplier_product_id", params, new RowMapper<SupplierReturnProduct>() {
                @Override
                public SupplierReturnProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

                    SupplierReturnProduct srp = new SupplierReturnProduct();
                    srp.setSupplierReturnProductId(rs.getInt("supplier_return_product_id"));
                    srp.setSupplierProductId(rs.getInt("supplier_product_id"));
                    srp.setReturnTime(rs.getTimestamp("return_time"));
                    srp.setQuantity(rs.getInt("quantity"));
                    return srp;
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

    public int countSupplierReturnProduct(int supplierProductId) {

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("supplier_product_id", supplierProductId);

        String query = "select sum(quantity) as total_return from "
                + "supplier_product_returns where supplier_product_id=:supplier_product_id group by supplier_product_id";

        try {
            return jdbc.queryForObject(query, param, Integer.class);
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return Enum.invalidIndex;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return Enum.invalidIndex;
        }

    }
}
