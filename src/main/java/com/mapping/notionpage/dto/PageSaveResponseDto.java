package com.mapping.notionpage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageSaveResponseDto {

    private long pageId;
    private String title;
    private String content;
    private long topPageId;
    private Long parentPageId;

    public PageSaveResponseDto(long pageId, PageRequestDto requestDto) {
        this.pageId = pageId;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.topPageId = requestDto.getTopPageId();
        this.parentPageId = requestDto.getParentPageId();
    }
}
