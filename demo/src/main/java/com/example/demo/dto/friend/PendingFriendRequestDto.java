package com.example.demo.dto.friend;

import com.example.demo.entity.FriendRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PendingFriendRequestDto {
    private String from;
    private Long friendRequestId;
    private String helloMessage;

    @Builder
    PendingFriendRequestDto(String from, String helloMessage, Long friendRequestId) {
        this.friendRequestId = friendRequestId;
        this.helloMessage = helloMessage;
        this.from = from;
    }
}
