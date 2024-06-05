package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleStatus;
import demo.spring_tutorial2.dto.RequestArticleUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryJDBC implements ArticleRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Article> findAll() {
        String query = "SELECT * FROM article";
        List<Article> result = jdbcTemplate.query(query, articleRowMapper());

        return result;
    }

    @Override
    public long count() {
        String query = "SELECT * FROM article";
        List<Article> result = jdbcTemplate.query(query, articleRowMapper());

        return result.size();
    }

    @Override
    public Article save(String title, String content, String hashTag) {

        String query = String.format("INSERT INTO article " +
                "(title, content, hashtag, status, created_at, created_by, modified_at, modified_by) " +
                "VALUES ('%s', '%s', '%s', 'PRIVATE', NOW(), '%s', NOW(), '%s') ", title, content, hashTag, "test", "test");

        jdbcTemplate.execute(query);

        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        String query = String.format("SELECT * FROM article WHERE article_id = %d", id);

        List<Article> result = jdbcTemplate.query(query, articleRowMapper());

        return result.stream().findFirst();
    }

    public void update(Long id, RequestArticleUpdate request) {
        String query = String.format("UPDATE article SET " +
                "status = '%s', " +
                "hashtag = '%s' " +
                "WHERE article_id = %d", request.getStatus(), request.getHashTag(), id);

        jdbcTemplate.execute(query);
    }

    @Override
    public void deleteById(Long id) {
        String query = String.format("UPDATE article SET " +
                "status = 'DELETE' " +
                "WHERE article_id = %d", id);

        jdbcTemplate.execute(query);
    }

    @Override
    public void realDeleteById(Long id) {

        String commentQuery = String.format("DELETE FROM article_comment " +
                "WHERE article_id = %d", id);

        String query = String.format("DELETE FROM article " +
                "WHERE article_id = %d", id);

        jdbcTemplate.execute(commentQuery);
        jdbcTemplate.execute(query);
    }

    private RowMapper<Article> articleRowMapper() {
        return new RowMapper<Article>() {

            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {

                Article article = Article.of(
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("hashtag"));

                article.setId(rs.getLong("article_id"));
                article.setStatus(ArticleStatus.valueOf(rs.getString("status")));

                return article;
            }
        };
    }


}
