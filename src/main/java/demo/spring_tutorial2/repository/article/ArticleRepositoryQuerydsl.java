package demo.spring_tutorial2.repository.article;

import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleStatus;
import demo.spring_tutorial2.domain.QArticle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class ArticleRepositoryQuerydsl implements ArticleRepository {

    @PersistenceContext
    EntityManager em;

    QArticle qArticle;
    JPAQueryFactory queryFactory;

    public ArticleRepositoryQuerydsl() {
        qArticle = QArticle.article;
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Article> findAll() {
        List<Article> articles = queryFactory.select(qArticle).from(qArticle).fetch();
        return articles;
    }

    @Override
    public long count() {
        int size = queryFactory.selectFrom(qArticle).fetch().size();
        return size;
    }

    @Override
    public Article save(String title, String content, String hashTag) {
        Article newArticle = Article.of(title, content, hashTag);
        em.persist(newArticle);

        return newArticle;
    }

    @Override
    public Optional<Article> findById(Long id) {
        Article article = queryFactory.select(qArticle).from(qArticle).where(qArticle.id.eq(id)).fetchOne();
        return Optional.ofNullable(article);
    }


    @Override
    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        article.setStatus(ArticleStatus.DELETE);
    }

    @Override
    public void realDeleteById(Long id) {
        Article article = em.find(Article.class, id);
        em.remove(article);
    }

}
