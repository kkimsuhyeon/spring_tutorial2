package demo.spring_tutorial2.dto.request.article;

import demo.spring_tutorial2.dto.domain.ArticleDto;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public abstract class RequestArticle {

    public abstract ArticleDto toDto();

    public ArticleDto getDto() {
        return toDto();
    }


}
