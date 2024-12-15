package com.travel.leave.chat.repository;

import com.travel.leave.chat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
}
