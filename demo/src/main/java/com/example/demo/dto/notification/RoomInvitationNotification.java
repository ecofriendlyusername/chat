package com.example.demo.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // jackson
public class RoomInvitationNotification implements StompMessage {
    private String type; // change to ENUM later
    private String destination;
    private Long chatRoomId;
    @Builder
    RoomInvitationNotification(String type, String destination, Long chatRoomId) {
        this.type = type;
        this.destination = destination;
        this.chatRoomId = chatRoomId;
    }
}
