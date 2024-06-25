package demo.spring_tutorial2.security;

import demo.spring_tutorial2.security.jwt.JwtAccessDeniedHandler;
import demo.spring_tutorial2.security.jwt.JwtVerificationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtVerificationFilter jwtVerificationFilter;

    @Autowired
    public WebSecurityConfig(JwtVerificationFilter jwtVerificationFilter) {
        this.jwtVerificationFilter = jwtVerificationFilter;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web
                .ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/token/check-token").authenticated()
                        .requestMatchers("/token/create-token").permitAll()
                        .requestMatchers("/token/user").hasAuthority("USER")
                        .requestMatchers("/token/manager").hasAuthority("MANAGER")
                        .anyRequest().permitAll())
                .exceptionHandling(manager -> manager.accessDeniedHandler(new JwtAccessDeniedHandler()))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtVerificationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable).build(); // csrf 비화성화
    }

}