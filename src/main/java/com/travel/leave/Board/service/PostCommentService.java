package com.travel.leave.Board.service;

import com.travel.leave.Board.dto.PostCommentDTO;
import com.travel.leave.Board.entity.PostComment;
import com.travel.leave.Board.repository.PostCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    public PostCommentDTO addComment(Long postCode, Long userCode, String content) {
        PostComment comment = PostComment.create(postCode, userCode, content);
        PostComment savedComment = postCommentRepository.save(comment);
        return PostCommentDTO.fromEntity(savedComment);
    }

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
        return PostCommentDTO.fromEntity(updatedComment);
    }


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

