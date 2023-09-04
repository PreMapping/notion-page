package com.mapping.notionpage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapping.notionpage.dao.PageDAO;
import com.mapping.notionpage.dto.CreatePageDTO;
import com.mapping.notionpage.dto.PageDTO;
import com.mapping.notionpage.dto.RequestPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageService {

    private final PageDAO pageDAO;


    public Long createPage(CreatePageDTO createPageDTO) {
        return pageDAO.createPage(createPageDTO);
    }

    public String getPageInfo(Long pageId) {
        PageDTO pageInfo = pageDAO.getPageInfo(pageId);
        List<Long> subPages = pageDAO.getSubPages(pageId);

        Long findPageId = pageInfo.getPageId();
        String title = pageInfo.getTitle();
        Long parentPageId = pageInfo.getParentPageId();
        Long topNode = pageInfo.getTopNode();

        List<Long> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(topNode);
        breadcrumbs.add(parentPageId);

        RequestPageDTO requestPageDTO = new RequestPageDTO(findPageId, title, subPages, breadcrumbs);
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 변환
        try {
            return objectMapper.writeValueAsString(requestPageDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 실패 ", e);
        }
    }

}