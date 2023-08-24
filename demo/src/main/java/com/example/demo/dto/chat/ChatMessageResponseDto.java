package com.example.demo.dto.chat;

import com.example.demo.dto.notification.StompMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponseDto implements StompMessage {
    private Long id;
    private String type;
    private String content;
    private String sender;

    @Builder
    ChatMessageResponseDto(Long id, String content, String type, String sender) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.sender = sender;
    }
}
