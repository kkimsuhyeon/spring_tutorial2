package demo.spring_tutorial2.repository.article;

import demo.spring_tutorial2.config.JpaConfig;
import demo.spring_tutorial2.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(JpaConfig.class)
class ArticleRepositoryJPATest {

    private final ArticleRepository articleRepositoryJPA;

    @Autowired
    public ArticleRepositoryJPATest(ArticleRepository articleRepositoryJPA) {
        this.articleRepositoryJPA = articleRepositoryJPA;
    }

    @Test
    @DisplayName("select 테스트")
    public void selectTest() {
        System.out.println(articleRepositoryJPA);
        List<Article> articles = articleRepositoryJPA.findAll();

        Assertions.assertThat(articles).isNotNull().hasSize(123);
    }

}