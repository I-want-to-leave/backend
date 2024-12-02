package com.travel.leave.board.service;

import com.travel.leave.board.dto.CreatePostDTO;
import com.travel.leave.board.dto.PostCommentDTO;
import com.travel.leave.board.dto.ResponseDetailPostDTO;
import com.travel.leave.board.dto.ResponsePostListDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostComment;
import com.travel.leave.board.mapper.PostCommentMapper;
import com.travel.leave.board.mapper.PostMapper;
import com.travel.leave.board.repository.PostCommentRepository;
import com.travel.leave.board.repository.PostRepository;
import com.travel.leave.exception.SearchKeywordException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional(readOnly = true)
    public List<ResponsePostListDTO> getPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Post> posts = postRepository.findAllActivePosts(pageable);

        return posts.stream()
                .map(PostMapper::fromPostToListDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResponsePostListDTO> searchPosts(String keyword, int page) {
        if (keyword == null) {
            throw new SearchKeywordException();
        }
        Pageable pageable = PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Post> posts = postRepository.searchPosts(keyword, pageable);

        return posts.stream()
                .map(PostMapper::fromPostToListDTO)
                .toList();
    }

    @Transactional
    public void createPost(Long userCode, CreatePostDTO createPostDTO) {
        Post post = PostMapper.ofCreatePostDTO(createPostDTO, userCode);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public ResponseDetailPostDTO getPost(Long postCode) {
        postRepository.incrementViews(postCode);
        ResponseDetailPostDTO postDetailsDTO = postRepository.findPostWithDetails(postCode)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        List<PostComment> comments = postCommentRepository.findCommentsByPostCode(postCode);
        List<PostCommentDTO> commentDTOs = comments.stream()
                .map(PostCommentMapper::fromPostCommentDTO)
                .toList();
        postDetailsDTO.setComments(commentDTOs);
        return postDetailsDTO;
    }

    @Transactional
    public void updatePost(Long postCode, Long userCode, CreatePostDTO createPostDTO) {
        Post post = postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException("업데이트 권한이 없습니다");
        }
        post.updatePost(createPostDTO.getTitle(), createPostDTO.getContent());
    }

    @Transactional
    public void deletePost(Long postCode, Long userCode) {
        Post post = postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException("삭제 권한이 없습니다");
        }
        post.deactivate(Timestamp.from(Instant.now()));
    }

    @Transactional(readOnly = true)
    public List<ResponsePostListDTO> getPopularPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findPostsByLikesDesc(pageable);
    }
}