package com.mapping.notionpage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AbstractPageResponseDto {

    private long pageId;
    private String title;
    private Long parentPageId;

    @Override
    public String toString() {
        return "\n\t\t{\n\t\t\tpageId : " + pageId +
                ",\n\t\t\ttitle : " + title +
                ",\n\t\t\tparentPageId : " + this.parentPageId +
                ",\n\t\t}\n";
    }
}
