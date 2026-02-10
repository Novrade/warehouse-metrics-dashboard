package com.example.warehouse_metrics_dashboard.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfiguration {

    // Configuration for Database1 DataSource
    @Bean(name="DB1")
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSource db1() {
        // Build and return the DB1 DataSource using properties prefixed with 'db1.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for Database2 DataSource
    @Bean(name="DB2")
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSource db2() {
        // Build and return the DB2 DataSource using properties prefixed with 'db2.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for Database3 DataSource
    @Bean(name="DB3")
    @ConfigurationProperties(prefix = "db3.datasource")
    public DataSource db3() {
        // Build and return the DB3 DataSource using properties prefixed with 'db3.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for Database4 DataSource
    @Bean(name="DB4")
    @ConfigurationProperties(prefix = "db4.datasource")
    public DataSource db4() {
        // Build and return the DB4 DataSource using properties prefixed with 'db4.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for JdbcTemplate using Database1 DataSource
    @Bean(name="JDBCTemplateDB1")
    public JdbcTemplate JDBCTemplateDB1(@Qualifier("DB1") DataSource dataSource) {
        // Create and return a JdbcTemplate using the DB1 DataSource
        return new JdbcTemplate(dataSource);
    }

    // Configuration for JdbcTemplate using Database2 DataSource
    @Bean(name="JDBCTemplateDB2")
    public JdbcTemplate JDBCTemplateDB2(@Qualifier("DB2") DataSource dataSource) {
        // Create and return a JdbcTemplate using the DB2 DataSource
        return new JdbcTemplate(dataSource);
    }

    // Configuration for JdbcTemplate using Database3 DataSource
    @Bean(name="JDBCTemplateDB3")
    public JdbcTemplate JDBCTemplateDB3(@Qualifier("DB3") DataSource dataSource) {
        // Create and return a JdbcTemplate using the DB3 DataSource
        return new JdbcTemplate(dataSource);
    }

    // Configuration for JdbcTemplate using DB4 DataSource
    @Bean(name="JDBCTemplateDB4")
    public JdbcTemplate JDBCTemplateDB4(@Qualifier("DB4") DataSource dataSource) {
        // Create and return a JdbcTemplate using the DB4 DataSource
        return new JdbcTemplate(dataSource);
    }

}
