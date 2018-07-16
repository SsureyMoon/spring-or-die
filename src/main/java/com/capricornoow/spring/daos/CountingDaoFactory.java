package com.capricornoow.spring.daos;

import com.capricornoow.spring.contexts.JdbcContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        DataSource dataSourceObject = dataSource();
        userDao.setDataSource(dataSourceObject);
        userDao.setJdbcContext(jdbcContext(dataSourceObject));
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        Map<String, String> env = System.getenv();
        String dbUrl = env.get("DB_ACCESS_URL");
        String dbName = env.get("DB_NAME");
        String dbUserName = env.get("DB_USERNAME");
        String dbUserPassword = env.get("DB_PASSWORD");

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl(dbUrl + "/" + dbName);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbUserPassword);

        return dataSource;
    }

    @Bean
    public JdbcContext jdbcContext(DataSource dataSource) {
        JdbcContext jdbcContextObject = new JdbcContext();
        jdbcContextObject.setDataSource(dataSource);

        return jdbcContextObject;
    }
}
