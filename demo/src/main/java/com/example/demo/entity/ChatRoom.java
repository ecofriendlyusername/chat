package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Member> members;

    private String roomName;
    private String destination;

    @Builder
    ChatRoom(String destination, List<Member> members, String roomName) {
        this.members = members;
        this.destination = destination;
        this.roomName = roomName;
    }
}
