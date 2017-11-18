/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.factory;

import com.coderbd.pos.constraints.Const;
import com.coderbd.pos.properties.ApplicationProperties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Biswajit Debnath
 */
@Configuration
public class DataSourceFactory {
    
    @Bean(name = "dataSource")
    public DataSource createBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Const.DB_DRIVER_NAME);
        dataSource.setUrl(Const.DB_URL);
        dataSource.setUsername(Const.DB_USERNAME);
        dataSource.setPassword(Const.DB_PASSWORD);
        return dataSource;
    }

}
