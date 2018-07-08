package com.capricornoow.spring.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class DConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbUrl = env.get("DB_ACCESS_URL");
        String dbUserName = env.get("DB_USERNAME");
        String dbUserPassword = env.get("DB_PASSWORD");

        Class.forName("org.postgresql.Driver");

        Properties props = new Properties();
        props.setProperty("user", dbUserName);
        props.setProperty("password", dbUserPassword);
        return DriverManager.getConnection(dbUrl, props);
    }
}
