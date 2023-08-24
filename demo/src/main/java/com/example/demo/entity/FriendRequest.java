package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "friend_request", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"from_id", "to_id"})
})
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member to;

    @OneToOne
    private Member from;

    private String helloMessage;

    @Builder
    FriendRequest(Member from, Member to, String helloMessage) {
        this.from = from;
        this.to = to;
        this.helloMessage = helloMessage;
    }
}
