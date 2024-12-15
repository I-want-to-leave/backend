package com.travel.leave.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListDTO {
    private Long roomId;
    private String roomName;
    private Timestamp createdAt;
}
