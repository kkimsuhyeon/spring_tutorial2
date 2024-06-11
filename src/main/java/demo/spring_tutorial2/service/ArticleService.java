package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.SearchValue;
import demo.spring_tutorial2.dto.domain.ArticleDto;
import demo.spring_tutorial2.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<ArticleDto> searchArticle(SearchValue searchValue, Pageable pageable) {
        Page<Article> result = articleRepository.findBySearchValue(searchValue, pageable);
        return result.map(ArticleDto::from);
    }
}
