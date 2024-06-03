package demo.spring_tutorial2.config;

import demo.spring_tutorial2.repository.article.ArticleRepository;
import demo.spring_tutorial2.repository.article.ArticleRepositoryJPA;
import demo.spring_tutorial2.repository.articleComment.ArticleCommentRepository;
import demo.spring_tutorial2.repository.articleComment.ArticleCommentRepositoryJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // 여기서 적용된 값이 createdBy, modifiedBy 란에 입력되는 값임
    // 추후에 접속한 사람의 이름이 들어가도록 시큐리티를 통해서 수정해야함
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("test??");
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new ArticleRepositoryJPA();
    }

    @Bean
    public ArticleCommentRepository articleCommentRepository() {
        return new ArticleCommentRepositoryJPA();
    }
}
