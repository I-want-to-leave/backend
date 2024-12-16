package com.travel.leave.schedule.service;

import com.travel.leave.login.entity.UserTravel;
import com.travel.leave.schedule.dto.get.MemberRequestDTO;
import com.travel.leave.schedule.dto.get.invite.UserInviteDTOs;
import com.travel.leave.schedule.repository.invite.UserTravelRepository;
import com.travel.leave.schedule.service.model.mapper.UserTravelFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserTravelService {
    private final static int FIND_USER_LIMIT = 7;
    private final UserTravelRepository userTravelRepository;
    private final UserTravelFactory userTravelFactory;

    public UserInviteDTOs getUsersForInvite(String emailKeyword) {
        return userTravelRepository.findScheduleInviteUsers(emailKeyword, FIND_USER_LIMIT);
    }


    public List<UserTravel> save(Long travelCode, Long userCode, List<MemberRequestDTO> member) {
        List<UserTravel> userTravels = userTravelFactory.generateUserTravels(travelCode, userCode, member);
        log.info("save");
        return userTravelRepository.saveAll(userTravels);
    }

}
