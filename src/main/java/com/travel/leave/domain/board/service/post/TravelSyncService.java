package com.travel.leave.domain.board.service.post;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postimage.entity.PostImage;
import com.travel.leave.domain.board.mapper.SyncTripMapper;
import com.travel.leave.domain.board.validator.common_validator.PostValidator;
import com.travel.leave.subdomain.usertravel.entity.UserTravel;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import com.travel.leave.subdomain.travellocaion.repository.TravelLocationRepository;
import com.travel.leave.subdomain.travelpreparation.repository.TravelPreparationRepository;
import com.travel.leave.subdomain.travel.repository.TravelRepository;
import com.travel.leave.subdomain.usertravel.repository.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelSyncService {

    private final TravelRepository travelRepository;
    private final TravelLocationRepository travelLocationRepository;
    private final TravelPreparationRepository travelPreparationRepository;
    private final UserTravelRepository userTravelRepository;
    private final PostValidator postValidator;

    @Transactional
    public Long syncTravelFromPost(Long postCode, Long userCode) {
        Post post = postValidator.validateActivePost(postCode);
        String mainImageUrl = findRepresentativeImage(post);
        Travel savedTravel = travelRepository.save(SyncTripMapper.toTravelEntity(post, mainImageUrl));

        List<TravelLocation> locations = post.getTravelRoutes().stream()
                .map(travelRoute -> SyncTripMapper.toTravelLocationEntity(travelRoute, savedTravel.getCode()))
                .collect(Collectors.toList());
        travelLocationRepository.saveAll(locations);

        List<TravelPreparation> preparations = post.getPreparations().stream()
                .map(preparation -> SyncTripMapper.toTravelPreparationEntity(preparation, savedTravel.getCode()))
                .collect(Collectors.toList());
        travelPreparationRepository.saveAll(preparations);

        UserTravel userTravel = SyncTripMapper.toUserTravelEntity(savedTravel.getCode(), userCode);
        userTravelRepository.save(userTravel);
        return savedTravel.getCode();
    }

    private String findRepresentativeImage(Post post) {
        return post.getImages().stream()
                .filter(image -> image.getOrder() == 0)
                .map(PostImage::getFilePath)
                .findFirst()
                .orElse(null);
    }
}