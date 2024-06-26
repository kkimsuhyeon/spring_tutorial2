package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.dto.request.article.RequestArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record ArticleDto(Long id, String title, String content, String hashtag, Member member,
                         List<ArticleCommentDto> comments) {

    public static ArticleDto of(Long id, String title, String content, String hashtag, Member member, List<ArticleCommentDto> comments) {
        return new ArticleDto(id, title, content, hashtag, member, comments);
    }

    public static ArticleDto of(Long id, String title, String content, String hashtag, Member member) {
        return new ArticleDto(id, title, content, hashtag, member, new ArrayList<>());
    }

    public static ArticleDto of(String title, String content, String hashtag, Member member, List<ArticleCommentDto> comments) {
        return new ArticleDto(null, title, content, hashtag, member, comments);
    }

    public static ArticleDto of(String title, String content, String hashtag, Member member) {
        return new ArticleDto(null, title, content, hashtag, member, new ArrayList<>());
    }

    public static ArticleDto fromRequest(RequestArticle request){
        return request.getDto();
    }

    public static ArticleDto fromNoComment(Article entity) {
        return ArticleDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getMember()
        );
    }

    public static ArticleDto fromWithComment(Article entity) {
        return ArticleDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getMember(),
                entity.getArticleComments().stream().map(ArticleCommentDto::from).collect(Collectors.toList()));
    }

    public Article toEntity() {
        return Article.of(title, content, hashtag, null);
    }

    public boolean isHashtagEmpty() {
        return hashtag == null || hashtag.isEmpty();
    }
}
