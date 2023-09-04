package com.mapping.notionpage.repository;

import com.mapping.notionpage.connection.DBConnectionUtil;
import com.mapping.notionpage.entity.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PageRepository {
    private final DBConnectionUtil dbConnectionUtil;

    public boolean createPage(String sql) {
        try (Connection connection = dbConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            int i = preparedStatement.executeUpdate();

            if (i == 1) {
                connection.commit();
                return true;
            } else connection.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public Page findById(String sql) {
        Page page = null;

        try (Connection connection = dbConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Long parentPageId = resultSet.getLong("parent_page_id");
                Long topPageId = resultSet.getLong("top_page_id");

                return Page.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .topPageId(topPageId)
                        .parentPageId(parentPageId)
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return page;
        }
        return page;
    }

    public List<Page> findAllByTopPageId(String sql) {
        List<Page> list = new ArrayList<>();

        try (Connection connection = dbConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long topPageId = resultSet.getLong("top_page_id");
                Long parentPageId = resultSet.getLong("parent_page_id");

                list.add(Page.builder()
                        .id(id)
                        .topPageId(topPageId)
                        .parentPageId(parentPageId)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }

}