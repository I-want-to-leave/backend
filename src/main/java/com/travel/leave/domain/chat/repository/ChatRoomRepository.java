package com.travel.leave.domain.chat.repository;

import com.travel.leave.domain.chat.entity.ChatRoom;
import com.travel.leave.domain.chat.entity.ChatRoomUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, ChatRoomUserId> {
}