package demo.spring_tutorial2.dto.request.article;

import demo.spring_tutorial2.dto.domain.ArticleDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class RequestCreateArticle extends RequestArticle {

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    @Nullable
    private final String hashtag;

    @Override
    public ArticleDto toDto() {
        return new ArticleDto(null, title, content, hashtag, null, new ArrayList<>());
    }
}
