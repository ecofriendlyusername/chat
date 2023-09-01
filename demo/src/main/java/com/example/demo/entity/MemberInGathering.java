package com.example.demo.entity;

import com.example.demo.enums.RoleInGathering;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberInGathering {
    @ManyToOne
    Member member;

    @ManyToOne
    Gathering gathering;

    RoleInGathering roleInGathering;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Builder
    MemberInGathering(Member member, Gathering gathering, RoleInGathering roleInGathering) {
        this.gathering = gathering;
        this.roleInGathering = roleInGathering;
        this.member = member;
    }
}
