package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.Article;
import demo.spring_tutorial2.domain.ArticleComment;
import demo.spring_tutorial2.domain.CommentStatus;
import demo.spring_tutorial2.dto.domain.ArticleCommentDto;
import demo.spring_tutorial2.repository.article.ArticleRepository;
import demo.spring_tutorial2.repository.articleComment.ArticleCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleCommentService(ArticleCommentRepository articleCommentRepository, ArticleRepository articleRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void add(ArticleCommentDto dto) {
        Article article = articleRepository
                .findById(dto.articleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        articleCommentRepository.save(article, dto.toEntity(article));
    }

    @Transactional
    public void update(ArticleCommentDto dto) {

        if (dto.id() == null) {
            throw new IllegalArgumentException("아이디가 존재하지 않음");
        }

        ArticleComment articleComment = articleCommentRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        articleComment.setContent(dto.content());
        articleComment.setStatus(dto.status());
    }

    @Transactional
    public void delete(Long id){
        ArticleComment articleComment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        articleComment.setStatus(CommentStatus.DELETE);
    }

    @Transactional
    public void realDelete(Long id) {
        ArticleComment articleComment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        articleCommentRepository.delete(articleComment);
    }

}
