package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Article;

public record ArticleDto(Long id, String title, String content, String hashtag) {

    public static ArticleDto of(String title, String content, String hashtag) {
        return new ArticleDto(null, title, content, hashtag);
    }

    public static ArticleDto of(Long id, String title, String content, String hashtag) {
        return new ArticleDto(id, title, content, hashtag);
    }

    public static ArticleDto from(Article entity) {
        return ArticleDto.of(entity.getTitle(), entity.getContent(), entity.getHashtag());
    }

    public Article toEntity() {
        return Article.of(title, content, hashtag);
    }
}
