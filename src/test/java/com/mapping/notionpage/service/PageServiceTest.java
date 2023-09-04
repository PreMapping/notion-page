package com.mapping.notionpage.service;

import com.mapping.notionpage.repository.dto.Page;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PageServiceTest {

    @Autowired
    private PageService pageService;

    @DisplayName("Db 저장 테스트")
    @Test
    void dbSaveTest() throws Exception{
        //given

        //when
        Page page=pageService.getResult(1);
        //then
        Assertions.assertThat(page.getId()).isEqualTo(1);
    }
}
