package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleRepository {
    public List<Article> findAll();
}
