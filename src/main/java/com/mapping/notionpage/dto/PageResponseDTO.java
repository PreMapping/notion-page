package com.mapping.notionpage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PageResponseDTO {
    private Long id;
    private String title;
    private String content;
    private List<Long> breadcrumbs;
    private List<Long> subPageList;
}
