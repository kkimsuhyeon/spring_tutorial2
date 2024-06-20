package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.constant.ArticleStatus;
import demo.spring_tutorial2.dto.SearchValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryJPA implements ArticleRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<Article> findBySearchValue(SearchValue search, Pageable pageable) {

        String query = "SELECT a FROM Article AS a";

        if (!search.isEmpty()) {
            query += searchQueryParser(search);
        }

        if (pageable.getSort().isSorted()) {
            query += pageQueryParser(pageable);
        }

        Long totalCount = getTotalCount(search);
        List<Article> articles = entityManager.createQuery(query, Article.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(articles, pageable, totalCount);
    }

    public Page<Article> findByTitle(String title, Pageable pageable) {
        String query = "SELECT a FROM Article AS a" +
                " WHERE a.title LIKE %:title%";

        List<Article> articles = entityManager
                .createQuery(query, Article.class)
                .setParameter("title", title)
                .getResultList();

        return new PageImpl<>(articles, pageable, articles.size());
    }

    public Page<Article> findByContent(String content, Pageable pageable) {
        String query = "SELECT a FROM Article AS a" +
                " WHERE a.title LIKE %:content%";

        List<Article> articles = entityManager
                .createQuery(query, Article.class)
                .setParameter("content", content)
                .getResultList();

        return new PageImpl<>(articles, pageable, articles.size());
    }

    public Optional<Article> findByIdWithComment(Long id) {
        String query = "SELECT a FROM Article AS a" +
                " LEFT JOIN FETCH a.articleComments AS ac" +
                " WHERE a.id = :id";

        Article article = entityManager.createQuery(query, Article.class).setParameter("id", id).getSingleResult();

        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        Article article = entityManager.find(Article.class, id);
        return Optional.ofNullable(article);
    }

    @Override
    public void save(Article article) {
        article.setStatus(ArticleStatus.PUBLIC);
        entityManager.persist(article);
    }

    @Override
    public Article delete(Article article) {
        article.setStatus(ArticleStatus.DELETE);
        return article;
    }

    @Override
    public Article deleteFromDatabase(Article article) {
        entityManager.remove(article);
        return article;
    }

    private Long getTotalCount(SearchValue search) {
        String countQuery = "SELECT COUNT(a) FROM Article AS a";

        if (!search.isEmpty()) {
            countQuery += searchQueryParser(search);
        }

        return entityManager.createQuery(countQuery, Long.class).getSingleResult();
    }

    private String pageQueryParser(Pageable pageable) {

        String orderQuery = " ORDER BY";
        StringBuilder queryBuilder = new StringBuilder(orderQuery);

        for (Sort.Order order : pageable.getSort()) {
            queryBuilder.append(" a.%s %s".formatted(order.getProperty(), order.getDirection()));
        }

        return queryBuilder.toString();
    }

    private String searchQueryParser(SearchValue search) {
        String searchQuery = " WHERE";

        if (!search.isTitleEmpty()) {
            searchQuery += " a.title LIKE '%%%s%%'".formatted(search.getTitle());
        }

        if (!search.isContentEmpty()) {
            if (!searchQuery.endsWith("WHERE")) {
                searchQuery += " AND";
            }
            searchQuery += " a.content LIKE '%%%s%%'".formatted(search.getContent());
        }

        return searchQuery;
    }

}
