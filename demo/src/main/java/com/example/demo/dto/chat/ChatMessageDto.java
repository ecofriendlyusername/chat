package com.example.demo.dto.chat;

import com.example.demo.dto.notification.StompMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChatMessageDto implements StompMessage {
    private Long chatRoomId;
    private String type;
    private String content;
    private String sender;

    @Builder
    ChatMessageDto(Long chatRoomId, String content, String type, String sender) {
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.type = type;
        this.sender = sender;
    }
}
