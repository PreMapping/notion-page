package com.mapping.notionpage.service;

import com.mapping.notionpage.dto.AbstractPageResponseDto;
import com.mapping.notionpage.dto.PageFindResponseDto;
import com.mapping.notionpage.dto.PageSaveResponseDto;
import com.mapping.notionpage.repository.PageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    public PageFindResponseDto findById(long pageId) throws SQLException {

        PageSaveResponseDto findNode = pageRepository.findById(pageId);

        List<AbstractPageResponseDto> subPages = new ArrayList<>();
        List<AbstractPageResponseDto> parentPages = new ArrayList<>();

        Long findNodeId = findNode.getPageId();
        Long topNodeId = findNode.getTopPageId();

        List<AbstractPageResponseDto> manyByTopNode = pageRepository.findManyByTopNode(topNodeId, findNodeId);

        Long parentNode = findNode.getParentPageId();

        for (int i = manyByTopNode.size() - 1; i >= 0; i--) {
            AbstractPageResponseDto node = manyByTopNode.get(i);

            Optional<Long> parentPageId = Optional.ofNullable(node.getParentPageId());
            if (parentPageId.isEmpty())
                continue;

            // subPages 체크
            if (parentPageId.get().equals(findNodeId))
                subPages.add(node);

            // parentPage 체크
            if (node.getPageId() == parentNode) {
                parentPages.add(0, node);
                parentNode = node.getParentPageId();
            }
        }

        return PageFindResponseDto.builder()
                    .pageId(findNode.getPageId())
                    .title(findNode.getTitle())
                    .content(findNode.getContent())
                    .subPages(subPages)
                    .breadcrumbs(parentPages)
                    .build();
    }
}
