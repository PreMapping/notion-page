package com.mapping.notionpage.repository;

import com.mapping.notionpage.connection.DBConnectionUtil;
import com.mapping.notionpage.dto.AbstractPageResponseDto;
import com.mapping.notionpage.dto.PageRequestDto;
import com.mapping.notionpage.dto.PageSaveResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Slf4j
@Repository
public class PageRepository {

    public PageSaveResponseDto findById(long pageId) throws SQLException {

        String FIND_SQL = "select * from page where id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PageSaveResponseDto page = new PageSaveResponseDto();

        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_SQL);
            pstmt.setLong(1, pageId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                page.setPageId(rs.getLong("id"));
                page.setTitle(rs.getString("title"));
                page.setContent(rs.getString("content"));
                page.setTopPageId(rs.getLong("top_page_id"));
                page.setParentPageId(rs.getLong("parent_page_id"));
            } else {
                throw new NoSuchElementException("page not found, pageId = " + pageId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
        } finally {
            close(con, pstmt, rs);
        }

        return page;
    }

    public List<AbstractPageResponseDto> findManyByTopNode(long topPageId, long findNodeId) {
        String FIND_MANY_SQL = "select id, title, parent_page_id from page where top_page_id = ? and id != ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AbstractPageResponseDto> responses = new ArrayList<>();

        try {
            con = getConnection();
            pstmt = con.prepareStatement(FIND_MANY_SQL);
            pstmt.setLong(1, topPageId);
            pstmt.setLong(2, findNodeId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AbstractPageResponseDto response = new AbstractPageResponseDto();
                response.setPageId(rs.getLong("id"));
                response.setTitle(rs.getString("title"));
                long parentPageId = rs.getLong("parent_page_id");
                response.setParentPageId(rs.getLong("parent_page_id"));
                responses.add(response);
            }
        } catch (SQLException e) {
            log.error("db error", e);
        } finally {
            close(con, pstmt, rs);
        }

        return responses;
    }

    public PageSaveResponseDto save(PageRequestDto request) {

        String INSERT_SQL = "insert into page(title, content, top_page_id, parent_page_id) values(?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        PageSaveResponseDto response = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getContent());
            pstmt.setLong(3, request.getTopPageId());
            if (!Objects.isNull(request.getParentPageId())) {
                pstmt.setLong(4, request.getParentPageId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            Integer generatedId = -1;
            if (keys.next()) {
                generatedId = keys.getInt(1); // id returned after insert execution
            }

            response = new PageSaveResponseDto(generatedId, request);
        } catch (SQLException e) {
            log.error("db error", e);
        } finally {
            close(con, pstmt, null);
        }
        return response;
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
}
