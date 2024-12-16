package com.travel.leave.domain.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatReadStatusId implements Serializable {
    @Column(name = "user_id")
    private Long userCode;

    @Column(name = "room_id")
    private Long roomId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatReadStatusId that = (ChatReadStatusId) o;
        return Objects.equals(userCode, that.userCode) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCode, roomId);
    }
}