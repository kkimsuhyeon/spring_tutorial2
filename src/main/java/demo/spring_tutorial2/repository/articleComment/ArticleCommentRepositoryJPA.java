package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;
import demo.spring_tutorial2.domain.constant.CommentStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ArticleCommentRepositoryJPA implements ArticleCommentRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<ArticleComment> findById(Long id) {
        ArticleComment articleComment = entityManager.find(ArticleComment.class, id);
        return Optional.ofNullable(articleComment);
    }

    public void save(Article article, ArticleComment comment) {
        comment.setStatus(CommentStatus.PUBLIC);

        article.addComment(comment);
        entityManager.persist(comment);
    }

    @Override
    public void delete(ArticleComment comment) {
        entityManager.remove(comment);
    }
}
