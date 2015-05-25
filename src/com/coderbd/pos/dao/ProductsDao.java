/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.Product;
import com.coderbd.pos.entity.Shop;
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
@Component("productsDao")
public class ProductsDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Product> getProducts(Shop shop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shop.getShopId());

        try {
            return jdbc.query("select * from pos.products where shop_id=:shop_id order by product_id", params, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setShopId(rs.getInt("shop_id"));
                    product.setProductBarcode(rs.getString("product_barcode"));
                    product.setProductName(rs.getString("product_name"));
                    product.setProductBuyRate(rs.getDouble("product_buy_rate"));
                    product.setProductSellRate(rs.getDouble("product_sell_rate"));
                    product.setProductInfoUpdated(rs.getTimestamp("product_info_updated"));
                    product.setProductStock(rs.getInt("product_stock"));
                    return product;
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

    public Product getProduct(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", params);

        try {
            return jdbc.queryForObject("select * from pos.products where product_id=:product_id order by product_id", params, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setShopId(rs.getInt("shop_id"));
                    product.setProductBarcode(rs.getString("product_barcode"));
                    product.setProductName(rs.getString("product_name"));
                    product.setProductBuyRate(rs.getDouble("product_buy_rate"));
                    product.setProductSellRate(rs.getDouble("product_sell_rate"));
                    product.setProductInfoUpdated(rs.getTimestamp("product_info_updated"));
                    product.setProductStock(rs.getInt("product_stock"));
                    return product;
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

    public Product getProduct(Shop shop, String productBarcode) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_barcode", productBarcode);
        params.addValue("shop_id", shop.getShopId());
        try {
            return jdbc.queryForObject("select * from pos.products where "
                    + "product_barcode=:product_barcode and shop_id=:shop_id", params, new RowMapper<Product>() {
                        @Override
                        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Product product = new Product();
                            product.setProductId(rs.getInt("product_id"));
                            product.setShopId(rs.getInt("shop_id"));
                            product.setProductBarcode(rs.getString("product_barcode"));
                            product.setProductName(rs.getString("product_name"));
                            product.setProductBuyRate(rs.getDouble("product_buy_rate"));
                            product.setProductSellRate(rs.getDouble("product_sell_rate"));
                            product.setProductInfoUpdated(rs.getTimestamp("product_info_updated"));
                            product.setProductStock(rs.getInt("product_stock"));
                            return product;
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

    public boolean createProduct(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", product.getShopId());
        params.addValue("product_barcode", product.getProductBarcode());
        params.addValue("product_name", product.getProductName());
        params.addValue("product_buy_rate", product.getProductBuyRate());
        params.addValue("product_sell_rate", product.getProductSellRate());
        params.addValue("product_info_updated", product.getProductInfoUpdated());
        params.addValue("product_stock", product.getProductStock());

        try {
            jdbc.update("insert into pos.products (shop_id, product_barcode, product_name, product_buy_rate, product_sell_rate,"
                    + "product_info_updated, product_stock) values(:shop_id, :product_barcode, "
                    + ":product_name, :product_buy_rate,:product_sell_rate, NOW(), :product_stock)", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }

    }

    public boolean updateProduct(Product product) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", product.getProductId());
        params.addValue("shop_id", product.getShopId());
        params.addValue("product_barcode", product.getProductBarcode());
        params.addValue("product_name", product.getProductName());
        params.addValue("product_buy_rate", product.getProductBuyRate());
        params.addValue("product_sell_rate", product.getProductSellRate());
        params.addValue("product_stock", product.getProductStock());

        try {
            jdbc.update("update pos.products set product_stock=:product_stock , product_name=:product_name ,"
                    + " product_buy_rate=:product_buy_rate, product_sell_rate=:product_sell_rate,"
                    + " product_info_updated=NOW() "
                    + "where (product_id=:product_id and shop_id=:shop_id) or (product_barcode=:product_barcode and shop_id=:shop_id)", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean updateProductStock(int productId, int soldStock) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("sold_stock", soldStock);

        try {
            jdbc.update("update pos.products set product_stock=product_stock-:sold_stock "
                    + "where product_id=:product_id", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean deleteAllShopProduct(Shop shop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shop.getShopId());

        try {
            jdbc.update("delete from pos.products where shop_id=:shop_id", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean deleteProductById(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);

        try {
            jdbc.update("delete from pos.products where product_id=:product_id", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean deleteProductByBarcode(String productBarcode, int shopId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_barcode", productBarcode);
        params.addValue("shop_id", shopId);

        try {
            jdbc.update("delete from pos.products where product_barcode=:product_barcode and shop_id=:shop_id", params);
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
