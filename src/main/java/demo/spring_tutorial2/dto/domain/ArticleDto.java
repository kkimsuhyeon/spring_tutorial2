package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record ArticleDto(Long id, String title, String content, String hashtag, List<ArticleCommentDto> comments) {

    public static ArticleDto of(Long id, String title, String content, String hashtag, List<ArticleCommentDto> comments) {
        return new ArticleDto(id, title, content, hashtag, comments);
    }

    public static ArticleDto of(Long id, String title, String content, String hashtag) {
        return new ArticleDto(id, title, content, hashtag, new ArrayList<>());
    }

    public static ArticleDto of(String title, String content, String hashtag, List<ArticleCommentDto> comments) {
        return new ArticleDto(null, title, content, hashtag, comments);
    }

    public static ArticleDto of(String title, String content, String hashtag) {
        return new ArticleDto(null, title, content, hashtag, new ArrayList<>());
    }

    public static ArticleDto fromNoComment(Article entity) {
        return ArticleDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag()
        );
    }

    public static ArticleDto fromWithComment(Article entity) {
        return ArticleDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getArticleComments().stream().map(ArticleCommentDto::from).collect(Collectors.toList()));
    }

    public Article toEntity() {
        return Article.of(title, content, hashtag, null);
    }

    public boolean isHashtagEmpty() {
        return hashtag == null || hashtag.isEmpty();
    }
}
