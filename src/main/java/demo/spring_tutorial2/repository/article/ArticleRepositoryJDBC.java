package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Article article) {

    }

    private RowMapper<Article> articleRowMapper() {
        return new RowMapper<Article>() {

            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Article.of("", "", "");
            }
        };
    }


}
