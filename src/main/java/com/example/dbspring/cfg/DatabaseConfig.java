package com.example.dbspring.cfg;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        hikariDataSource.setUsername(env.getProperty("spring.datasource.username"));
        hikariDataSource.setPassword(env.getProperty("spring.datasource.password"));
        hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return hikariDataSource;
    }
}