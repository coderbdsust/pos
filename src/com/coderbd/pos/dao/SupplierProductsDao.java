/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.constraints.Enum;
import com.coderbd.pos.entity.SupplierOrder;
import com.coderbd.pos.entity.SupplierOrderProduct;
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
@Component("supplierProductsDao")
public class SupplierProductsDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public SupplierOrderProduct getSupplierOrderProduct(int supplierProductId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_product_id", supplierProductId);

        try {
            return jdbc.queryForObject("select * from pos.supplier_order_products where supplier_product_id=:supplier_product_id", params, new RowMapper<SupplierOrderProduct>() {
                @Override
                public SupplierOrderProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

                    SupplierOrderProduct sop = new SupplierOrderProduct();
                    sop.setSupplierProductId(rs.getInt("supplier_product_id"));
                    sop.setSupplierOrderId(rs.getInt("supplier_order_id"));
                    sop.setSupplierProductName(rs.getString("supplier_product_name"));
                    sop.setSupplierRate(rs.getDouble("supplier_rate"));
                    sop.setSupplierProductQuantity(rs.getInt("supplier_product_quantity"));
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

    public int countSupplierProductSoldQuantity(int supplierProductId) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_product_id", supplierProductId);

        try {
            return jdbc.queryForObject("select sum(order_product_quantity) as sold_quantity from pos.order_products where product_id \n"
                    + "in  (select product_id from pos.products where supplier_product_id=:supplier_product_id) group by product_id", params, Integer.class);
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return Enum.invalidIndex;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return Enum.invalidIndex;
        }
    }

    public List<SupplierOrderProduct> getSupplierOrderProducts(SupplierOrder so) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_id", so.getSupplierOrderId());

        try {
            return jdbc.query("select * from pos.supplier_products where supplier_order_id=:supplier_order_id", params, new RowMapper<SupplierOrderProduct>() {
                @Override
                public SupplierOrderProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

                    SupplierOrderProduct sop = new SupplierOrderProduct();
                    sop.setSupplierProductId(rs.getInt("supplier_product_id"));
                    sop.setSupplierOrderId(rs.getInt("supplier_order_id"));
                    sop.setSupplierProductName(rs.getString("supplier_product_name"));
                    sop.setSupplierRate(rs.getDouble("supplier_rate"));
                    sop.setSupplierProductQuantity(rs.getInt("supplier_product_quantity"));
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

    public int saveSupplierOrderProduct(SupplierOrderProduct sop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_order_id", sop.getSupplierOrderId());
        params.addValue("supplier_product_name", sop.getSupplierProductName());
        params.addValue("supplier_rate", sop.getSupplierRate());
        params.addValue("supplier_product_quantity", sop.getSupplierProductQuantity());

        try {
            jdbc.update("insert into pos.supplier_products (supplier_order_id, supplier_product_name, supplier_rate, supplier_product_quantity) values(:supplier_order_id, :supplier_product_name, :supplier_rate, :supplier_product_quantity)", params);
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

    public boolean deleteSupplierOrderProduct(SupplierOrderProduct sop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_product_id", sop.getSupplierProductId());
        try {
            jdbc.update("delete from pos.supplier_products where supplier_product_id=:supplier_product_id", params);
            MapSqlParameterSource param = new MapSqlParameterSource();
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean updateSupplierOrderProduct(SupplierOrderProduct sop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("supplier_product_id", sop.getSupplierProductId());
        params.addValue("supplier_product_name", sop.getSupplierProductName());
        params.addValue("supplier_rate", sop.getSupplierRate());
        params.addValue("supplier_product_quantity", sop.getSupplierProductQuantity());

        try {
            return jdbc.update("update pos.supplier_products set supplier_product_name=:supplier_product_name,"
                    + " supplier_rate=:supplier_rate, supplier_product_quantity=:supplier_product_quantity"
                    + " where supplier_product_id=:supplier_product_id", params) == 1;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

}
