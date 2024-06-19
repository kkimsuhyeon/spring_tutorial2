package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;
import demo.spring_tutorial2.domain.CommentStatus;

public record ArticleCommentDto(Long id, Long articleId, String content, CommentStatus status) {

    public static ArticleCommentDto of(Long id, Long articleId, String content, CommentStatus status) {
        return new ArticleCommentDto(id, articleId, content, status);
    }

    public static ArticleCommentDto of(Long articleId, String content, CommentStatus status) {
        return new ArticleCommentDto(null, articleId, content, status);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return ArticleCommentDto.of(entity.getId(), entity.getArticle().getId(), entity.getContent(), entity.getStatus());
    }

    public ArticleComment toEntity(Article article) {
        return ArticleComment.of(content, article);
    }
}
