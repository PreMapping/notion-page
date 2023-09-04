package com.mapping.notionpage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageFindResponseDto {

    private long pageId;
    private String title;
    private String content;
    private List<AbstractPageResponseDto> subPages = new ArrayList<>();
    private List<AbstractPageResponseDto> breadcrumbs = new ArrayList<>();

    @Builder
    public PageFindResponseDto(long pageId,
                               String title,
                               String content,
                               List<AbstractPageResponseDto> subPages,
                               List<AbstractPageResponseDto> breadcrumbs) {
        this.pageId = pageId;
        this.title = title;
        this.content = content;
        this.subPages = new ArrayList<>(subPages);
        this.breadcrumbs = new ArrayList<>(breadcrumbs);
    }
}
