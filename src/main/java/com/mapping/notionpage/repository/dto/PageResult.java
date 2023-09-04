package com.mapping.notionpage.repository.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class PageResult {
    private long pageId;
    private String title;

    private String content;

    private ArrayList<Long> subPages;

    private ArrayList<Long> breadCrumbs;

    public PageResult(long id, String title, String content, ArrayList<Long> subPages, ArrayList<Long> breadCrumbs) {
        this.pageId=id;
        this.title=title;
        this.content=content;
        this.subPages=subPages;
        this.breadCrumbs=breadCrumbs;
    }
}
