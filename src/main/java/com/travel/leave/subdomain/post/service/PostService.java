package com.travel.leave.subdomain.post.service;

import com.travel.leave.domain.board.dto.response.postdetail.*;
import com.travel.leave.domain.board.exception.PostAlreadySharedException;
import com.travel.leave.domain.board.exception.enums.PostExceptionMessage;
import com.travel.leave.domain.board.mapper.PostImageMapper;
import com.travel.leave.domain.board.mapper.PostMapper;
import com.travel.leave.domain.board.mapper.PostPreparationMapper;
import com.travel.leave.domain.board.mapper.PostTravelRouteMapper;
import com.travel.leave.domain.board.validator.aop.aop_annotation.ValidateBoardMaster;
import com.travel.leave.domain.board.exception.enums.DefaultExceptionMessages;
import com.travel.leave.domain.board.validator.common_validator.BoardValidator;
import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.post.repository.PostRepository;
import com.travel.leave.subdomain.postcomment.repository.PostCommentRepository;
import com.travel.leave.subdomain.postimage.entity.PostImage;
import com.travel.leave.subdomain.postimage.repository.PostImageRepository;
import com.travel.leave.subdomain.postimage.service.PostImageService;
import com.travel.leave.subdomain.postlike.service.PostLikeService;
import com.travel.leave.subdomain.postpreparation.entity.PostPreparation;
import com.travel.leave.subdomain.postpreparation.repository.PostPreparationRepository;
import com.travel.leave.subdomain.posttravelroute.entity.PostTravelRoute;
import com.travel.leave.subdomain.posttravelroute.repository.PostTravelRouteRepository;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travel.repository.TravelRepository;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travellocaion.repository.TravelLocationRepository;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import com.travel.leave.subdomain.travelpreparation.repository.TravelPreparationRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final BoardValidator boardValidator;

    @Transactional
    public Long createPost(Long userCode, Long travelCode) {
        if (postRepository.existsByTravelCode(travelCode)) {
            throw new PostAlreadySharedException(PostExceptionMessage.POST_ALREADY_SHARED);
        }

        Travel travel = travelRepository.findActiveTravelById(travelCode)
                .orElseThrow(() -> new EntityNotFoundException(DefaultExceptionMessages.TRAVEL_NOT_FOUND.getMessage()));

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

        return post.getPostCode();
    }

    @Transactional(readOnly = true)
    public PostResponseDTO detailPost(Long postCode) {
        incrementPostViews(postCode);
        Post post = boardValidator.validateActivePost(postCode);
        Long likeCount = postLikeService.getLikesCount(postCode);
        List<PostImageDTO> imageDTOs = postImageService.getImagesByPostCode(postCode);
        List<PostCommentDTO> commentDTOs = postCommentRepository.findCommentsByPostCode(postCode);
        List<PostPreparationDTO> preparationDTOS = postPreparationRepository.findPreparationsByPostCode(postCode);
        List<PostTravelRouteDTO> travelRouteDTOs = postTravelRouteRepository.findRoutesByPostCode(postCode);
        return PostMapper.toPostResponseDTO(post, likeCount, imageDTOs, commentDTOs, preparationDTOS, travelRouteDTOs);
    }

    @Transactional @ValidateBoardMaster @SuppressWarnings("unused")
    public void removePost(Long postCode, Long userCode) {
        Post post = boardValidator.validateActivePost(postCode);
        post.deactivate(new Timestamp(System.currentTimeMillis()));
    } // userCode 는 aop 에서 사용 됨

    @Transactional
    public void incrementPostViews(Long postCode) {
        postRepository.incrementViews(postCode);
    }
}