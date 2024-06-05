package demo.spring_tutorial2.dto;

import demo.spring_tutorial2.domain.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestArticleUpdate {
    private ArticleStatus status;
    private String hashTag;
}
