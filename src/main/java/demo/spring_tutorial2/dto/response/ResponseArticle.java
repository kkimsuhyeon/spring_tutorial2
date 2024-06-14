package demo.spring_tutorial2.dto.response;

import demo.spring_tutorial2.dto.domain.ArticleCommentDto;
import demo.spring_tutorial2.dto.domain.ArticleDto;

import java.util.List;

public record ResponseArticle(Long id, String title, String content, String hashtag, List<ArticleCommentDto> comments) {

    public static ResponseArticle of(Long id, String title, String content, String hashtag, List<ArticleCommentDto> comments) {
        return new ResponseArticle(id, title, content, hashtag, comments);
    }

    public static ResponseArticle from(ArticleDto dto) {
        return ResponseArticle.of(dto.id(), dto.title(), dto.content(), dto.hashtag(), dto.comments());
    }
}
