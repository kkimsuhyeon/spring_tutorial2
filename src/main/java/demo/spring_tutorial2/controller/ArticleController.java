package demo.spring_tutorial2.controller;

import demo.spring_tutorial2.dto.SearchValue;
import demo.spring_tutorial2.dto.domain.ArticleDto;
import demo.spring_tutorial2.dto.response.ResponseList;
import demo.spring_tutorial2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

        ResponseList<ArticleDto> result = ResponseList
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

    @GetMapping("/form")
    public String createArticle() {
        return "article/form";
    }


}
