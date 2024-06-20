package demo.spring_tutorial2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchValue {
    private String title;
    private String content;

    public SearchValue(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public SearchValue() {

    }

    public boolean isTitleEmpty() {
        return title == null || title.isEmpty();
    }

    public boolean isContentEmpty() {
        return content == null || content.isEmpty();
    }

    public boolean isEmpty() {
        return isTitleEmpty() && isContentEmpty();
    }
}
