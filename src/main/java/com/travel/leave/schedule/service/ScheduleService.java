package com.travel.leave.schedule.service;

import com.travel.leave.entity.UserTravel;
import com.travel.leave.schedule.dto.get.TravelRequestDTO;
import com.travel.leave.schedule.dto.get.invite.UserInviteDTOs;
import com.travel.leave.schedule.service.model.cache.TravelCache;
import com.travel.leave.schedule.service.model.cache.handler.TravelCacheHandler;
import com.travel.leave.travel.entity.Travel;
import com.travel.leave.travel.entity.TravelLocation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final TravelCacheHandler travelCacheHandler;
    private final TravelService travelService;
    private final TravelLocationService travelLocationService;
    private final UserTravelService userTravelService;

    public UserInviteDTOs getUsersForInvite(String emailKeyword) {
        return userTravelService.getUsersForInvite(emailKeyword);
    }

    public TravelCache loadTravel(Long travelCode) {
        if(!travelCacheHandler.hasTravel(travelCode)){
            return travelCacheHandler.loadTravel(travelCode, false);
        }
        return travelCacheHandler.loadTravel(travelCode, true);
    }

    @Transactional      //나중에 리팩터링할 때 사진 저장로직 실행시간 점검 후 트랜젝션 분리 요망
    public TravelCache initializeTravel(Long userCode, TravelRequestDTO travelRequestDTO) {
        Travel savedTravel = travelService.save(travelRequestDTO);  //여행 저장
        List<TravelLocation> savedTravelLocations = travelLocationService.save(savedTravel.getCode(), travelRequestDTO.schedule()); //여행 스케쥴 저장
        List<UserTravel> savedUserTravels = userTravelService.save(savedTravel.getCode(), userCode, travelRequestDTO.member()); //여행에 포함된 멤버 저장

        return travelCacheHandler.loadTravel(savedTravel.getCode(), false);
    }


}
