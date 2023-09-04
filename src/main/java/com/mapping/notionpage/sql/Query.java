package com.mapping.notionpage.sql;

import com.mapping.notionpage.dto.PageCreateRequestDTO;

public class Query {

    public static String selectById(Long pageId) {
        String sql = "SELECT * FROM page WHERE id = ";
        return sql += pageId;
    }

    public static String selectByParentPageId(Long parentPageId) {
        String sql = "SELECT * FROM page WHERE parent_page_id = ";
        return sql += parentPageId;
    }

    public static String selectByTopPageId(Long topPageId) {
        String sql = "SELECT * FROM page WHERE top_page_id = ";
        return sql += topPageId;
    }

    public static String insertForTopPageAndParentPage(PageCreateRequestDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO page (title,content,top_page_id,parent_page_id) VALUES('")
                .append(dto.getTitle())
                .append("'")
                .append(",")
                .append("'")
                .append(dto.getContent())
                .append("'")
                .append(",")
                .append(dto.getTopPageId())
                .append(",")
                .append(dto.getParentPageId())
                .append(")");

        return sb.toString();
    }

    public static String insertForTopPage(PageCreateRequestDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO page (title,content,top_page_id) VALUES('")
                .append(dto.getTitle())
                .append("'")
                .append(",")
                .append("'")
                .append(dto.getContent())
                .append("'")
                .append(",")
                .append(dto.getTopPageId())
                .append(")");

        return sb.toString();
    }

}
