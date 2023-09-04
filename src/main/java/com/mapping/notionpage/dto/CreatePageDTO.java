package com.mapping.notionpage.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreatePageDTO {

    private String title;
    private String content;
    private Long topNode;
    private Long parentPageId;
}
