package com.mapping.notionpage.service;

import com.mapping.notionpage.repository.dto.Page;
import com.mapping.notionpage.repository.dto.PageResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class PageServiceTest {

    @Autowired
    private PageService pageService;

//    @DisplayName("Db 저장 테스트")
//    @Test
//    void dbSaveTest() throws Exception{
//        //given
//
//        //when
//        Page page=pageService.getResult(5L);
//        //then
//        Assertions.assertThat(page.getId()).isEqualTo(1);
//    }
    @DisplayName("결과 테스트(5 입력받을시)")
    @Test
    void dbSaveTest() throws Exception{
        //given
        Long input =5L;
        //when
        PageResult pageResult=pageService.getResult(input);
        ArrayList<Long> compare=new ArrayList<>();
        compare.add(1L);
        compare.add(2L);
        compare.add(5L);
        //then
        Assertions.assertThat(pageResult.getBreadCrumbs()).isEqualTo(compare);
    }
}
