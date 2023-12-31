package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ChatRoom chatRoom;

    private String sender;
    private String content;
    private String type;
    private LocalDateTime timeStamp;


    @Builder
    ChatMessage(String sender, ChatRoom chatRoom, String content, String type) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
    }
}
