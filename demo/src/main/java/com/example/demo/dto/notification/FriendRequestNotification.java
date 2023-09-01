package com.example.demo.dto.notification;

import com.example.demo.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // jackson
public class FriendRequestNotification implements StompMessage {

    private NotificationType type;
    private Long friendRequestId;
    private String from;

    private String helloMessage;
    @Builder
    FriendRequestNotification(NotificationType type, String from, String helloMessage, Long friendRequestId) {
        this.type = type;
        this.from = from;
        this.friendRequestId = friendRequestId;
        this.helloMessage = helloMessage;
    }
}
