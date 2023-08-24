package com.example.demo.dto.chat;

import lombok.*;

import java.util.List;
@Setter
@NoArgsConstructor
@Getter
public class ChatRoomResponseDto {
    private Long id;

    private List<String> memberEmails;

    private String destination;

    private String roomName;

    @Builder
    ChatRoomResponseDto(Long id, String destination, String roomName, List<String> memberEmails) {
        this.id = id;
        this.destination = destination;
        this.roomName = roomName;
        this.memberEmails = memberEmails;
    }
}
