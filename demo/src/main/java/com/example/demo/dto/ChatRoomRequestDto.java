package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomRequestDto {
    private List<String> invitees;
    private String roomName;

    @Builder
    ChatRoomRequestDto(String roomName, List<String> invitees) {
        this.roomName = roomName;
        this.invitees = invitees;
    }
}
