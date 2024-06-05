package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.RequestArticleUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ArticleRepository {
    public List<Article> findAll();

    public long count();

    public Article save(String title, String content, String hashTag);

    public Optional<Article> findById(Long id);

    public void deleteById(Long id);

    public void realDeleteById(Long id);
}
