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
public class MemberInChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Member member;

    @ManyToOne
    ChatRoom chatRoom;

    @Builder
    MemberInChatRoom(Member member, ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        this.member = member;
    }
}
