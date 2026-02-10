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

    // Configuration for WMS DataSource
    @Bean(name="WMSDataSource")
    @ConfigurationProperties(prefix = "wms.datasource")
    public DataSource wms() {
        // Build and return the WMS DataSource using properties prefixed with 'wms.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for WCS DataSource
    @Bean(name="WCSDataSource")
    @ConfigurationProperties(prefix = "wcs.datasource")
    public DataSource wcs() {
        // Build and return the WCS DataSource using properties prefixed with 'wcs.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for SRC DataSource
    @Bean(name="SRCDataSource")
    @ConfigurationProperties(prefix = "src.datasource")
    public DataSource src() {
        // Build and return the SRC DataSource using properties prefixed with 'src.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for WCSH DataSource
    @Bean(name="WCSHDataSource")
    @ConfigurationProperties(prefix = "wcsh.datasource")
    public DataSource wcsh() {
        // Build and return the WCSH DataSource using properties prefixed with 'wcsh.datasource'
        return DataSourceBuilder.create().build();
    }

    // Configuration for JdbcTemplate using WMS DataSource
    @Bean(name="JDBCTemplateWMS")
    public JdbcTemplate JDBCTemplateWMS(@Qualifier("WMSDataSource") DataSource dataSource) {
        // Create and return a JdbcTemplate using the WMS DataSource
        return new JdbcTemplate(dataSource);
    }

    // Configuration for JdbcTemplate using WCS DataSource
    @Bean(name="JDBCTemplateWCS")
    public JdbcTemplate JDBCTemplateWCS(@Qualifier("WCSDataSource") DataSource dataSource) {
        // Create and return a JdbcTemplate using the WCS DataSource
        return new JdbcTemplate(dataSource);
    }

    // Configuration for JdbcTemplate using SRC DataSource
    @Bean(name="JDBCTemplateSRC")
    public JdbcTemplate JDBCTemplateSRC(@Qualifier("SRCDataSource") DataSource dataSource) {
        // Create and return a JdbcTemplate using the SRC DataSource
        return new JdbcTemplate(dataSource);
    }

    // Configuration for JdbcTemplate using WCSH DataSource
    @Bean(name="JDBCTemplateWCSH")
    public JdbcTemplate JDBCTemplateWCSH(@Qualifier("WCSHDataSource") DataSource dataSource) {
        // Create and return a JdbcTemplate using the WCSH DataSource
        return new JdbcTemplate(dataSource);
    }

}
