package com.mapping.notionpage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageCreateRequestDTO {
    private String title;
    private String content;
    private Long topPageId;
    private Long parentPageId;
}
