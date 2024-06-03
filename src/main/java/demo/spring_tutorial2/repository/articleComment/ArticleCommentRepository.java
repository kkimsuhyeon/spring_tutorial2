package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;

import java.util.List;
import java.util.Optional;

public interface ArticleCommentRepository {

    public List<ArticleComment> findAll();

    public Optional<ArticleComment> findById(Long id);

    public long count();

    public ArticleComment save(Article article, String content);

    public void delete(ArticleComment comment);

}
