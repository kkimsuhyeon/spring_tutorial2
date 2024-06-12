package demo.spring_tutorial2.controller.api;

import demo.spring_tutorial2.dto.SearchValue;
import demo.spring_tutorial2.dto.domain.ArticleDto;
import demo.spring_tutorial2.dto.response.ResponseArticle;
import demo.spring_tutorial2.dto.response.ResponseListArticle;
import demo.spring_tutorial2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<ResponseListArticle<ArticleDto>> articles(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        SearchValue searchValue = new SearchValue();
        searchValue.setTitle(title);
        searchValue.setContent(content);

        Page<ArticleDto> articles = articleService.searchArticle(searchValue, pageable);

        ResponseListArticle<ArticleDto> result = ResponseListArticle
                .of(articles.getTotalElements(), articles.getTotalPages(), pageable.getSort(), articles.getContent());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{articleId}")
    public ResponseEntity<?> article(@PathVariable(value = "articleId") Long id) {
        try {
            ArticleDto article = articleService.getArticle(id);
            return new ResponseEntity<>(ResponseArticle.from(article), HttpStatus.OK);
        } catch (Exception error) {
            Map<String, String> message = new HashMap<>();
            message.put("message", error.getMessage());
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
