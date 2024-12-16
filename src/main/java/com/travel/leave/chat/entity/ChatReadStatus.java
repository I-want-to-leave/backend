package com.travel.leave.chat.entity;

import com.travel.leave.login.entity.User;
import groovy.transform.builder.Builder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "chat_read_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatReadStatus {
    @EmbeddedId
    private ChatReadStatusId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(name = "last_read_message_id", nullable = false)
    private Long lastReadMessageId;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatReadStatus that = (ChatReadStatus) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}