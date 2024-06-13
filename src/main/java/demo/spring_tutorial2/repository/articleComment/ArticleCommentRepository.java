package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;

public interface ArticleCommentRepository {
    public void save(Article article, ArticleComment comment);
}
