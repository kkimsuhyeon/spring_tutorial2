package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryJPA implements ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }
}
