/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.User;
import com.coderbd.pos.entity.pojo.ShopDailyProfit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Biswajit Debnath
 */
@Component("shopsDao")
public class ShopsDao {

    @Autowired
    private ShopOwnersDao shopOwnersDao;

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);

    }

    public Shop getShop(int shopId) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shopId);

        try {
            return jdbc.queryForObject("select * from pos.shops where shop_id=:shop_id", params, new RowMapper<Shop>() {
                @Override
                public Shop mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Shop shop = new Shop();
                    shop.setShopId(rs.getInt("shop_id"));
                    shop.setShopName(rs.getString("shop_name"));
                    shop.setShopTin(rs.getString("shop_tin"));
                    shop.setShopAddress(rs.getString("shop_address"));
                    shop.setShopMobile(rs.getString("shop_mobile"));
                    return shop;
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

    public Shop getShop(Shop shop) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_name", shop.getShopName());
        params.addValue("shop_tin", shop.getShopTin());
        params.addValue("shop_address", shop.getShopAddress());
        params.addValue("shop_mobile", shop.getShopMobile());

        try {
            return jdbc.queryForObject("select * from pos.shops where "
                    + "shop_name=:shop_name and shop_tin=:shop_tin "
                    + "and shop_address=:shop_address and shop_mobile=:shop_mobile", params, new RowMapper<Shop>() {
                        @Override
                        public Shop mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Shop shop = new Shop();
                            shop.setShopId(rs.getInt("shop_id"));
                            shop.setShopName(rs.getString("shop_name"));
                            shop.setShopTin(rs.getString("shop_tin"));
                            shop.setShopAddress(rs.getString("shop_address"));
                            shop.setShopMobile(rs.getString("shop_mobile"));
                            return shop;
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

    public List<Shop> getShops(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", user.getUserId());

        try {
            return jdbc.query("select * from pos.shops natural join pos.shop_owners "
                    + "join  pos.users using(user_id) where user_id=:user_id", params, new RowMapper<Shop>() {
                        @Override
                        public Shop mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Shop shop = new Shop();
                            shop.setShopId(rs.getInt("shop_id"));
                            shop.setShopName(rs.getString("shop_name"));
                            shop.setShopTin(rs.getString("shop_tin"));
                            shop.setShopAddress(rs.getString("shop_address"));
                            shop.setShopMobile(rs.getString("shop_mobile"));
                            return shop;
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

    public List<ShopDailyProfit> getShopDailyProfits(Shop shop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shop.getShopId());

        try {
            return jdbc.query("select  shop_id, date, \n"
                    + "sum(total_amount) as daily_sell, \n"
                    + "sum(total_paid) as daily_paid, \n"
                    + "sum(total_due) as daily_due, \n"
                    + "sum(order_profit) as daily_profit from (select shop_id,\n"
                    + "customer_order_id,\n"
                    + "order_barcode, \n"
                    + "date(order_time) as date, \n"
                    + "total_amount, \n"
                    + "total_paid, \n"
                    + "total_due, \n"
                    + "(sum(profit)-total_due) as order_profit from \n"
                    + "\n"
                    + "(select \n"
                    + "customer_order_id, product_id,\n"
                    + "(order_product_sell_rate\n"
                    + "-order_product_discount*0.01\n"
                    + "+order_product_vat*0.01 \n"
                    + "- product_buy_rate) * order_product_quantity\n"
                    + "as profit from products natural join order_products order by customer_order_id ) \n"
                    + "as profit_table join customer_orders  using(customer_order_id) \n"
                    + "group by customer_order_id) as shop_profit where shop_id=:shop_id group by date", params, new RowMapper<ShopDailyProfit>() {
                        @Override
                        public ShopDailyProfit mapRow(ResultSet rs, int rowNum) throws SQLException {
                            ShopDailyProfit sdp = new ShopDailyProfit();
                            sdp.setDate(rs.getDate("date"));
                            sdp.setShopId(rs.getInt("shop_id"));
                            sdp.setDailySell(rs.getDouble("daily_sell"));
                            sdp.setDailyPaid(rs.getDouble("daily_paid"));
                            sdp.setDailyDue(rs.getDouble("daily_due"));
                            sdp.setDailyProfit(rs.getDouble("daily_profit"));
                            return sdp;
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

    @Transactional
    public boolean createShop(User user, Shop shop) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_name", shop.getShopName());
        params.addValue("shop_tin", shop.getShopTin());
        params.addValue("shop_address", shop.getShopAddress());
        params.addValue("shop_mobile", shop.getShopMobile());

        try {
            jdbc.update("insert into pos.shops (shop_name, shop_tin, shop_address, shop_mobile)"
                    + " values(:shop_name, :shop_tin, :shop_address, :shop_mobile)", params);
            shop = getShop(shop);
            if (shop == null) {
                return false;
            }
            shopOwnersDao.createShopOwner(user.getUserId(), shop.getShopId());
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
