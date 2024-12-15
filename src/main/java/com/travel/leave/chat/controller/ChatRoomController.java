package com.travel.leave.chat.controller;

import com.travel.leave.chat.dto.request.ChatRoomCreateDTO;
import com.travel.leave.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<Long> createChatRoom(
            @RequestBody ChatRoomCreateDTO request,
            @AuthenticationPrincipal Long userCode) {
        Long roomId = chatRoomService.createChatRoom(request, userCode);
        return ResponseEntity.ok(roomId);
    }
}

