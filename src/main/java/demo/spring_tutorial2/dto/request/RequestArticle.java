package demo.spring_tutorial2.dto.request;

import demo.spring_tutorial2.dto.domain.ArticleDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class RequestArticle {

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    @Nullable
    private String hashtag;

    public static RequestArticle of(String title, String content, String hashtag) {
        return new RequestArticle(title, content, hashtag);
    }

    public static RequestArticle of(String title, String content) {
        return new RequestArticle(title, content);
    }

    public ArticleDto toDto() {
        return ArticleDto.of(title, content, hashtag);
    }
}
