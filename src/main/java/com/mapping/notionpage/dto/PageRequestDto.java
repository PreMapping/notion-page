package com.mapping.notionpage.dto;

import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PageRequestDto {

    private String title;
    private String content;
    private long topPageId;
    private Long parentPageId;
}
