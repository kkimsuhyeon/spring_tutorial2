package demo.spring_tutorial2.repository.article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleRepositoryJDBCTest {

    private final ArticleRepository articleRepositoryJDBC;

    @Autowired
    public ArticleRepositoryJDBCTest(ArticleRepository articleRepositoryJDBC) {
        this.articleRepositoryJDBC = articleRepositoryJDBC;
    }

    @Test
    @DisplayName("select 테스트")
    public void selectTest() {
        articleRepositoryJDBC.findAll();
    }
}