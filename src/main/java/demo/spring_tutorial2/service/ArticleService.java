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

import java.util.Optional;

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

    public ArticleDto getArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);

        return article
                .map(ArticleDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 id 유저 존재하지 않음"));
    }

    public void save() {

    }

    public void update() {

    }

    public void delete() {

    }
}
