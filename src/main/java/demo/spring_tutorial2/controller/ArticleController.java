package demo.spring_tutorial2.controller;

import demo.spring_tutorial2.dto.SearchValue;
import demo.spring_tutorial2.dto.domain.ArticleDto;
import demo.spring_tutorial2.dto.response.ResponseArticle;
import demo.spring_tutorial2.dto.response.ResponseListArticle;
import demo.spring_tutorial2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    // http://localhost:8080/articles?page=0&size=5&sort=name,asc&sort=email,desc
    @GetMapping
    public String articles(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 10, page = 1, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        SearchValue searchValue = new SearchValue();
        searchValue.setTitle(title);
        searchValue.setContent(content);

        Page<ArticleDto> articles = articleService.searchArticles(searchValue, pageable);

        ResponseListArticle<ArticleDto> result = ResponseListArticle
                .of(articles.getTotalElements(), articles.getTotalPages(), pageable.getSort(), articles.getContent());

        map.addAttribute("result", result);

        return "article/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable(value = "articleId") Long id, ModelMap map) {

        ArticleDto article = articleService.getArticle(id);

        map.addAttribute("result", article);
        return "article/detail";
    }


}
