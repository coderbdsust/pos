/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.Expenditure;
import com.coderbd.pos.entity.ExpenseCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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
@Component("expendituresDao")
public class ExpendituresDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public Expenditure getExpenditures(int expId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("exp_id", expId);

        try {
            return jdbc.queryForObject("select * from pos.expenditures where exp_id=:exp_id", params, new RowMapper<Expenditure>() {
                @Override
                public Expenditure mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Expenditure expenditure = new Expenditure();
                    expenditure.setExpId(rs.getInt("exp_id"));
                    expenditure.setExpCategoryId(rs.getInt("exp_category_id"));
                    expenditure.setExpAmount(rs.getDouble("exp_amount"));
                    expenditure.setExpTime(rs.getTimestamp("exp_time"));
                    expenditure.setExpDesc(rs.getString("exp_desc"));
                    return expenditure;
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

    public List<Expenditure> getExpenditures(ExpenseCategory expCategory) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("exp_category_id", expCategory.getExpCategoryId());

        try {
            return jdbc.query("select * from pos.expenditures "
                    + "where exp_category_id=:exp_category_id", params, new RowMapper<Expenditure>() {
                        @Override
                        public Expenditure mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Expenditure expenditure = new Expenditure();
                            expenditure.setExpId(rs.getInt("exp_id"));
                            expenditure.setExpCategoryId(rs.getInt("exp_category_id"));
                            expenditure.setExpAmount(rs.getDouble("exp_amount"));
                            expenditure.setExpTime(rs.getTimestamp("exp_time"));
                            expenditure.setExpDesc(rs.getString("exp_desc"));
                            return expenditure;
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

    public boolean createExpenditure(Expenditure expenditure) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("exp_category_id", expenditure.getExpCategoryId());
        params.addValue("exp_amount", expenditure.getExpAmount());
        params.addValue("exp_time", expenditure.getExpTime());
        params.addValue("exp_desc", expenditure.getExpDesc());

        try {
            jdbc.update("insert into pos.expenditures (exp_category_id, exp_amount, exp_time, exp_desc) "
                    + "values(:exp_category_id, :exp_amount, :exp_time, :exp_desc)", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean updateExpenditure(Expenditure expenditure) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("exp_id", expenditure.getExpId());
        params.addValue("exp_category_id", expenditure.getExpCategoryId());
        params.addValue("exp_amount", expenditure.getExpAmount());
        params.addValue("exp_time", expenditure.getExpTime());
        params.addValue("exp_desc", expenditure.getExpDesc());

        try {
            jdbc.update("update pos.expenditure set  exp_amount=:exp_amount, "
                    + "exp_time=:exp_time, exp_desc=:exp_desc where exp_id=:exp_id", params);
            return true;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

    public boolean deleteExpenditure(Expenditure expenditure) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("exp_id", expenditure.getExpId());

        try {
            jdbc.update("delete from pos.expenditures where exp_id=:exp_id", params);
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
