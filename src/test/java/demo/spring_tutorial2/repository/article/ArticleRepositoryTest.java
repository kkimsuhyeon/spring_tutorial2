package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.config.JpaConfig;
import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleStatus;
import demo.spring_tutorial2.dto.RequestArticleUpdate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(JpaConfig.class)
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleRepositoryTest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Test
    @DisplayName("select 테스트")
    public void selectTest() {
        List<Article> articles = articleRepository.findAll();
        Assertions.assertThat(articles).isNotNull().hasSize(4);
    }

    @Test
    @DisplayName("insert 테스트")
    public void insertTest() {
        long count = articleRepository.count();
        articleRepository.save("new article", "new article content", "");

        Assertions.assertThat(articleRepository.count()).isEqualTo(count + 1);
    }

    @Test
    @DisplayName("select id 테스트")
    public void selectOneTest() {
        Article article = articleRepository.findById(1L).orElseThrow();
        Assertions.assertThat(article.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("update 테스트")
    public void updateTest() {
        Article article = articleRepository.findById(1L).orElseThrow();

        String updatedHashtag = "#springboot";
        ArticleStatus updatedStatus = ArticleStatus.PUBLIC;

        if (articleRepository instanceof ArticleRepositoryJPA) {
            article.setHashtag(updatedHashtag);
            article.setStatus(updatedStatus);
        } else if (articleRepository instanceof ArticleRepositoryJDBC) {
            RequestArticleUpdate request = new RequestArticleUpdate();
            request.setStatus(updatedStatus);
            request.setHashTag(updatedHashtag);
            ((ArticleRepositoryJDBC) articleRepository).update(article.getId(), request);

            article = articleRepository.findById(1L).orElseThrow();
        } else {
            Assertions.fail("통과되면 안됨");
        }

        Assertions.assertThat(article)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag)
                .hasFieldOrPropertyWithValue("status", updatedStatus);
    }

    @Test
    @DisplayName("delete 테스트")
    public void deleteTest() {
        Long articleId = 1L;
        articleRepository.deleteById(articleId);

        Article article = articleRepository.findById(articleId).orElseThrow();
        Assertions.assertThat(article.getStatus()).isEqualTo(ArticleStatus.DELETE);
    }

    @Test
    @DisplayName("real delete 테스트")
    public void realDeleteTest() {
        Article article = articleRepository.findById(1L).orElseThrow();

        long count = articleRepository.count();

        articleRepository.realDeleteById(article.getId());

        Assertions.assertThat(articleRepository.count()).isEqualTo(count - 1);
    }
}