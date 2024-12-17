package com.travel.leave.domain.board.service.post;

import com.travel.leave.domain.board.dto.response.PostListDTO;
import com.travel.leave.domain.board.repository.PostSortRepository;
import com.travel.leave.domain.board.board_enum.SortField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSortService {

    private final PostSortRepository postSortRepository;

    public List<PostListDTO> getSortedPosts(SortField SortField, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postSortRepository.findPostsBySort(SortField, pageable);
    }

    public List<PostListDTO> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postSortRepository.searchPosts(keyword, pageable);
    }
}