package com.travel.leave.domain.ai_travel.service.recommend.travel_batch;

import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.mapper.AI_Mapper.AI_TripMapper;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import com.travel.leave.subdomain.usertravel.entity.UserTravel;
import com.travel.leave.subdomain.usertravel.repository.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiTripPersistenceService {

    private final JdbcBatchTravelService jdbcBatchTravelService;
    private final UserTravelRepository userTravelRepository;

    @Transactional
    public void saveAllTripData(RecommendDTO recommendDTO, Long userCode) {
        Travel travel = jdbcBatchTravelService.saveTravel(recommendDTO);

        List<TravelLocation> travelLocations = recommendDTO.getDailyRoutes().stream()
                .flatMap(List::stream)
                .map(latLngDTO -> AI_TripMapper.toTravelLocationEntity(latLngDTO, travel.getCode()))
                .toList();
        jdbcBatchTravelService.saveTravelLocations(travelLocations);

        List<TravelPreparation> travelPreparations = recommendDTO.getRecommendedItems().stream()
                .map(itemDTO -> AI_TripMapper.toTravelPreparationEntity(itemDTO, travel.getCode()))
                .toList();
        jdbcBatchTravelService.saveTravelPreparations(travelPreparations);

        saveUserTravel(travel.getCode(), userCode);

        }

    public void saveUserTravel(Long travelCode, Long userCode) {
        UserTravel userTravel = AI_TripMapper.toUserTravelEntity(travelCode, userCode);
        userTravelRepository.save(userTravel);
    }
}