package com.travel.leave.chat.entity;

import com.travel.leave.chat.validator.ChatRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "chat_room_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomUser {
    @EmbeddedId
    private ChatRoomUserId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(name = "joined_at", nullable = false)
    @CreationTimestamp
    private Timestamp joinedAt;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoomUser that = (ChatRoomUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}