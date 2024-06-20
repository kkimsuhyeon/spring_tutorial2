package demo.spring_tutorial2.config;

import demo.spring_tutorial2.repository.article.ArticleRepository;
import demo.spring_tutorial2.repository.article.ArticleRepositoryJPA;
import demo.spring_tutorial2.repository.member.MemberRepository;
import demo.spring_tutorial2.repository.member.MemberRepositoryJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public ArticleRepository articleRepository() {
        return new ArticleRepositoryJPA();
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryJPA();
    }
}
