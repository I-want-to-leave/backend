package com.travel.leave.domain.chat.service;

import com.travel.leave.domain.chat.dto.request.ChatRoomCreateDTO;
import com.travel.leave.domain.chat.entity.ChatRoom;
import com.travel.leave.domain.chat.mapper.ChatRoomMapper;
import com.travel.leave.domain.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public ChatRoomService(
            ChatRoomRepository chatRoomRepository,
            @Qualifier("HashRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.chatRoomRepository = chatRoomRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public Long createChatRoom(ChatRoomCreateDTO chatRoomCreateDTO, Long userCode) {
        ChatRoom chatRoom = ChatRoomMapper.createChatRoom(chatRoomCreateDTO, userCode);
        chatRoomRepository.save(chatRoom);
        String activeUsersKey = REDIS_CHAT_MSG.ACTIVE_USERS_LIST.getMessage() + chatRoom.getId();
        redisTemplate.opsForSet().add(activeUsersKey, userCode);
        return chatRoom.getId();
    }
}

