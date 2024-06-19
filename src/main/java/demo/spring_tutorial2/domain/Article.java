package demo.spring_tutorial2.domain;

import demo.spring_tutorial2.domain.constant.ArticleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    private String hashtag;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleComment> articleComments = new ArrayList<>();

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public Article(String title, String content, String hashtag, List<ArticleComment> comments) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.articleComments = comments;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    public static Article of(String title, String content, String hashtag, List<ArticleComment> comments) {
        return new Article(title, content, hashtag, comments);
    }
    
    public void addComment(ArticleComment comment) {
        articleComments.add(comment);
        if (comment.getArticle() == null || !comment.getArticle().equals(this)) {
            comment.setArticle(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
