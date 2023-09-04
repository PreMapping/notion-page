package com.mapping.notionpage.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PageDTO {

    private Long pageId;
    private String title;
    private String content;
    private Long topNode;
    private Long parentPageId;


}
