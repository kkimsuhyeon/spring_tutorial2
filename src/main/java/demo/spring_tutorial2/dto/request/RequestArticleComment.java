package demo.spring_tutorial2.dto.request;

import demo.spring_tutorial2.domain.CommentStatus;
import demo.spring_tutorial2.dto.domain.ArticleCommentDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class RequestArticleComment {

    @NotNull
    private final Long articleId;

    private final String content;

    private final CommentStatus status;

    public static RequestArticleComment of(Long articleId, String content, CommentStatus status) {
        return new RequestArticleComment(articleId, content, status);
    }

    public ArticleCommentDto toDto() {
        return ArticleCommentDto.of(articleId, content);
    }
}
