package com.travel.leave.board.service.post;

import com.travel.leave.board.dto.response.PostListDTO;
import com.travel.leave.board.repository.post.PostSortRepository;
import com.travel.leave.board.service.enums.SortField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSortService {

    private final PostSortRepository postSortRepository;

    public List<PostListDTO> getSortedPosts(SortField sortField, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postSortRepository.findPostsBySort(sortField, pageable);
    }

    public List<PostListDTO> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postSortRepository.searchPosts(keyword, pageable);
    }
}