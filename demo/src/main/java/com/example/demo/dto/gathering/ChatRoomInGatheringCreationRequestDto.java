package com.example.demo.dto.gathering;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ChatRoomInGatheringCreationRequestDto {
    String chatRoomName;

    @Builder
    ChatRoomInGatheringCreationRequestDto(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
}
