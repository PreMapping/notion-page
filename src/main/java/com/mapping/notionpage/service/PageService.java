package com.mapping.notionpage.service;

import com.mapping.notionpage.dto.PageCreateRequestDTO;
import com.mapping.notionpage.dto.PageResponseDTO;
import com.mapping.notionpage.entity.Page;
import com.mapping.notionpage.repository.PageRepository;
import com.mapping.notionpage.sql.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PageService {
    private final PageRepository repository;

    public Boolean createForTopPage(PageCreateRequestDTO dto) {
        return repository.createPage(Query.insertForTopPage(dto));
    }

    public Boolean createForTopPageAndParentPage(PageCreateRequestDTO dto) {
        return repository.createPage(Query.insertForTopPageAndParentPage(dto));
    }

    public PageResponseDTO findById(Long id) {
        String findByIdSql = Query.selectById(id);
        Page foundId = repository.findById(findByIdSql);

        Long topPageId = foundId.getTopPageId();
        String findAllByTopPageIdSql = Query.selectByTopPageId(topPageId);
        List<Page> list = repository.findAllByTopPageId(findAllByTopPageIdSql);

        List<Long> breadcrumbs = getBreadcrumbs(list, id);
        List<Long> subPageList = getSubPageList(list, id);

        return PageResponseDTO.builder()
                .id(id)
                .title(foundId.getTitle())
                .content(foundId.getContent())
                .breadcrumbs(breadcrumbs)
                .subPageList(subPageList)
                .build();
    }

    private List<Long> getBreadcrumbs(List<Page> list, Long startId) {
        List<Long> breadcrumbs = new ArrayList<>();
        Long[] find = new Long[list.size() + 1];

        for (Page page : list) {
            find[Math.toIntExact(page.getId())] = page.getParentPageId();
        }

        Long id = startId;

        while (true) {
            if (id != 0) {
                breadcrumbs.add(id);
                id = find[Math.toIntExact(id)];
            } else break;
        }

        Collections.reverse(breadcrumbs);

        return breadcrumbs;
    }

    private List<Long> getSubPageList(List<Page> list, Long targetId) {
        return list.stream()
                .filter(page -> Objects.equals(page.getParentPageId(), targetId))
                .map(Page::getId)
                .collect(Collectors.toList());
    }

}
