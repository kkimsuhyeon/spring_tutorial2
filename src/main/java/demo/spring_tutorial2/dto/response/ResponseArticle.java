package demo.spring_tutorial2.dto.response;

import demo.spring_tutorial2.dto.domain.ArticleDto;

public record ResponseArticle(Long id, String title, String content, String hashtag) {

    public static ResponseArticle of(Long id, String title, String content, String hashtag) {
        return new ResponseArticle(id, title, content, hashtag);
    }

    public static ResponseArticle from(ArticleDto dto) {
        return ResponseArticle.of(dto.id(), dto.title(), dto.content(), dto.hashtag());
    }
}
