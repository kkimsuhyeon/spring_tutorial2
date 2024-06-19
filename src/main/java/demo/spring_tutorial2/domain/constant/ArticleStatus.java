package demo.spring_tutorial2.domain.constant;

import lombok.Getter;

public enum ArticleStatus {
    PRIVATE("비공개"),
    PUBLIC("공개"),
    DELETE("삭제");

    @Getter
    private final String description;

    ArticleStatus(String description) {
        this.description = description;
    }
}
