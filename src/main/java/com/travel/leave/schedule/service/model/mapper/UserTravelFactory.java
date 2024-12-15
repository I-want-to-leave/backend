package com.travel.leave.schedule.service.model.mapper;

import com.travel.leave.entity.UserTravel;
import com.travel.leave.schedule.dto.get.MemberRequestDTO;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTravelFactory {
    public List<UserTravel> generateUserTravels(Long travelCode, Long userCode, List<MemberRequestDTO> member) {
        log.info("generateUserTravels");
        List<UserTravel> userTravelNotContainsMe = member.stream()  //채팅방을 만든 사람을 추가하지 않은 Usertravel
                .map(user -> {
                    return getUserTravel(travelCode, user.userCode());
                }).collect(Collectors.toList());
        return addMe(travelCode, userCode, userTravelNotContainsMe);
    }

    private List<UserTravel> addMe(Long travelCode, Long userCode, List<UserTravel> userTravelNotContainsMe) {  //채팅방을 만든 사람을 추가하는 UserTravel
        log.info("addMe");
        userTravelNotContainsMe.add(getUserTravel(travelCode, userCode));
        return userTravelNotContainsMe;
    }

    private UserTravel getUserTravel(Long travelCode, Long userCode) {
        log.info("getUserTravel");
        return UserTravel.of(travelCode, userCode);
    }
}
