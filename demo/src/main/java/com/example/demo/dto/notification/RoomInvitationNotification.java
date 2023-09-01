package com.example.demo.dto.notification;

import com.example.demo.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // jackson
public class RoomInvitationNotification implements StompMessage {
    private NotificationType type; // change to ENUM later
    private String destination;
    private Long chatRoomId;
    @Builder
    RoomInvitationNotification(NotificationType type, String destination, Long chatRoomId) {
        this.type = type;
        this.destination = destination;
        this.chatRoomId = chatRoomId;
    }
}
