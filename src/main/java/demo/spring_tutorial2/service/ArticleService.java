package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.SearchValue;
import demo.spring_tutorial2.dto.domain.ArticleDto;
import demo.spring_tutorial2.repository.article.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<ArticleDto> searchArticles(SearchValue searchValue, Pageable pageable) {
        Page<Article> result = articleRepository.findBySearchValue(searchValue, pageable);
        return result.map(ArticleDto::from);
    }

    public ArticleDto getArticle(Long id) {
        Optional<Article> article = articleRepository.findByIdWithComment(id);

        return article
                .map(ArticleDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 id 유저 존재하지 않음"));
    }

    @Transactional
    public void save(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    @Transactional
    public void update(Long articleId, ArticleDto dto) {
        Optional<Article> article = articleRepository.findById(articleId);

        article.map((a) -> {
            a.setTitle(dto.title());
            a.setContent(dto.content());

            if (!dto.isHashtagEmpty()) {
                a.setHashtag(dto.hashtag());
            }

            return a;
        }).orElseThrow(() -> new IllegalArgumentException("해당 id 유저 존재하지 않음"));
    }

    public void delete(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        article.map(articleRepository::delete).orElseThrow(() -> new IllegalArgumentException("해당 id 유저 존재하지 않음"));
    }
}
