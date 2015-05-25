/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.dao;

import com.coderbd.pos.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Biswajit Debnath
 */
@Component("usersDao")
public class UsersDao {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public User getUser(String usernameOrMobileNumber, String password) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("usernameOrMobileNumber", usernameOrMobileNumber);
        params.addValue("password", password);

        try {
            return jdbc.queryForObject("select * from pos.users where "
                    + "(username=:usernameOrMobileNumber or mobile=:usernameOrMobileNumber) and password=:password", params, new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet rs, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(rs.getInt("user_id"));
                            user.setUsername(rs.getString("username"));
                            user.setPassword(rs.getString("password"));
                            user.setName(rs.getString("name"));
                            user.setMobile(rs.getString("mobile"));
                            user.setAuthority(rs.getString("authority"));
                            user.setActive(rs.getBoolean("active"));
                            return user;
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

    public User getUser(String usernameOrMobileNumber) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("usernameOrMobileNumber", usernameOrMobileNumber);
        try {
            return jdbc.queryForObject("select * from pos.users where "
                    + "(username=:usernameOrMobileNumber or mobile=:usernameOrMobileNumber)", params, new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet rs, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(rs.getInt("user_id"));
                            user.setUsername(rs.getString("username"));
                            user.setPassword(rs.getString("password"));
                            user.setName(rs.getString("name"));
                            user.setMobile(rs.getString("mobile"));
                            user.setAuthority(rs.getString("authority"));
                            user.setActive(rs.getBoolean("active"));
                            return user;
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

    public List<User> getUsers() {
        try {
            return jdbc.query("select * from pos.users", new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setMobile(rs.getString("mobile"));
                    user.setAuthority(rs.getString("authority"));
                    user.setActive(rs.getBoolean("active"));
                    return user;
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

    public List<User> getUsers(String authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authority", authority);
        try {
            return jdbc.query("select * from users where authority=:authority "
                    + "and user_id not in(select user_id from pos.shop_owners);", params, new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                            User user = new User();
                            user.setUserId(rs.getInt("user_id"));
                            user.setUsername(rs.getString("username"));
                            user.setPassword(rs.getString("password"));
                            user.setName(rs.getString("name"));
                            user.setMobile(rs.getString("mobile"));
                            user.setAuthority(rs.getString("authority"));
                            user.setActive(rs.getBoolean("active"));
                            return user;
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

    public boolean createUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", user.getUsername());
        params.addValue("password", user.getPassword());
        params.addValue("name", user.getName());
        params.addValue("mobile", user.getMobile());
        params.addValue("authority", user.getAuthority());
        params.addValue("active", user.isActive());

        try {
            return jdbc.update("insert into pos.users (username, password, name, mobile, authority, active)"
                    + " values(:username, :password, :name, :mobile, :authority, :active)", params) == 1;
        } catch (CannotGetJdbcConnectionException conExp) {
            System.out.println(conExp.getMessage());
            return false;
        } catch (DataAccessException dae) {
            System.out.println(dae.getMessage());
            return false;
        }
    }

}
