package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomInGathering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Gathering gathering;

    @ManyToOne
    ChatRoom chatRoom;

    @Builder
    ChatRoomInGathering(Gathering gathering, ChatRoom chatRoom) {
        this.gathering = gathering;
        this.chatRoom = chatRoom;
    }
}
