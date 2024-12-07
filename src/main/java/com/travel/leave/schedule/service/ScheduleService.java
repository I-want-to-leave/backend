package com.travel.leave.schedule.service;

import com.travel.leave.entity.UserTravel;
import com.travel.leave.schedule.dto.request.initialize.ScheduleInitializeRequestDTO;
import com.travel.leave.schedule.dto.request.initialize.ScheduleInvitedUserRequestDTO;
import com.travel.leave.schedule.dto.request.initialize.ScheduleTravelLocationRequestDTO;
import com.travel.leave.schedule.dto.response.initialize.ScheduleInviteUsersResponseDTO;
import com.travel.leave.schedule.repository.UserTravelRepository;
import com.travel.leave.travel.entity.Travel;
import com.travel.leave.travel.entity.TravelLocation;
import com.travel.leave.travel.repository.TravelLocationRepository;
import com.travel.leave.travel.repository.TravelRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final static int FIND_USER_LIMIT = 7;
    private final TravelRepository travelRepository;
    private final TravelLocationRepository travelLocationRepository;
    private final UserTravelRepository userTravelRepository;

    public ScheduleInviteUsersResponseDTO getUsersForInvite(String emailKeyword) {
        return travelRepository.findScheduleInviteUsers(emailKeyword, FIND_USER_LIMIT);
    }

    @Transactional
    public void initializeSchedule(Long userCode, ScheduleInitializeRequestDTO scheduleInitializeRequestDTO) {
        Travel savedTravel = saveInitializedTravel(Travel.of(scheduleInitializeRequestDTO));

        saveInitializedTravelLocation(
                mapTravelLocations(scheduleInitializeRequestDTO.travelLocationRequestDTOs(), savedTravel.getCode()));

        saveUserTravels(
                mapUserTravels(scheduleInitializeRequestDTO.invitedUserRequestDTOs(), savedTravel.getCode(), userCode));
    }

    private Travel saveInitializedTravel(Travel travel) {
        return travelRepository.save(travel);
    }

    private void saveInitializedTravelLocation(List<TravelLocation> travelLocations) {
        travelLocationRepository.saveAll(travelLocations);
    }

    private void saveUserTravels(List<UserTravel> userTravels) {
        userTravelRepository.saveAll(userTravels);
    }

    private List<TravelLocation> mapTravelLocations(List<ScheduleTravelLocationRequestDTO> scheduleTravelLocationRequestDTOs,
                                                    Long travelCode) {
        List<TravelLocation> travelLocations = new ArrayList<>();
        scheduleTravelLocationRequestDTOs.forEach(travelLocationRequestDTO -> {
            travelLocations.add(TravelLocation.of(travelLocationRequestDTO, travelCode));
        });
        return travelLocations;
    }

    private List<UserTravel> mapUserTravels(List<ScheduleInvitedUserRequestDTO> scheduleInvitedUserRequestDTOs,
                                                     Long travelCode,
                                                     Long userCode) {
        List<UserTravel> userTravels = new ArrayList<>();
        scheduleInvitedUserRequestDTOs.forEach(userInvitedUserRequestDTO -> {
            userTravels.add(UserTravel.of(travelCode, userCode));
        });
        userTravels.add(UserTravel.of(travelCode, userCode));

        return userTravels;
    }
}
