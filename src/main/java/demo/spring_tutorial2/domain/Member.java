package demo.spring_tutorial2.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nickname;

    private String memo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<MemberRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = true)
    private LocalDateTime expiredAt;

    @Builder
    public Member(String email, String password, String nickname, String memo) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static Member of(String email, String password, String nickname, String memo) {
        return new Member(email, password, nickname, memo);
    }

    public void addArticle(Article article) {
        articles.add(article);
        if (article.getMember() == null || !article.getMember().equals(this)) {
            article.assignMember(this);
        }
    }


}
