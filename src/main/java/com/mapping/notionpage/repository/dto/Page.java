package com.mapping.notionpage.repository.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Page {
    private long id;
    private String title;

    private String content;

    private long topPageId;

    private Optional<Long> parentPageId;

    public Page(long id, String title, String content, long topNode, Optional<Long> parentPageId) {
        this.id=id;
        this.title=title;
        this.content=content;
        this.topPageId=topNode;
        this.parentPageId=parentPageId;
    }
}
