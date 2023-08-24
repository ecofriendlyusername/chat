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
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Member memberOne;

    @OneToOne
    Member memberTwo;

    public Member getOther(Member member) {
        if (member == memberOne) return memberTwo;
        else if (member == memberTwo) return memberOne;
        return null;
    }

    @Builder
    Friendship(Member memberOne, Member memberTwo) {
        this.memberOne = memberOne;
        this.memberTwo = memberTwo;
    }
}
