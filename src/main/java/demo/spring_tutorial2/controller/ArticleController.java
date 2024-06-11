package demo.spring_tutorial2.controller;

import demo.spring_tutorial2.dto.domain.ArticleDto;
import demo.spring_tutorial2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    //    http://localhost:8080/articles?page=0&size=5&sort=name,asc&sort=email,desc
    @GetMapping
    public String articles(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {

        Page<ArticleDto> articles = articleService.searchArticle(null, pageable);
        map.addAttribute("articles", articles);

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable(value = "articleId") Long articleId, ModelMap map) {
        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(
    ) {
        return "redirect:/articles";
    }

    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        return "articles/form";
    }

    @PostMapping("/{articleId}/form")
    public String updateArticle(
            @PathVariable Long articleId
    ) {
        return "redirect:/articles/%d".formatted(articleId);
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable Long articleId
    ) {
        return "redirect:/articles";
    }
}
