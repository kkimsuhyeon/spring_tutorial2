package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;
import demo.spring_tutorial2.domain.CommentStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleCommentRepositoryJPA implements ArticleCommentRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Article article, ArticleComment comment) {
        comment.setStatus(CommentStatus.PUBLIC);

        article.addComment(comment);
        entityManager.persist(comment);
    }

    public void update() {
    }


}
