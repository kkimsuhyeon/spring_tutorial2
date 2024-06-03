package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleCommentRepositoryJPA implements ArticleCommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ArticleComment> findAll() {
        List<ArticleComment> resultList = em.createQuery("SELECT c FROM ArticleComment AS c", ArticleComment.class).getResultList();

        return resultList;
    }

    @Override
    public Optional<ArticleComment> findById(Long id) {
        ArticleComment articleComment = em.find(ArticleComment.class, id);

        return Optional.ofNullable(articleComment);
    }

    @Override
    public long count() {
        List<ArticleComment> resultList = em.createQuery("SELECT c FROM ArticleComment AS c", ArticleComment.class).getResultList();

        return resultList.size();
    }

    @Override
    public ArticleComment save(Article article, String content) {
        ArticleComment comment = ArticleComment.of(article, content);
        em.persist(comment);
        article.addArticleComment(comment);

        return comment;
    }

    @Override
    public void delete(ArticleComment comment) {
        em.remove(comment);
    }

    public void flush() {
        em.flush();
    }
}
