package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;

public record ArticleCommentDto(Long id, Long articleId, String content) {

    public static ArticleCommentDto of(Long id, Long articleId, String content) {
        return new ArticleCommentDto(id, articleId, content);
    }

    public static ArticleCommentDto of(Long articleId, String content) {
        return new ArticleCommentDto(null, articleId, content);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return ArticleCommentDto.of(entity.getId(), entity.getArticle().getId(), entity.getContent());
    }

    public ArticleComment toEntity(Article article) {
        return ArticleComment.of(content, article);
    }
}
