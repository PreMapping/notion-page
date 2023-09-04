package com.mapping.notionpage.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPageDTO {

    private Long pageId;
    private String title;
    private List<Long> subPages;
    private List<Long> breadcrumbs;
}
