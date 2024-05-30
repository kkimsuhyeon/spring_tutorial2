package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryJDBC implements ArticleRepository {

    @Override
    public List<Article> findAll() {
        return List.of();
    }
}
