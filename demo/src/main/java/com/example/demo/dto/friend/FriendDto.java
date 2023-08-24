package com.example.demo.dto.friend;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendDto {
    Long id;
    String email;
    String pfpFileName;
    String name;
    Long dmChatRoomId;
    String dmDestination;

    @Builder
    FriendDto(Long id, String email, String pfpFileName, String name, Long dmChatRoomId, String dmDestination) {
        this.id = id;
        this.email = email;
        this.pfpFileName = pfpFileName;
        this.name = name;
        this.dmChatRoomId = dmChatRoomId;
        this.dmDestination = dmDestination;
    }
}
