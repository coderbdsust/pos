/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.ExpenseCategory;
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
@Component("expenseCategoriesDao")
public class ExpenseCategoriesDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ExpenseCategory> getExpenseCategories(int shopId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shopId);

        try {
            return jdbc.query("select * from pos.expense_categories where shop_id=:shop_id", params, new RowMapper<ExpenseCategory>() {
                @Override
                public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ExpenseCategory expenseCategory = new ExpenseCategory();
                    expenseCategory.setExpCategoryId(rs.getInt("exp_category_id"));
                    expenseCategory.setShopId(rs.getInt("shop_id"));
                    expenseCategory.setExpName(rs.getString("exp_name"));
                    return expenseCategory;
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

    public ExpenseCategory getExpenseCategory(int shopId, String expName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", shopId);
        params.addValue("exp_name", expName);

        try {
            return jdbc.queryForObject("select * from pos.expense_categories where shop_id=:shop_id and exp_name=:exp_name", params, new RowMapper<ExpenseCategory>() {
                @Override
                public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ExpenseCategory expenseCategory = new ExpenseCategory();
                    expenseCategory.setExpCategoryId(rs.getInt("exp_category_id"));
                    expenseCategory.setShopId(rs.getInt("shop_id"));
                    expenseCategory.setExpName(rs.getString("exp_name"));
                    return expenseCategory;
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

    public boolean createExpenseCategory(ExpenseCategory expCategory) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("shop_id", expCategory.getShopId());
        params.addValue("exp_name", expCategory.getExpName());

        try {
            jdbc.update("insert into pos.expense_categories (shop_id, exp_name) values(:shop_id, :exp_name)", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean deleteExpenseCategory(ExpenseCategory expCategory) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("exp_category_id", expCategory.getExpCategoryId());

        try {
            jdbc.update("delete from pos.expense_categories where exp_category_id=:exp_category_id)", params);
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
