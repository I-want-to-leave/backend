package com.travel.leave.chat.mapper;

import com.travel.leave.chat.dto.request.ChatRoomCreateDTO;
import com.travel.leave.chat.entity.ChatRoom;
import com.travel.leave.chat.entity.ChatRoomUser;
import com.travel.leave.chat.entity.ChatRoomUserId;
import com.travel.leave.chat.validator.ChatRole;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;

@Component
public class ChatRoomMapper {

    public static ChatRoom createChatRoom(ChatRoomCreateDTO chatRoomCreateDTO, Long userCode) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomCreateDTO.getChatRoomName())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .participants(new HashSet<>())
                .build();

        ChatRoomUser creator = ChatRoomUser.builder()
                .id(new ChatRoomUserId(chatRoom.getId(), userCode))
                .chatRoom(chatRoom)
                .role(ChatRole.CREATOR)
                .joinedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        chatRoom.getParticipants().add(creator);
        return chatRoom;
    }
}