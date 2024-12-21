package com.travel.leave.subdomain.postcomment.service;

import com.travel.leave.domain.board.dto.request.CommentRequestDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.exception.common_exception.base_runtime.custom_exception.PostNickNameException;
import com.travel.leave.exception.enums.custom.post.DefaultPostExceptionMsg;
import com.travel.leave.exception.enums.custom.post.PostExceptionMsg;
import com.travel.leave.domain.board.mapper.PostCommentMapper;
import com.travel.leave.domain.board.validator.aop.aop_annotation.ValidateCommentMaster;
import com.travel.leave.domain.board.validator.common_validator.BoardValidator;
import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postcomment.entity.PostComment;
import com.travel.leave.subdomain.postcomment.repository.PostCommentRepository;
import com.travel.leave.subdomain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final BoardValidator boardValidator;
    private final UserRepository userRepository;
    private final PostCommentMapper postCommentMapper;

    @Transactional
    public PostCommentDTO addComment(Long postCode, Long userCode,  CommentRequestDTO commentRequest) {
        Post post = boardValidator.validateActivePost(postCode);
        String nickname = userRepository.findNicknameByUserCode(userCode)
                .orElseThrow(() -> new PostNickNameException(PostExceptionMsg.NICKNAME_NOT_FOUND));

        PostComment comment = postCommentMapper.createPostCommentEntity(post, userCode, commentRequest.getContent(), nickname);
        PostComment savedComment = postCommentRepository.save(comment);
        return postCommentRepository.findCommentDTOById(savedComment.getCode());
    }

    @Transactional @ValidateCommentMaster @SuppressWarnings("unused")
    public PostCommentDTO updateComment(Long commentCode, Long userCode, CommentRequestDTO commentRequest) {
        PostComment comment = postCommentRepository.findActiveCommentById(commentCode)
                        .orElseThrow(() -> new EntityNotFoundException(DefaultPostExceptionMsg.COMMENT_NOT_FOUND.getMessage()));
        comment.setterContent(commentRequest.getContent());
        PostComment updatedComment = postCommentRepository.save(comment);
        return postCommentRepository.findCommentDTOById(updatedComment.getCode());
    } // aop 에서 사용자 검증

    @Transactional @ValidateCommentMaster @SuppressWarnings("unused")
    public Long deleteComment(Long commentCode, Long userCode) {
        PostComment comment = postCommentRepository.findActiveCommentById(commentCode)
                .orElseThrow(() -> new EntityNotFoundException(DefaultPostExceptionMsg.COMMENT_NOT_FOUND.getMessage()));
        comment.deactivate();
        return comment.getCode();
    }  // aop 에서 사용자 검증
}