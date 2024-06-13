package demo.spring_tutorial2.dto.request;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.domain.ArticleCommentDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class RequestArticleComment {

    @NotNull
    private final Long articleId;

    @NotNull
    private final String content;

    public static RequestArticleComment of(Long articleId, String content) {
        return new RequestArticleComment(articleId, content);
    }

    public ArticleCommentDto toDto() {
        return ArticleCommentDto.of(articleId, content);
    }
}
