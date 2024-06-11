package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.SearchValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepository {

    public Page<Article> findBySearchValue(SearchValue search, Pageable pageable);
}
