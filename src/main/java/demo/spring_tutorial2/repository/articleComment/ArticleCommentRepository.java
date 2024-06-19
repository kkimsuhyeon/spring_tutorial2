package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;

import java.util.Optional;

public interface ArticleCommentRepository {
    public Optional<ArticleComment> findById(Long id);

    public void save(Article article, ArticleComment comment);

    public void delete(ArticleComment comment);
}
