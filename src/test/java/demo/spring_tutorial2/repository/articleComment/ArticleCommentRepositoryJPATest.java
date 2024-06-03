package demo.spring_tutorial2.repository.articleComment;

import demo.spring_tutorial2.config.JpaConfig;
import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;
import demo.spring_tutorial2.repository.article.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JpaConfig.class)
class ArticleCommentRepositoryJPATest {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleCommentRepositoryJPATest(ArticleCommentRepository articleCommentRepository, ArticleRepository articleRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
    }

    @Test
    @DisplayName("select 테스트")
    public void selectTest() {
        List<ArticleComment> articleComments = articleCommentRepository.findAll();
        Assertions.assertThat(articleComments).isNotNull().hasSize(300);
    }

    @Test
    @DisplayName("insert 테스트")
    public void insertTest() {
        Article article = articleRepository.findById(1L).orElseThrow();
        int commentInArticleCount = article.getArticleComments().size();
        long articleCommentCount = articleCommentRepository.count();

        articleCommentRepository.save(article, "new comment");

        Assertions.assertThat(articleCommentRepository.count()).isEqualTo(articleCommentCount + 1);
        Assertions.assertThat(article.getArticleComments().size()).isEqualTo(commentInArticleCount + 1);
    }

    @Test
    @DisplayName("update 테스트")
    public void updateTest() {
        ArticleComment comment = articleCommentRepository.findById(1L).orElseThrow();
        String updatedContent = "??????";
        comment.setContent(updatedContent);

        Assertions.assertThat(comment).hasFieldOrPropertyWithValue("content", updatedContent);
    }

    @Test
    @DisplayName("delete 테스트")
    public void deleteTest() {
        Article article = articleRepository.findById(1L).orElseThrow();
        ArticleComment articleComment = articleCommentRepository.findById(113L).orElseThrow();

        long prevCount = articleCommentRepository.count();
        int prevSize = article.getArticleComments().size();

        // 이게 관계가 떨어져야지 remove가 발동하는건가??
        articleCommentRepository.delete(articleComment);
        article.getArticleComments().remove(articleComment);

        Assertions.assertThat(articleCommentRepository.count()).isEqualTo(prevCount - 1);
        Assertions.assertThat(article.getArticleComments().size()).isEqualTo(prevSize - 1);
    }
}