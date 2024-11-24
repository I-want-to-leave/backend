package com.travel.leave.Board.service;

import com.travel.leave.Board.dto.CreatePostDTO;
import com.travel.leave.Board.dto.ResponseDetailPostDTO;
import com.travel.leave.Board.dto.ResponsePostListDTO;
import com.travel.leave.Board.entity.Post;
import com.travel.leave.Board.entity.PostComment;
import com.travel.leave.Board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<ResponsePostListDTO> getPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Post> posts = postRepository.findAllActivePosts(pageable);

        return posts.stream()
                .map(ResponsePostListDTO::fromEntity)
                .toList();
    }


    public List<ResponsePostListDTO> searchPosts(String keyword, int page) {
        if (keyword == null) {
            throw new IllegalArgumentException("검색어를 입력하세요.");
        }
        Pageable pageable = PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Post> posts = postRepository.searchPosts(keyword, pageable);

        return posts.stream()
                .map(ResponsePostListDTO::fromEntity)
                .toList();
    }


    public void createPost(Long userCode, CreatePostDTO createPostDTO) {
        Post post = createPostDTO.toEntity(userCode);
        postRepository.save(post);
    }

    public ResponseDetailPostDTO getPost(Long postCode) {
        postRepository.incrementViews(postCode);
        List<Object[]> result = postRepository.findPostWithLikeCountAndComments(postCode);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        Post post = (Post) result.get(0)[0];
        Long likeCount = (Long) result.get(0)[1];
        List<PostComment> comments = result.stream()
                .map(row -> (PostComment) row[2])
                .filter(Objects::nonNull)
                .toList();
        return ResponseDetailPostDTO.fromEntity(post, likeCount, comments);
    }

    public void updatePost(Long postCode, Long userCode, CreatePostDTO createPostDTO) {
        Post post = postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException("업데이트 권한이 없습니다");
        }
        post.updatePost(createPostDTO.getTitle(), createPostDTO.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postCode, Long userCode) {
        Post post = postRepository.findActivePostById(postCode)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));
        if (!post.getUserCode().equals(userCode)) {
            throw new AccessDeniedException("삭제 권한이 없습니다");
        }
        post.deactivate();
    }

    public List<ResponsePostListDTO> getPopularPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Object[]> results = postRepository.findPostsByLikesDesc(pageable);
        return results.stream()
                .map(row -> {
                    Post post = (Post) row[0];
                    return ResponsePostListDTO.fromEntity(post);
                })
                .toList();
    }
}