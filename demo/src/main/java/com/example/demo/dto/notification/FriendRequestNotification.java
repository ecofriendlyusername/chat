package com.example.demo.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // jackson
public class FriendRequestNotification implements StompMessage  {

    private String type;
    private Long friendRequestId;
    private String from;

    private String helloMessage;
    @Builder
    FriendRequestNotification(String type, String from, String helloMessage, Long friendRequestId) {
        this.type = type;
        this.from = from;
        this.friendRequestId = friendRequestId;
        this.helloMessage = helloMessage;
    }
}
