package com.travel.leave.board.service;

import com.travel.leave.board.dto.PostCommentDTO;
import com.travel.leave.board.entity.PostComment;
import com.travel.leave.board.mapper.PostCommentMapper;
import com.travel.leave.board.repository.PostCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    @Transactional
    public PostCommentDTO addComment(Long postCode, Long userCode, String content) {
        PostComment comment = PostComment.of(postCode, userCode, content); // 생성작업이기에 매퍼에 포함 X
        PostComment savedComment = postCommentRepository.save(comment);
        return PostCommentMapper.fromPostCommentDTO(savedComment);
    }

    @Transactional
    public PostCommentDTO updateComment(Long postCode, Long commentCode, Long userCode, String newContent) {

        PostComment comment = postCommentRepository.findById(commentCode)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if (!comment.getPostCode().equals(postCode)) {
            throw new IllegalArgumentException("해당 게시글에 속하지 않는 댓글입니다.");
        }

        if (!comment.getUserCode().equals(userCode)) {
            throw new AccessDeniedException("댓글 수정 권한이 없습니다.");
        }

        comment.setterContent(newContent);
        PostComment updatedComment = postCommentRepository.save(comment);
        return PostCommentMapper.fromPostCommentDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Long postCode, Long commentId, Long userCode) {
        PostComment comment = postCommentRepository.findActiveCommentById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        if (!comment.getPostCode().equals(postCode)) {
            throw new IllegalArgumentException("해당 게시글에 속하지 않는 댓글입니다.");
        }

        if (!comment.getUserCode().equals(userCode)) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        comment.deactivate();
    }
}