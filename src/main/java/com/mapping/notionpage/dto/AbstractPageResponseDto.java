package com.mapping.notionpage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AbstractPageResponseDto {

    private long pageId;
    private String title;
    private Long parentPageId;
}
