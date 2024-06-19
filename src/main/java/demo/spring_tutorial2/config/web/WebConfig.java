package demo.spring_tutorial2.config.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    //Filter에 포함되는 URL 주소
    private static final String[] INCLUDE_PATHS = {"/test/*", "/test2/*"};

    @Bean
    public FilterRegistrationBean filterBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new ApiFilter());
        registrationBean.setOrder(Integer.MIN_VALUE); //필터 여러개 적용 시 순번
        registrationBean.addUrlPatterns("/*"); //전체 URL 포함
//        registrationBean.addUrlPatterns("/test/*"); //특정 URL 포함
//        registrationBean.setUrlPatterns(Arrays.asList(INCLUDE_PATHS)); //여러 특정 URL 포함
//        registrationBean.setUrlPatterns(Arrays.asList("/test/*", "/test2/*"));

        return registrationBean;
    }
}
