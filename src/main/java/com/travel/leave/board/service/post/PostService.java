package com.travel.leave.board.service.post;

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
import com.travel.leave.board.service.enums.BOARD_EX_MSG;
import com.travel.leave.board.service.like.PostLikeService;
import com.travel.leave.board.service.post_image.PostImageService;
import com.travel.leave.board.validator.common_validator.PostValidator;
import com.travel.leave.ai_travel.entity.Travel;
import com.travel.leave.ai_travel.entity.TravelLocation;
import com.travel.leave.ai_travel.entity.TravelPreparation;
import com.travel.leave.ai_travel.repository.TravelLocationRepository;
import com.travel.leave.ai_travel.repository.TravelPreparationRepository;
import com.travel.leave.ai_travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostPreparationRepository postPreparationRepository;
    private final PostTravelRouteRepository postTravelRouteRepository;
    private final PostCommentRepository postCommentRepository;
    private final TravelRepository travelRepository;
    private final TravelPreparationRepository travelPreparationRepository;
    private final TravelLocationRepository travelLocationRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeService postLikeService;
    private final PostImageService postImageService;
    private final PostValidator postValidator;

    @Transactional
    public void createPost(Long userCode, Long travelCode) {
        postValidator.validateUniqueTravelCode(travelCode);

        Travel travel = travelRepository.findActiveTravelById(travelCode)
                .orElseThrow(() -> new IllegalArgumentException(BOARD_EX_MSG.TRAVEL_NOT_FOUND.getMessage()));

        List<TravelPreparation> travelPreparations = travelPreparationRepository.findByTravelCode(travelCode);
        List<TravelLocation> travelLocations = travelLocationRepository.findByTravelCode(travelCode);

        Post post = PostMapper.toPostEntity(travel, userCode);
        postRepository.save(post);

        PostImage postImages = PostImageMapper.toPostImageEntitiesForCreation(travel.getImageUrl(), post);
        postImageRepository.save(postImages);

        List<PostPreparation> postPreparations = PostPreparationMapper.toPostPreparationEntities(travelPreparations, post);
        postPreparationRepository.saveAll(postPreparations);

        List<PostTravelRoute> postTravelRoutes = PostTravelRouteMapper.toPostTravelRouteEntities(travelLocations, post);
        postTravelRouteRepository.saveAll(postTravelRoutes);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO detailPost(Long postCode) {
        incrementPostViews(postCode);
        Post post = postValidator.validateActivePost(postCode);
        Long likeCount = postLikeService.getLikesCount(postCode);
        List<PostImageDTO> imageDTOs = postImageService.getImagesByPostCode(postCode);
        List<PostCommentDTO> commentDTOs = postCommentRepository.findCommentsByPostCode(postCode);
        List<PostPreparationDTO> preparationDTOS = postPreparationRepository.findPreparationsByPostCode(postCode);
        List<PostTravelRouteDTO> travelRouteDTOs = postTravelRouteRepository.findRoutesByPostCode(postCode);
        return PostMapper.toPostResponseDTO(post, likeCount, imageDTOs, commentDTOs, preparationDTOS, travelRouteDTOs);
    }

    @Transactional
    public void removePost(Long postCode, Long userCode) {
        Post post = postValidator.returnValidatePostMaster(postCode, userCode);
        post.deactivate(new Timestamp(System.currentTimeMillis()));
    }

    @Transactional
    public void incrementPostViews(Long postCode) {
        postRepository.incrementViews(postCode);
    }
}