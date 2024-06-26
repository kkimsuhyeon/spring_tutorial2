package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.config.JpaConfig;
import demo.spring_tutorial2.config.RepositoryConfig;
import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.dto.SearchValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest
@Import({JpaConfig.class, RepositoryConfig.class})
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleRepositoryTest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Test
    @DisplayName("전체 가져오기")
    public void findAllTest() {
        Page<Article> result = articleRepository.findBySearchValue(new SearchValue(), Pageable.ofSize(100));
        List<Article> articles = result.getContent();

        Assertions.assertThat(articles).hasSize(13).first().hasFieldOrPropertyWithValue("title", "title1");
    }

    @Test
    @DisplayName("search값을 통해서 가져오기")
    public void searchTest() {
        Page<Article> articles = articleRepository.findBySearchValue(new SearchValue(null, null), PageRequest.of(0, 10));
        Assertions.assertThat(articles.getContent()).isNotNull().hasSize(10);
    }

    @Test
    @DisplayName("id값으로 가져오기")
    public void findByIdTest() throws Exception {

        Article article = articleRepository.findById(1L).orElseThrow(() -> {
            Assertions.fail("찾지 못함");
            return new Exception("");
        });

        Assertions.assertThat(article).hasFieldOrPropertyWithValue("title", "title1");
    }

    @Test
    @DisplayName("id값으로 comment같이 가져오기")
    public void findByIdWithCommentTest() throws Exception {
        Article article = articleRepository.findByIdWithComment(1L).orElseThrow(() -> {
            Assertions.fail("찾지 못함");
            return new Exception("");
        });

        Assertions.assertThat(article).hasFieldOrPropertyWithValue("title", "title1");
    }

    @Test
    @DisplayName("게시글 생성")
    public void saveArticleTest() {

    }

    @Test
    @DisplayName("게시글 수정")
    public void updateArticleTest() {

    }

    @Test
    @DisplayName("게시글 삭제 상태 변경")
    public void deleteArticleTest() {

    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteArticleFromDatabaseTest() {

    }
}