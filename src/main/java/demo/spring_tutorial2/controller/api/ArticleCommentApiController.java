package demo.spring_tutorial2.controller.api;

import demo.spring_tutorial2.dto.request.RequestArticleComment;
import demo.spring_tutorial2.service.ArticleCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class ArticleCommentApiController {

    private final ArticleCommentService articleCommentService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> addComment(@ModelAttribute RequestArticleComment request) {
        Map<String, String> result = new HashMap<>();
        try {
            articleCommentService.add(request.toDto());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception error) {
            result.put("message", error.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable(value = "commentId") Long commentId,
            @ModelAttribute @Valid RequestArticleComment request) {

        Map<String, String> result = new HashMap<>();
        articleCommentService.update(request.toDtoWithId(commentId));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable(value = "commentId") Long commentId
    ) {
        articleCommentService.delete(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
