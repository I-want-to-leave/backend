package com.travel.leave.subdomain.postcomment.service;

import com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postcomment.entity.PostComment;
import com.travel.leave.domain.board.mapper.PostCommentMapper;
import com.travel.leave.subdomain.postcomment.repository.PostCommentRepository;
import com.travel.leave.subdomain.postcomment.validator.PostCommentValidator;
import com.travel.leave.domain.board.validator.common_validator.BoardValidator;
import com.travel.leave.subdomain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostCommentValidator postCommentValidator;
    private final BoardValidator boardValidator;
    private final UserRepository userRepository;
    private final PostCommentMapper postCommentMapper;

    @Transactional
    public PostCommentDTO addComment(Long postCode, Long userCode, String content) {
        Post post = boardValidator.validateActivePost(postCode);
        String nickname = userRepository.findNicknameByUserCode(userCode)
                .orElseThrow(() -> new IllegalArgumentException(CommentExceptionMessage.USER_NOT_FOUND.getMessage()));

        PostComment comment = postCommentMapper.createPostCommentEntity(post, userCode, content, nickname);
        PostComment savedComment = postCommentRepository.save(comment);
        return postCommentRepository.findCommentDTOById(savedComment.getCode());
    }

    @Transactional
    public PostCommentDTO updateComment(Long postCode, Long commentCode, Long userCode, String newContent) {
        PostComment comment = postCommentValidator.validateOwnership(postCode, commentCode, userCode);
        comment.setterContent(newContent);
        PostComment updatedComment = postCommentRepository.save(comment);
        return postCommentRepository.findCommentDTOById(updatedComment.getCode());
    }

    @Transactional
    public Long deleteComment(Long postCode, Long commentCode, Long userCode) {
        PostComment comment = postCommentValidator.validateOwnership(postCode, commentCode, userCode);
        comment.deactivate();
        return comment.getCode();
    } // 댓글을 작성한 본인만이 지울 수 있음, 게시물 작성자도 불가함
}