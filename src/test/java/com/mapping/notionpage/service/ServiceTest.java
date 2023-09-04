package com.mapping.notionpage.service;

import com.mapping.notionpage.dto.PageFindResponseDto;
import com.mapping.notionpage.dto.PageRequestDto;
import com.mapping.notionpage.dto.PageSaveResponseDto;
import com.mapping.notionpage.repository.PageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private PageService pageService;
    @Autowired
    private PageRepository pageRepository;

    @DisplayName("페이지 저장에 성공할 것이다.")
    @Test
    void savePageTest() throws Exception {

        PageRequestDto request = PageRequestDto.builder()
                .title("제목")
                .content("내용")
                .topPageId(1)
                .parentPageId(6L)
                .build();

        PageSaveResponseDto response = pageRepository.save(request);
        Assertions.assertThat(response.getPageId()).isEqualTo(6L);
    }

    @DisplayName("페이지 조회에 성공할 것이다.")
    @Test
    void findPageTest() throws Exception {

        PageFindResponseDto response = pageService.findById(6L);
        System.out.println(response.toString());

        Assertions.assertThat(response.getPageId()).isEqualTo(6L);
    }
}
