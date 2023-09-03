package com.mapping.notionpage.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Component
public class DBConnectionUtil {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url,
                    username,
                    password);

            log.info("get connection = {}, class = {}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            log.error("connection fail");
            throw new IllegalStateException(e);
        }
    }

}
