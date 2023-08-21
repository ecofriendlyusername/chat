package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    private MainChannel mainChannel;
    //mappedBy = "table"
    @ManyToMany(fetch = FetchType.LAZY)
    private List<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ChatLine> chatLines;

    private String name;

    @Builder
    Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void addChatRoom(ChatRoom chatRoom) {
        if (this.chatRooms == null) this.chatRooms = new ArrayList<>();
        this.chatRooms.add(chatRoom);
    }
}
