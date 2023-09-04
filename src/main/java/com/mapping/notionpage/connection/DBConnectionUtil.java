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

    private static final String URL = "jdbc:mysql://localhost:3306/notionpage";
    private static final String USERNAME = "board";
    private static final String PASSWORD = "board1111";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL,
                    USERNAME,
                    PASSWORD);

            log.info("get connection = {}, class = {}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            log.error("connection fail");
            throw new IllegalStateException(e);
        }
    }

}
