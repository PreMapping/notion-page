package com.mapping.notionpage.service;

import com.mapping.notionpage.dto.CreatePageDTO;
import com.mapping.notionpage.dto.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class PageServiceTest {

    @Autowired
    private PageService pageService;

    @Test
    public void testCreatePageAndGetPageInfo() {
        // 새 페이지 생성
        CreatePageDTO createPageDTO = new CreatePageDTO("페이지 제목", "페이지 내용", 1L, null);
        Long pageId = pageService.createPage(createPageDTO);

        // 생성된 페이지 정보 조회

        // 조회된 페이지 정보 확인

    }

    @Test
    public void setup() {
        // 데이터 5개 넣기
        for (int i = 0; i < 5; i++) {
            CreatePageDTO createPageDTO = new CreatePageDTO(
                    "페이지 제목 " + i,
                    "페이지 내용 " + i,
                    1L,
                    null
            );
            pageService.createPage(createPageDTO);
        }
    }
}