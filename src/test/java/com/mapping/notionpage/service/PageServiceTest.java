package com.mapping.notionpage.service;

import com.mapping.notionpage.dto.PageCreateRequestDTO;
import com.mapping.notionpage.dto.PageResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PageServiceTest {

    @Autowired
    PageService pageService;

    @Test
    @DisplayName("제목,내용,TopPageId 로 페이지 생성에 성공한다.")
    void createForTopPageTest() {
        // given
        PageCreateRequestDTO pageCreateRequestDTO = new PageCreateRequestDTO("title", "content", 991L, null);
        // when
        Boolean result = pageService.createForTopPage(pageCreateRequestDTO);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("제목,내용,topPageId,parentPageId 로 페이지 생성에 성공한다.")
    void createForTopPageAndParentPageTest() {
        // given
        PageCreateRequestDTO pageCreateRequestDTO = new PageCreateRequestDTO("title", "content", 991L, 981L);
        // when
        Boolean result = pageService.createForTopPageAndParentPage(pageCreateRequestDTO);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("페이지 아이디로 조회에 성공할 것이다.")
    void findByIdTest() {
        // given
        Long id = 8L;

        // when
        PageResponseDTO byId = pageService.findById(id);
        System.out.println(byId);

        // then
        assertThat(byId.getId()).isEqualTo(id);
        assertThat(byId.getTitle()).isEqualTo("제목8");
        assertThat(byId.getContent()).isEqualTo("내용8");
        assertThat(byId.getBreadcrumbs()).isEqualTo(List.of(1L, 3L, 8L));
        assertThat(byId.getSubPageList()).isEqualTo(List.of(10L, 11L));
    }

}