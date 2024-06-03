package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.ArticleComment;
import demo.spring_tutorial2.repository.articleComment.ArticleCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    @Autowired
    public ArticleCommentService(ArticleCommentRepository articleCommentRepository) {
        this.articleCommentRepository = articleCommentRepository;
    }

    public List<ArticleComment> findAll() {
        return articleCommentRepository.findAll();
    }

    public ArticleComment findById(Long id) {
        return articleCommentRepository.findById(id).orElseThrow();
    }
}
