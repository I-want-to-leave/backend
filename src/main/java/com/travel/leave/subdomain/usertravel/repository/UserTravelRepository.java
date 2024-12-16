package com.travel.leave.subdomain.usertravel.repository;

import com.travel.leave.subdomain.usertravel.entity.UserTravel;
import com.travel.leave.domain.schedule.repository.invite.UserInviteReadQuery;
import com.travel.leave.domain.schedule.repository.query.UserTravelReadQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTravelRepository extends JpaRepository<UserTravel, Long>, UserInviteReadQuery, UserTravelReadQuery {
}
