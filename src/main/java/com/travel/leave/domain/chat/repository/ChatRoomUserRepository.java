package com.travel.leave.domain.chat.repository;

import com.travel.leave.domain.chat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
}
