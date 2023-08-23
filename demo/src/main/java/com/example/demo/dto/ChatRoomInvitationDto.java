package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomInvitationDto {
    private Long chatRoomId;
    private String destination;
    private List<String> invitees;


    @Builder
    ChatRoomInvitationDto(Long chatRoomId, String destination, List<String> invitees) {
        this.destination = destination;
        this.chatRoomId = chatRoomId;
        this.invitees = invitees;
    }
}

