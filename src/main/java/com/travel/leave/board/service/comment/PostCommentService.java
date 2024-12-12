package com.travel.leave.board.service.comment;

import com.travel.leave.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostComment;
import com.travel.leave.board.mapper.PostCommentMapper;
import com.travel.leave.board.repository.comment.PostCommentRepository;
import com.travel.leave.board.validator.PostCommentValidator;
import com.travel.leave.board.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostCommentValidator postCommentValidator;
    private final PostValidator postValidator;

    @Transactional
    public PostCommentDTO addComment(Long postCode, Long userCode, String content) {
        Post post = postValidator.validateActivePost(postCode);
        PostComment comment = PostCommentMapper.toPostCommentEntity(post, userCode, content);
        PostComment savedComment = postCommentRepository.save(comment);
        return PostCommentMapper.toPostCommentDTO(savedComment);
    }

    @Transactional
    public PostCommentDTO updateComment(Long postCode, Long commentCode, Long userCode, String newContent) {
        PostComment comment = postCommentValidator.validateOwnership(postCode, commentCode, userCode);
        comment.setterContent(newContent);
        PostComment updatedComment = postCommentRepository.save(comment);
        return PostCommentMapper.toPostCommentDTO(updatedComment);
    }

    @Transactional
    public Long deleteComment(Long postCode, Long commentCode, Long userCode) {
        PostComment comment = postCommentValidator.validateOwnership(postCode, commentCode, userCode);
        comment.deactivate();
        return comment.getCode();
    }
}