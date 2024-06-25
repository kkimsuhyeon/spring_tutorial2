package demo.spring_tutorial2.config;

import demo.spring_tutorial2.security.CustomUserDetailsService;
import demo.spring_tutorial2.security.TokenProvider;
import demo.spring_tutorial2.security.jwt.JwtVerificationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public FilterConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(userDetailsService);
    }

    @Bean
    public JwtVerificationFilter jwtVerificationFilter() {
        return new JwtVerificationFilter(tokenProvider());
    }
}
