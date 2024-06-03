package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.config.JpaConfig;
import demo.spring_tutorial2.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DataJpaTest
@Import(JpaConfig.class)
class ArticleRepositoryJPATest {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleRepositoryJPATest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Test
    @DisplayName("select 테스트")
    public void selectTest() {
        List<Article> articles = articleRepository.findAll();
        Assertions.assertThat(articles).isNotNull().hasSize(123);
    }

    @Test
    @DisplayName("insert 테스트")
    public void insertTest() {
        long count = articleRepository.count();
        articleRepository.save("new article", "new article content", "");

        Assertions.assertThat(articleRepository.count()).isEqualTo(count + 1);
    }

    @Test
    @DisplayName("update 테스트")
    public void updateTest() {
        Article article = articleRepository.findById(1L).orElseThrow();

        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);
        ((ArticleRepositoryJPA) articleRepository).flush();

        Assertions.assertThat(article).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @Test
    @DisplayName("delete 테스트")
    public void deleteTest() {

        Article article = articleRepository.findById(1L).orElseThrow();
        long count = articleRepository.count();
        int commentSize = article.getArticleComments().size();

        articleRepository.delete(article);

        Assertions.assertThat(articleRepository.count()).isEqualTo(count - 1);
    }
}