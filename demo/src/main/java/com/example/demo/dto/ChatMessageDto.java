package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private String type;
    private String content;
    private String sender;

    @Builder
    ChatMessageDto(Long id, String content, String type, String sender) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.sender = sender;
    }
}
