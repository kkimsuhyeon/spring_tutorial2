package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryJPA implements ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article AS a", Article.class).getResultList();
    }

    public long count() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList().size();
    }

    @Override
    public Article save(String title, String content, String hashTag) {
        Article newArticle = Article.of(title, content, hashTag);
        em.persist(newArticle);

        return newArticle;
    }

    @Override
    public Optional<Article> findById(Long id) {
        Article article = em.find(Article.class, id);

        return Optional.ofNullable(article);
    }

    @Override
    public void delete(Article article) {
        em.remove(article);
    }

    public void flush() {
        em.flush();
    }

}
