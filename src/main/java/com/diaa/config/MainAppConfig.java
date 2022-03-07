package com.diaa.config;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.diaa.config")
@PropertySource("classpath:persistence-mysql.properties")
public class MainAppConfig {

    // setup variable to hold the properties
    @Autowired
    private Environment environment;

    // setup logger for diagnostics
    private Logger logger = Logger.getLogger(getClass().getName());


    // Define a bean for ViewResolver
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver internalResourceViewResolver =
                new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;
    }

    // define a Bean for our security data source
    @Bean
    public DataSource securityDataSource(){

        // Create connection pool
        ComboPooledDataSource comboPooledDataSource
                = new ComboPooledDataSource();

        // set the jdbc driver class
        try {
            comboPooledDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        //log the connection props
        logger.info(">>> jdbc.url= " + environment.getProperty("jdbc.url"));
        logger.info(">>> jdbc.user= " + environment.getProperty("jdbc.user"));

        // set database connection props
        comboPooledDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        comboPooledDataSource.setUser(environment.getProperty("jdbc.user"));
        comboPooledDataSource.setPassword(environment.getProperty("jdbc.password"));

        // set connection pool props
        comboPooledDataSource.setInitialPoolSize(
                getIntProperty("connection.pool.initialPoolSize")
        );
        comboPooledDataSource.setMinPoolSize(
                getIntProperty("connection.pool.minPoolSize")
        );
        comboPooledDataSource.setMaxPoolSize(
                getIntProperty("connection.pool.maxPoolSize")
        );
        comboPooledDataSource.setMaxIdleTime(
                getIntProperty("connection.pool.maxIdleTime")
        );
        return comboPooledDataSource;
    }

    // read environment property and convert it to int
    public int getIntProperty(String propName){
        String propValue = environment.getProperty(propName);

        // Convert to int
        int intPropValue = Integer.parseInt(propValue);

        return intPropValue;
    }




}
