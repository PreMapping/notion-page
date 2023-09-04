package com.mapping.notionpage.repository;

import com.mapping.notionpage.entity.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RepositoryTest {
    @Autowired
    private PageRepository repository;

//    @Test
//    @DisplayName("페이지 생성에 성공할 것이다.")
//    void createTest() {
//        // given
//        Page page = Page.builder()
//                .title("title")
//                .content("content")
//                .topPageId(1L)
////                .parentPageId(1L)
//                .build();
//
//        // when
//        repository.createPage(Query.INSERT, page);
//
//        // then
//    }

    @Test
    @DisplayName("topPageId로 페이지 조회에 성공할 것이다.")
    void selectWhereTopPageIdTest() {
        // given
        String sql = "SELECT * FROM page WHERE top_page_id = ";
        Long topPageID = 1L;
        sql += topPageID;

        // when
        List<Page> allByTopPageId = repository.findAllByTopPageId(sql);

        // then
        allByTopPageId
                .forEach(e -> System.out.println(e.getId() + " " + e.getTopPageId() + " " + e.getParentPageId()));
    }

}