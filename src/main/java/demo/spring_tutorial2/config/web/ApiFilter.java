package demo.spring_tutorial2.config.web;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("-- 필터 초기화 --");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String requestURI = req.getRequestURI();

        log.info("---Request(" + requestURI + ") 필터---");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("---Response(" + requestURI + ") 필터---");
    }

    @Override
    public void destroy() {
        log.info("---필터 인스턴스 종료---");
    }
}
