package demo.spring_tutorial2.config;

import demo.spring_tutorial2.repository.article.ArticleRepository;
import demo.spring_tutorial2.repository.article.ArticleRepositoryJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // 여기서 적용된 값이 createdBy, modifiedBy 란에 입력되는 값임
    // 추후에 접속한 사람의 이름이 들어가도록 시큐리티를 통해서 수정해야함
    @Bean
    public AuditorAware<String> auditorAware() {
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context);

        return () -> Optional.of("????");
//        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(Principal.class::cast).map(Principal::getName);
    }
}
