package com.travel.leave.board.service.post;

import com.travel.leave.board.dto.request.create.CreatePostRequestDTO;
import com.travel.leave.board.dto.response.postdetail.*;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostImage;
import com.travel.leave.board.entity.PostPreparation;
import com.travel.leave.board.entity.PostTravelRoute;
import com.travel.leave.board.mapper.*;
import com.travel.leave.board.repository.comment.PostCommentRepository;
import com.travel.leave.board.repository.post_iamge.PostImageRepository;
import com.travel.leave.board.repository.post.PostRepository;
import com.travel.leave.board.repository.preparation.PostPreparationRepository;
import com.travel.leave.board.repository.travelroute.PostTravelRouteRepository;
import com.travel.leave.board.service.like.PostLikeService;
import com.travel.leave.board.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostPreparationRepository postPreparationRepository;
    private final PostTravelRouteRepository postTravelRouteRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostLikeService postLikeService;
    private final PostValidator postValidator;

    @Transactional
    public void createPost(CreatePostRequestDTO createPostRequestDTO, Long userCode, Long travelCode) {
        postValidator.validateUniqueTravelCode(travelCode);

        Post post = PostMapper.toPostEntity(createPostRequestDTO, userCode, travelCode);
        postRepository.save(post);

        List<PostImage> postImages = PostImageMapper.toPostImageEntitiesForCreation(createPostRequestDTO.getImagePath(), post);
        postImageRepository.saveAll(postImages);

        List<PostPreparation> preparations = PostPreparationMapper.toPostPreparationEntities(createPostRequestDTO.getPreparation(), post);
        postPreparationRepository.saveAll(preparations);

        List<PostTravelRoute> travelRoutes = PostTravelRouteMapper.toPostTravelRouteEntities(createPostRequestDTO.getTravelRoute(), post);
        postTravelRouteRepository.saveAll(travelRoutes);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO detailPost(Long postCode) {
        postRepository.incrementViews(postCode);
        Post post = postValidator.validateActivePost(postCode);
        Long likeCount = postLikeService.getLikesCount(postCode);

        List<PostImageDTO> imageDTOs = postImageRepository.findImagesByPostCode(postCode).stream()
                .map(PostImageMapper::toPostImageDTO)
                .collect(Collectors.toList());

        List<PostCommentDTO> commentDTOs = postCommentRepository.findCommentsByPostCode(postCode).stream()
                .map(PostCommentMapper::toPostCommentDTO)
                .collect(Collectors.toList());

        List<PostPreparationDTO> preparationDTOS = postPreparationRepository.findPreparationsByPostCode(postCode).stream()
                .map(PostPreparationMapper::toPostPreparationDTO)
                .collect(Collectors.toList());

        List<PostTravelRouteDTO> travelRouteDTOs = postTravelRouteRepository.findRoutesByPostCode(postCode).stream()
                .map(PostTravelRouteMapper::toPostTravelRouteDTO)
                .collect(Collectors.toList());

        return PostMapper.toPostResponseDTO(post, likeCount, imageDTOs, commentDTOs, preparationDTOS, travelRouteDTOs);
    }

    @Transactional
    public void removePost(Long postCode, Long userCode) {
        Post post = postValidator.returnValidatePostMaster(postCode, userCode);
        post.deactivate(new Timestamp(System.currentTimeMillis()));
    }
}