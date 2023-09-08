package com.example.demo.dto.gathering;

import com.example.demo.entity.ChatRoom;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class ChatRoomSimpleDto {
    String destination;
    Long id;

    public static ChatRoomSimpleDto convertChatRoomToChatRoomSimpleDto(ChatRoom chatRoom) {
        return ChatRoomSimpleDto.builder()
                .destination(chatRoom.getDestination())
                .id(chatRoom.getId())
                .build();
    }

    @Builder
    ChatRoomSimpleDto(String destination, Long id) {
        this.destination = destination;
        this.id = id;
    }
}
