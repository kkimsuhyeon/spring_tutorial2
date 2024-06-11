package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.SearchValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryJPA implements ArticleRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Page<Article> findBySearchValue(SearchValue search, Pageable pageable) {

        String query = "SELECT a FROM Article AS a ";
        String countQuery = "SELECT COUNT(a) FROM Article AS a";

        if (pageable.getSort().isSorted()) {
            query = pageQueryParser(query, pageable);
        }

        Long totalCount = getTotalCount(search);
        List<Article> articles = entityManager.createQuery(query, Article.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize() + 1)
                .getResultList();

        return new PageImpl<>(articles, pageable, totalCount);
    }

    public Page<Article> findByTitle(String title, Pageable pageable) {
        String query = "SELECT a FROM Article AS a " +
                "WHERE a.title LIKE %:title%";

        List<Article> articles = entityManager
                .createQuery(query, Article.class)
                .setParameter("title", title)
                .getResultList();

        return new PageImpl<>(articles, pageable, articles.size());
    }

    public Page<Article> findByContent(String content, Pageable pageable) {
        String query = "SELECT a FROM Article AS a " +
                "WHERE a.title LIKE %:content%";

        List<Article> articles = entityManager
                .createQuery(query, Article.class)
                .setParameter("content", content)
                .getResultList();

        return new PageImpl<>(articles, pageable, articles.size());
    }

    private Long getTotalCount(SearchValue search) {
        String countQuery = "SELECT COUNT(a) FROM Article AS a";
        return entityManager.createQuery(countQuery, Long.class).getSingleResult();
    }

    private String pageQueryParser(String query, Pageable pageable) {
        query += query + "ORDER BY ";
        StringBuilder queryBuilder = new StringBuilder(query);

        for (Sort.Order order : pageable.getSort()) {
            queryBuilder.append("a.%s %s".formatted(order.getProperty(), order.getDirection()));
        }

        query = queryBuilder.toString();

        return query;
    }

}
