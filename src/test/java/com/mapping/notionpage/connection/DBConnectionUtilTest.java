package com.mapping.notionpage.connection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DBConnectionUtilTest {
    @Autowired
    private DBConnectionUtil dbConnectionUtil;

    @Test
    @DisplayName("데이터 베이스 연결에 성공할 것이다.")
    void connectionTest() {
        // given
        Connection connection = dbConnectionUtil.getConnection();
        // when
        // then
        assertThat(connection).isNotNull();
    }
}