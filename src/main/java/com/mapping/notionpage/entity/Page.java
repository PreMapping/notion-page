package com.mapping.notionpage.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@NoArgsConstructor
public class Page {
    private Long id;
    private String title;
    private String content;
    private Long topPageId;
    private Long parentPageId;

    @Builder
    public Page(Long id, String title, String content, Long topPageId, Long parentPageId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.topPageId = topPageId;
        this.parentPageId = parentPageId;
    }

    public Page(ResultSet resultSet) {
        try {
            this.id = resultSet.getLong("id");
            this.title = resultSet.getString("title");
            this.content = resultSet.getString("title");
            this.topPageId = resultSet.getLong("topPageId");
            this.parentPageId = resultSet.getLong("parentPageId");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
