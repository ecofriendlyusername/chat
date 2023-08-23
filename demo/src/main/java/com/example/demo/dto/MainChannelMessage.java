package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // jackson
public class MainChannelMessage {
    private String type; // change to ENUM later
    private String destination;
    private Long chatRoomId;
    @Builder
    MainChannelMessage(String type, String destination, Long chatRoomId) {
        this.type = type;
        this.destination = destination;
        this.chatRoomId = chatRoomId;
    }
}
