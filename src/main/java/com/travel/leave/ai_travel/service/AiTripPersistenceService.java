package com.travel.leave.ai_travel.service;

import com.travel.leave.login.entity.UserTravel;
import com.travel.leave.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.ai_travel.entity.Travel;
import com.travel.leave.ai_travel.entity.TravelLocation;
import com.travel.leave.ai_travel.entity.TravelPreparation;
import com.travel.leave.ai_travel.mapper.AI_Mapper.AI_TripMapper;
import com.travel.leave.ai_travel.repository.TravelLocationRepository;
import com.travel.leave.ai_travel.repository.TravelPreparationRepository;
import com.travel.leave.ai_travel.repository.TravelRepository;
import com.travel.leave.schedule.repository.invite.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiTripPersistenceService {

    private final TravelRepository travelRepository;
    private final TravelLocationRepository travelLocationRepository;
    private final TravelPreparationRepository travelPreparationRepository;
    private final UserTravelRepository userTravelRepository;

    @Transactional
    public void saveAllTripData(RecommendDTO recommendDTO, Long userCode) {
        Travel travel = saveTravel(recommendDTO);
        saveTravelLocations(recommendDTO, travel.getCode());
        saveTravelPreparations(recommendDTO, travel.getCode());
        saveUserTravel(travel.getCode(), userCode);
    }

    public Travel saveTravel(RecommendDTO recommendDTO) {
        Travel travel = AI_TripMapper.toTravelEntity(recommendDTO);
        return travelRepository.save(travel);
    }

    public void saveTravelLocations(RecommendDTO recommendDTO, Long travelCode) {
        List<TravelLocation> travelLocations = recommendDTO.getDailyRoutes().stream()
                .flatMap(List::stream)
                .map(latLngDTO -> AI_TripMapper.toTravelLocationEntity(latLngDTO, travelCode))
                .collect(Collectors.toList());
        travelLocationRepository.saveAll(travelLocations);
    }

    public void saveTravelPreparations(RecommendDTO recommendDTO, Long travelCode) {
        List<TravelPreparation> travelPreparations = recommendDTO.getRecommendedItems().stream()
                .map(itemDTO -> AI_TripMapper.toTravelPreparationEntity(itemDTO, travelCode))
                .collect(Collectors.toList());
        travelPreparationRepository.saveAll(travelPreparations);
    }

    public void saveUserTravel(Long travelCode, Long userCode) {
        UserTravel userTravel = AI_TripMapper.toUserTravelEntity(travelCode, userCode);
        userTravelRepository.save(userTravel);
    }
}