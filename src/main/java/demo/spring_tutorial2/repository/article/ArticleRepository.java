package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.SearchValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleRepository {

    public Page<Article> findBySearchValue(SearchValue search, Pageable pageable);

    public Optional<Article> findByIdWithComment(Long id);

    public Optional<Article> findById(Long id);

    public void save(Article article);

    public Article delete(Article article);

    public Article deleteFromDatabase(Article article);
}
