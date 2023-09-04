package com.mapping.notionpage.dao;

import com.mapping.notionpage.connection.DBConnectionUtil;
import com.mapping.notionpage.dto.CreatePageDTO;
import com.mapping.notionpage.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PageDAO {

    private final DBConnectionUtil dbConnectionUtil;


    public long createPage(CreatePageDTO createPageDTO) {
        String insertPageSql = "INSERT INTO page (title, content, top_node, parent_page_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPageSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, createPageDTO.getTitle());
            preparedStatement.setString(2, createPageDTO.getContent());
            preparedStatement.setObject(3, createPageDTO.getTopNode()); // topNode 가 null 일 수 있으므로 setObject 를 사용
            preparedStatement.setObject(4, createPageDTO.getParentPageId(), Types.BIGINT); // parentPageId가 null 일 수 있으므로 setObject 를 사용

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("페이지 생성 실패");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    log.info("pageId={}", generatedKeys.getLong(1));
                    return generatedKeys.getLong(1); // ID 반환
                } else {
                    throw new RuntimeException("페이지 생성 실패, 생성된 ID를 가져올 수 없음");
                }
            }
        } catch (Exception e) {
            log.error("페이지 생성 실패", e);
            throw new RuntimeException(e);
        }
    }

    public PageDTO getPageInfo(Long pageId) {
        String selectPageSql = "SELECT title, content, top_node, parent_page_id FROM page WHERE id = ?";

        try (Connection connection = dbConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPageSql)) {

            preparedStatement.setLong(1, pageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    Long topNode = (Long) resultSet.getObject("top_node"); // topNode가 null일 경우를 처리
                    Long parentPageId = resultSet.getLong("parent_page_id");

                    return new PageDTO(pageId, title, content, topNode, parentPageId);
                } else {
                    throw new RuntimeException("페이지를 찾을 수 없음");
                }
            }
        } catch (Exception e) {
            log.error("페이지 조회 실패", e);
            throw new RuntimeException(e);
        }
    }

    public List<Long> getSubPages(Long pageId) {
        String selectPageIdsSql = "SELECT id FROM page WHERE parent_page_id = ?";

        try (Connection connection = dbConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPageIdsSql)) {

            preparedStatement.setLong(1, pageId);

            List<Long> pageIdList = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pageIdList.add(resultSet.getLong("id"));
                }
            }

            return pageIdList;
        } catch (Exception e) {
            log.error("페이지 ID 목록 조회 실패", e);
            throw new RuntimeException(e);
        }
    }
}

